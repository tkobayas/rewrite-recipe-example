package org.example.lst;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.UUID;

import org.openrewrite.Checksum;
import org.openrewrite.Cursor;
import org.openrewrite.FileAttributes;
import org.openrewrite.PrintOutputCapture;
import org.openrewrite.SourceFile;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.lang.Nullable;
import org.openrewrite.marker.Markers;

public class MyLangSourceFile implements SourceFile {

    private final String originalSource;
    private final MyLangASTNode root;
    private final UUID id;
    private final Markers markers;
    private final Path sourcePath;
    private final Charset charset;
    private final boolean charsetBomMarked;
    private final Checksum checksum;
    private final FileAttributes fileAttributes;

    /**
     * Constructor using default values for markers, sourcePath, charset, BOM, checksum and file attributes.
     */
    public MyLangSourceFile(String originalSource, MyLangASTNode root, Path sourcePath) {
        this(
                originalSource,
                root,
                UUID.randomUUID(),
                Markers.EMPTY,
                sourcePath,
                StandardCharsets.UTF_8,
                false,
                null,
                null
        );
    }

    public MyLangSourceFile(String originalSource, MyLangASTNode root, UUID id, Path sourcePath) {
        this(
                originalSource,
                root,
                id,
                Markers.EMPTY,
                sourcePath,
                StandardCharsets.UTF_8,
                false,
                null,
                null
        );
    }

    /**
     * Full constructor.
     */
    public MyLangSourceFile(String originalSource,
                            MyLangASTNode root,
                            UUID id,
                            Markers markers,
                            Path sourcePath,
                            Charset charset,
                            boolean charsetBomMarked,
                            Checksum checksum,
                            FileAttributes fileAttributes) {
        this.originalSource = originalSource;
        this.root = root;
        this.id = id;
        this.markers = markers;
        this.sourcePath = sourcePath;
        this.charset = charset;
        this.charsetBomMarked = charsetBomMarked;
        this.checksum = checksum;
        this.fileAttributes = fileAttributes;
    }

    public String getOriginalSource() {
        return originalSource;
    }

    public MyLangASTNode getRoot() {
        return root;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Markers getMarkers() {
        return markers;
    }

    @Override
    public <T extends Tree> T withMarkers(Markers markers) {
        if (this.markers.equals(markers)) {
            return (T) this;
        }
        return (T) new MyLangSourceFile(originalSource, root, id, markers, sourcePath, charset, charsetBomMarked, checksum, fileAttributes);
    }

    @Override
    public <T extends Tree> T withId(UUID id) {
        if (this.id.equals(id)) {
            return (T) this;
        }
        return (T) new MyLangSourceFile(originalSource, root, id, markers, sourcePath, charset, charsetBomMarked, checksum, fileAttributes);
    }

    @Override
    public <P> boolean isAcceptable(TreeVisitor<?, P> v, P p) {
        // For simplicity, we consider this SourceFile acceptable to any visitor.
        return true;
    }

    @Override
    public Path getSourcePath() {
        return sourcePath;
    }

    @Override
    public <T extends SourceFile> T withSourcePath(Path path) {
        if ((sourcePath == null && path == null) || (sourcePath != null && sourcePath.equals(path))) {
            return (T) this;
        }
        return (T) new MyLangSourceFile(originalSource, root, id, markers, path, charset, charsetBomMarked, checksum, fileAttributes);
    }

    @Override
    public @Nullable Charset getCharset() {
        return charset;
    }

    @Override
    public <T extends SourceFile> T withCharset(Charset charset) {
        if (this.charset.equals(charset)) {
            return (T) this;
        }
        return (T) new MyLangSourceFile(originalSource, root, id, markers, sourcePath, charset, charsetBomMarked, checksum, fileAttributes);
    }

    @Override
    public boolean isCharsetBomMarked() {
        return charsetBomMarked;
    }

    @Override
    public <T extends SourceFile> T withCharsetBomMarked(boolean marked) {
        if (this.charsetBomMarked == marked) {
            return (T) this;
        }
        return (T) new MyLangSourceFile(originalSource, root, id, markers, sourcePath, charset, marked, checksum, fileAttributes);
    }

    @Override
    public @Nullable Checksum getChecksum() {
        return checksum;
    }

    @Override
    public <T extends SourceFile> T withChecksum(@Nullable Checksum checksum) {
        if ((this.checksum == null && checksum == null) || (this.checksum != null && this.checksum.equals(checksum))) {
            return (T) this;
        }
        return (T) new MyLangSourceFile(originalSource, root, id, markers, sourcePath, charset, charsetBomMarked, checksum, fileAttributes);
    }

    @Override
    public @Nullable FileAttributes getFileAttributes() {
        return fileAttributes;
    }

    @Override
    public <T extends SourceFile> T withFileAttributes(@Nullable FileAttributes fileAttributes) {
        if ((this.fileAttributes == null && fileAttributes == null) || (this.fileAttributes != null && this.fileAttributes.equals(fileAttributes))) {
            return (T) this;
        }
        return (T) new MyLangSourceFile(originalSource, root, id, markers, sourcePath, charset, charsetBomMarked, checksum, fileAttributes);
    }

    // For demonstration, simply returns the original source.
    // In a full lossless implementation, this would reassemble the source from the AST.
    @Override
    public String printAll() {
        return originalSource;
    }

    @Override
    public <P> TreeVisitor<?, PrintOutputCapture<P>> printer(Cursor cursor) {
        return new MyLangPrinter<>();
    }
}
