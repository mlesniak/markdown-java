package com.mlesniak.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebControllerTest {
    Logger LOG = LoggerFactory.getLogger(WebControllerTest.class);

    @Autowired
    private WebController wc;

    @Test
    public void convertFilename() {
        // Root and main html.
        checkFilename(null, "index.md");
        checkFilename("index.html", "index.md");

        // Normal files.
        checkFilename("foo.html", "foo.md");

        // Cornes cases.
        checkFilename("foo-html-explanation.html", "foo-html-explanation.md");
        // checkFilename("foo.html-explanation.html", "foo.html-explanation.md");
    }

    private void checkFilename(String parameter, String expected) {
        Optional<String> option = Optional.empty();
        if (parameter != null) {
            option = Optional.of(parameter);
        }

        var name = wc.convertFilename(option);
        LOG.info("Comparing source={} with result={} against expected={}", parameter, name, expected);
        assertEquals(expected, name);
    }
}