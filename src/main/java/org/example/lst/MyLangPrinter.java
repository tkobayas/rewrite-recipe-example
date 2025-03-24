package org.example.lst;

import org.openrewrite.PrintOutputCapture;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import  org.jspecify.annotations.Nullable;

/**
 * A printer for MyLang source files, similar in spirit to the PlainTextPrinter.
 * It visits a MyLangSourceFile and produces a PrintOutputCapture that contains
 * the output from {@link MyLangSourceFile#printAll()}.
 *
 * @param <P> the printer context type
 */
public class MyLangPrinter<P> extends TreeVisitor<MyLangSourceFile, PrintOutputCapture<P>> {

    @Override
    public @Nullable MyLangSourceFile visit(@Nullable Tree tree, PrintOutputCapture<P> p) {
        if (tree instanceof MyLangSourceFile) {
            MyLangSourceFile file = (MyLangSourceFile) tree;
            // Append the entire printed source to the capture.
            p.append(file.printAll());
            // Return the file itself.
            return file;
        }
        // If the provided tree is not a MyLangSourceFile, return null.
        return null;
    }
}
