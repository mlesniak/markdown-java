package com.mlesniak.markdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    Logger LOG = LoggerFactory.getLogger(Controller.class);

    @RequestMapping("/{name}")
    public String requestMarkdown(@PathVariable("name") String name) {
        LOG.info("Markdown file {} requested", name);
        return String.format("<%s>", name);
    }
}