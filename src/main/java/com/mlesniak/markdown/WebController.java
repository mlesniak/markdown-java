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

    /**
     * Handler to serve all static files and the favicon.
     *
     * @param name requested name inside the static/ directory.
     * @return content
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping({ "/static/{name}", "favicon.ico" })
    public ResponseEntity<byte[]> requestStaticfile(@PathVariable(name = "name", required = false) String name) throws IOException {
        try {
            return ResponseEntity.ok(Files.readAllBytes(Path.of("static/" + name)));
        } catch (NoSuchFileException e) {
            LOG.info("Requested static file {} not found", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Handler for all markdown files.
     *
     * @param name requested file (with .html suffix)
     * @return content
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping({ "/{name}", "/" })
    public ResponseEntity<byte[]> requestContentFile(@PathVariable(name = "name", required = false) Optional<String> name) throws IOException {
        Optional<String> ocontent = getMarkdown(name);
        if (ocontent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String content = ocontent.get();

        String html = toHTML(content);
        String htmlInTemplate = useTemplate(html);

        return ResponseEntity.ok(htmlInTemplate.getBytes(Charset.defaultCharset()));
    }

    /**
     * Return markdown for a given filename.
     *
     * @param name
     *                 the filename. If no filename is given, index.md is used.
     * @return the content of the markdown file or empty, if no file was found.
     */
    private Optional<String> getMarkdown(Optional<String> name) {
        if (name.isEmpty()) {
            LOG.info("Root / requested");
        } else {
            LOG.info("File '{}' requested", name.get());
        }
        var filename = convertFilename(name);
        try {
            return Optional.of(Files.readString(Path.of(filename)));
        } catch (NoSuchFileException e) {
            LOG.info("Content file {} not found", filename);
            return Optional.empty();
        } catch (IOException e) {
            LOG.info("IO error on file {}", filename, e);
            return Optional.empty();
        }
    }

    /**
     * Injects the html content of the converted markdown into the default template
     * file by replacing $$$ with the converted content.
     *
     * @param html
     *                 the markdown html
     * @return a full-featured html file
     * @throws IOException
     */
    private String useTemplate(String html) throws IOException {
        String template = Files.readString(Path.of("static/template.html"));
        template = template.replaceFirst("\\$\\$\\$", html);
        return template;
    }

    /**
     * Convert markdown to HTML.
     *
     * @param markdown source markdown
     * @return converted html
     */
    private String toHTML(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(false).build();
        String output = renderer.render(document);
        return output;
    }

    /**
     * Convert the name of a requested html file to its pendant in markdown by replacing the last .html suffix with .md.
     *
     * @param name requested filename.
     * @return markdown filename.
     */
    String convertFilename(Optional<String> name) {
        var filename = name.orElseGet(() -> "index.html");
        filename = filename.replaceAll("\\.html$", ".md");
        return filename;
    }
}
