package com.mlesniak.markdown;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Defines a set of getter methods to retrieve properties about the
 * current version control status.
 */
@Service
public class VersionService {
    private Logger logger = LoggerFactory.getLogger(VersionService.class);
    private Properties info;

    @PostConstruct
    public void prepareMap() {
        info = new Properties();
        try {
            InputStream stream = getClass().getResourceAsStream("/git.properties");
            info.load(stream);
        } catch (IOException e) {
            logger.warn("Unable to load version information", e);
        }
    }

    private String get(String name) {
        return info.getProperty(name);
    }

    /**
     * Return the last commit id.
     *
     * @return commit id
     */
    public String getCommit() {
        return get("git.commit.id.abbrev");
    }

    /**
     * Return the build time.
     *
     * @return build time
     */
    public String getBuildTime() {
        return get("git.build.time");
    }
}
