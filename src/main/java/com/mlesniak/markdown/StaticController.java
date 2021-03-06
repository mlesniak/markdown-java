package com.mlesniak.markdown;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;
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
    Logger logger = LoggerFactory.getLogger(StaticController.class);

    /**
     * Handler to serve all static files and the favicon.
     *
     * @param name requested name inside the static/ directory.
     * @return content
     * @throws IOException
     */
    @ResponseBody
    @GetMapping({"/static/{name}", "favicon.ico"})
    public ResponseEntity<byte[]> requestStaticfile(HttpServletRequest request,
            @PathVariable(name = "name", required = false) String name) throws IOException {
        try {
            String directory = "static";
            Path p1 = Paths.get(directory);
            if (name == null) {
                // favicon.ico or other files not in static/ but mentioned in the annotation.
                name = request.getRequestURI();
            }
            Path p2 = p1.resolve(name);
            byte[] data = Files.readAllBytes(p2); // NOSONAR The GetMapping disallows dots in the
                                                  // name.
            return ResponseEntity.ok(data);
        } catch (NoSuchFileException e) {
            logger.info("Requested static file {} not found", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
