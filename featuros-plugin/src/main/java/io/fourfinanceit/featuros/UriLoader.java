package io.fourfinanceit.featuros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Properties;

class UriLoader {

    private static final Logger log = LoggerFactory.getLogger(UriLoader.class);
    static final String URI_PROPERTY = "featuros.uri";
    static final String DEFAULT_URI = "http://localhost:31113";

    final String properties;

    public UriLoader() {
        this.properties = "/application.properties";
    }

    URI determineUri() {
        return loadUri().orElse(URI.create(DEFAULT_URI));
    }

    private Optional<URI> loadUri() {
        try (InputStream is = Featuros.class.getResourceAsStream(properties)) {
            if (is != null) {
                Properties applicationProperties = new Properties();
                applicationProperties.load(is);
                return Optional.of(new URI(applicationProperties.getProperty(URI_PROPERTY, DEFAULT_URI)));
            }
        } catch (IOException e) {
            log.error("Error reading from application.properties");
        } catch (URISyntaxException e) {
            log.error("Error in URI syntax", e);
        }

        return Optional.empty();
    }

    /**
     * Visible for testing!
     *
     * @param propertiesPath - test use only
     */
    UriLoader(String propertiesPath) {
        this.properties = propertiesPath;
    }
}
