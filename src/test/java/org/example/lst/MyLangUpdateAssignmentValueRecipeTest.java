package org.example.lst;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.openrewrite.test.RewriteTest;
import org.openrewrite.test.SourceSpec;
import org.openrewrite.test.SourceSpecs;

class MyLangUpdateAssignmentValueRecipeTest implements RewriteTest {

    @Test
    void updateAssignmentValue() {
        rewriteRun(
          spec -> spec
            .recipe(new MyLangUpdateAssignmentValueRecipe())
            .parser(MyLangParserProvider.builder()),
          // Supply a source spec via a lambda that configures the before/after text and file path.
          mylang("x = 42;",
            "x = 43;")
        );
    }

    public static SourceSpecs mylang(@Nullable String before, @Nullable String after) {
        SourceSpec<MyLangSourceFile> spec = new SourceSpec<>(MyLangSourceFile.class, null, MyLangParserProvider.builder(), before, s -> after);
        return spec;
    }
}
