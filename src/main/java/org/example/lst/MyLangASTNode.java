package org.example.lst;

import java.util.List;

public class MyLangASTNode {
    private final String text;
    private final List<MyLangASTNode> children;
    private final int start;
    private final int stop;

    public MyLangASTNode(String text, List<MyLangASTNode> children, int start, int stop) {
        this.text = text;
        this.children = children;
        this.start = start;
        this.stop = stop;
    }

    public String getText() {
        return text;
    }

    public List<MyLangASTNode> getChildren() {
        return children;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }
}
