package org.example.lst;

public class MyLangUtils {

    /**
     * Reassembles the source code for the given AST node, using the original source text
     * to preserve formatting (such as whitespace and comments) for unmodified parts.
     *
     * @param node           the AST node to reassemble
     * @param originalSource the original source text from which the node was parsed
     * @return the reassembled (lossless) source text for the node
     */
    public static String reassemble(MyLangASTNode node, String originalSource) {
        // If the node is a leaf node, use the original substring if it hasn't been changed.
        if (node.getChildren().isEmpty()) {
            String nodeText = "<EOF>".equals(node.getText()) ? "" : node.getText();
            // Calculate the original text for this node using its offsets.
            String originalText = originalSource.substring(node.getStart(), node.getStop() + 1);
            // If the node's text is unchanged, return the original text.
            if (originalText.equals(nodeText)) {
                return originalText;
            } else {
                // Otherwise, return the updated text.
                return nodeText;
            }
        } else {
            // For composite nodes, reassemble by filling in the gaps.
            StringBuilder sb = new StringBuilder();
            // Start at the node's starting offset.
            int current = node.getStart();
            for (MyLangASTNode child : node.getChildren()) {
                // If there's a gap between the current offset and the child's start,
                // append the original text for that gap.
                if (child.getStart() > current) {
                    sb.append(originalSource, current, child.getStart());
                }
                // Recursively reassemble the child.
                sb.append(reassemble(child, originalSource));
                // Update current to the character after the child's stop offset.
                current = child.getStop() + 1;
            }
            // Append any trailing text after the last child, up to the node's stop offset.
            if (current < node.getStop() + 1) {
                sb.append(originalSource, current, node.getStop() + 1);
            }
            return sb.toString();
        }
    }
}
