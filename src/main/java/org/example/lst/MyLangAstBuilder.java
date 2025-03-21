package org.example.lst;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.ArrayList;
import java.util.List;

public class MyLangAstBuilder extends MyLangBaseVisitor<MyLangASTNode> {

    // Entry point to build the AST from a given parse tree context.
    public MyLangASTNode build(ParserRuleContext ctx) {
//        return visit(ctx);
        return createNode(ctx);
    }

    @Override
    public MyLangASTNode visitProgram(MyLangParser.ProgramContext ctx) {
        return createNode(ctx);
    }

    // Recursively builds a node from the provided ParserRuleContext.
    private MyLangASTNode createNode(ParserRuleContext ctx) {
        int start = ctx.getStart().getStartIndex();
        int stop = ctx.getStop().getStopIndex();
        List<MyLangASTNode> children = new ArrayList<>();

        // Iterate over each child node.
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof ParserRuleContext) {
                children.add(createNode((ParserRuleContext) ctx.getChild(i)));
            } else if (ctx.getChild(i) instanceof TerminalNode) {
                TerminalNode terminal = (TerminalNode) ctx.getChild(i);
                children.add(new MyLangASTNode(
                        terminal.getText(),
                        new ArrayList<>(),
                        terminal.getSymbol().getStartIndex(),
                        terminal.getSymbol().getStopIndex()
                ));
            }
        }
        return new MyLangASTNode(ctx.getText(), children, start, stop);
    }
}

