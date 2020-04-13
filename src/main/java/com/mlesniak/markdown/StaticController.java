package com.mlesniak.markdown;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Serve static files.
 */
@Controller
public class StaticController {
    Logger LOG = LoggerFactory.getLogger(StaticController.class);

    /**
     * Handler to serve all static files and the favicon.
     *
     * @param name requested name inside the static/ directory.
     * @return content
     * @throws IOException
     */
    @ResponseBody
    @GetMapping({"/static/{name}", "favicon.ico"})
    public ResponseEntity<byte[]> requestStaticfile(
            @PathVariable(name = "name", required = false) String name) throws IOException {
        try {
            String directory = "static/";
            byte[] data = Files.readAllBytes(Path.of(directory + name));
            return ResponseEntity.ok(data);
        } catch (NoSuchFileException e) {
            LOG.info("Requested static file {} not found", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
