package com.mlesniak.markdown;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {
    Logger LOG = LoggerFactory.getLogger(WebController.class);

    @ResponseBody
    @RequestMapping({ "/{name}", "/" })
    public byte[] requestMarkdown(@PathVariable(name = "name", required = false) Optional<String> name)
            throws IOException {
        LOG.info("Markdown file '{}' requested", name);

        // Read content from markdown file.
        var filename = convertFilename(name);
        var content = Files.readString(Path.of(filename));

        // Parse to markdown.
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String output = renderer.render(document);

        return output.getBytes(Charset.defaultCharset());
    }

    String convertFilename(Optional<String> name) {
        var filename = name.orElseGet(() -> "index.html");
        filename = filename.replaceAll("\\.html$", ".md");
        return filename;
    }
}
