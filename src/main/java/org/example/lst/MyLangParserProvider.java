package org.example.lst;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.jspecify.annotations.Nullable;
import org.openrewrite.ExecutionContext;
import org.openrewrite.SourceFile;
import org.openrewrite.Parser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MyLangParserProvider implements Parser {

    @Override
    public Stream<SourceFile> parseInputs(Iterable<Input> sources, @Nullable Path relativeTo, ExecutionContext ctx) {
        List<SourceFile> sourceFiles = new ArrayList<>();
        for (Input input : sources) {
            String source = input.getSource(ctx).readFully();
            // Create a CharStream from the source text.
            CharStream charStream = CharStreams.fromString(source);
            // Instantiate the ANTLR-generated lexer and parser.
            MyLangLexer lexer = new MyLangLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MyLangParser antlrParser = new MyLangParser(tokens);
            // Parse the input according to the 'program' rule.
            ParserRuleContext tree = antlrParser.program();

            // Build the lossless AST using our custom visitor.
            MyLangAstBuilder builder = new MyLangAstBuilder();
            MyLangASTNode root = builder.build(tree);

            // Wrap the original source and AST in a SourceFile.
            MyLangSourceFile sourceFile = new MyLangSourceFile(source, root);
            sourceFiles.add(sourceFile);
        }
        return sourceFiles.stream();
    }

    @Override
    public boolean accept(Path path) {
        // For this example, only accept files with the ".mylang" extension.
        return path.toString().endsWith(".mylang");
    }

    @Override
    public Path sourcePathFromSourceText(Path prefix, String sourceCode) {
        // For demonstration, generate a fixed filename.
        // In a production setting, you could derive this based on the source content (e.g. via hashing).
        return prefix.resolve("MyLangSource.mylang");
    }
}

