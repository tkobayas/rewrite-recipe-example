package org.example.lst;

import java.util.ArrayList;
import java.util.List;

import org.openrewrite.ExecutionContext;
import org.openrewrite.SourceFile;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.lang.Nullable;

public class MyLangUpdateAssignmentValueRecipe extends org.openrewrite.Recipe {

    @Override
    public String getDisplayName() {
        return "Update assignment value from 42 to 43 in MyLang files";
    }

    @Override
    public String getDescription() {
        return "Updates any literal \"42\" in MyLang source files to \"43\".";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new MyLangVisitor();
    }


    private static class MyLangVisitor extends TreeVisitor<SourceFile, ExecutionContext> {

        @Override
        public @Nullable SourceFile visit(@Nullable Tree tree, ExecutionContext ctx) {
            if (tree instanceof MyLangSourceFile) {
                MyLangSourceFile sourceFile = (MyLangSourceFile) tree;
                MyLangASTNode originalAst = sourceFile.getRoot();
                MyLangASTNode updatedAst = updateAst(originalAst);
                String updatedSource = MyLangUtils.reassemble(updatedAst, sourceFile.getOriginalSource());
                // If the updated source equals the original, then no changes occurred.
                if (updatedSource.equals(sourceFile.printAll())) {
                    return sourceFile;
                }
                return new MyLangSourceFile(updatedSource, updatedAst, sourceFile.getId(), sourceFile.getSourcePath())
                        .withMarkers(sourceFile.getMarkers());
            }
            return (SourceFile) super.visit(tree, ctx);
        }

        /**
         * Recursively updates the AST: if a leaf node with text "42" is found,
         * it returns a new node with text "43". For composite nodes, only returns a new node
         * if any of its children changed or if the reconstructed text differs from the original.
         */
        private MyLangASTNode updateAst(MyLangASTNode node) {
            // If it's a leaf node:
            if (node.getChildren().isEmpty()) {
                if ("42".equals(node.getText())) {
                    return new MyLangASTNode("43", node.getChildren(), node.getStart(), node.getStop());
                }
                return node;
            }

            boolean changed = false;
            List<MyLangASTNode> newChildren = new ArrayList<>();
            for (MyLangASTNode child : node.getChildren()) {
                MyLangASTNode newChild = updateAst(child);
                newChildren.add(newChild);
                if (newChild != child) {
                    changed = true;
                }
            }
            // Rebuild the parent's text from its children.
            StringBuilder newTextBuilder = new StringBuilder();
            for (MyLangASTNode child : newChildren) {
                newTextBuilder.append(child.getText());
            }
            String newText = newTextBuilder.toString();
            // If nothing changed and the parent's text is the same as before, return the original node.
            if (!changed && node.getText().equals(newText)) {
                return node;
            }
            return new MyLangASTNode(newText, newChildren, node.getStart(), node.getStop());
        }

    }
}