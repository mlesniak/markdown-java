package com.mlesniak.markdown;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {
    Logger LOG = LoggerFactory.getLogger(WebController.class);

    @RequestMapping("/{name}")
    @ResponseBody
    public byte[] requestMarkdown(@PathVariable("name") String name) throws IOException {
        LOG.info("Markdown file '{}' requested", name);
        var content = Files.readString(Path.of(name));
        return content.getBytes(Charset.defaultCharset());
    }
}
