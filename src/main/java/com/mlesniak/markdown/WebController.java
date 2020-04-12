package com.mlesniak.markdown;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Optional;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {
    Logger LOG = LoggerFactory.getLogger(WebController.class);

    @ResponseBody
    @RequestMapping({ "/static/{name}", "favicon.ico" })
    // TODO optional name
    public ResponseEntity<byte[]> requestStaticfile(@PathVariable(name = "name", required = false) String name) throws IOException {
        try {
            
            return ResponseEntity.ok(Files.readAllBytes(Path.of("static/" + name)));
        } catch (NoSuchFileException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // TODO refactoring
    @ResponseBody
    @RequestMapping({ "/{name}", "/" })
    public ResponseEntity<byte[]> requestMarkdown(@PathVariable(name = "name", required = false) Optional<String> name) throws IOException {
        if (name.isEmpty()) {
            LOG.info("Root / requested");
        } else {
            LOG.info("File '{}' requested", name.get());
        }

        // Read content from markdown file.
        var filename = convertFilename(name);
        String content;
        try {
            content = Files.readString(Path.of(filename));
        } catch (NoSuchFileException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Parse to markdown.
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String output = renderer.render(document);

        // Inject markdown into pre-defined template.
        String template = Files.readString(Path.of("static/template.html"));
        template = template.replaceFirst("\\$\\$\\$", output);

        return ResponseEntity.ok(template.getBytes(Charset.defaultCharset()));
    }

    String convertFilename(Optional<String> name) {
        var filename = name.orElseGet(() -> "index.html");
        filename = filename.replaceAll("\\.html$", ".md");
        return filename;
    }
}
