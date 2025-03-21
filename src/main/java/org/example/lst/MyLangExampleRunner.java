package org.example.lst;

import org.openrewrite.ExecutionContext;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.SourceFile;
import org.openrewrite.Parser.Input;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Stream;

public class MyLangExampleRunner {

    public static void main(String[] args) {
        // Sample source in our simple language.
        String source = "x = 42;";

        // Create an Input implementation for this source.
        Input input = Input.fromString(source);

        // Create a parser provider instance.
        MyLangParserProvider parserProvider = new MyLangParserProvider();
        ExecutionContext ctx = new InMemoryExecutionContext();

        // Parse the input source.
        Stream<SourceFile> sourceFiles = parserProvider.parseInputs(Collections.singletonList(input), null, ctx);

        // Process the resulting SourceFile(s).
        sourceFiles.forEach(sf -> {
            System.out.println("Parsed Source:");
            System.out.println(sf.printAll());

            // If the source file is our custom type, print some AST details.
            if (sf instanceof MyLangSourceFile) {
                MyLangSourceFile myLangSourceFile = (MyLangSourceFile) sf;
                System.out.println("AST Root Text: " + myLangSourceFile.getRoot().getText());
            }
        });
    }
}
