package io.fourfinanceit.featuros;

import jodd.http.HttpException;
import jodd.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Optional;

import static jodd.http.HttpRequest.post;

/**
 * Plugin that reaches out to featuros dashboard and notifies of current deployment.
 * When not using a boot-starter module, it has to be called manually, e.g.:
 * <pre>
 *     public static void main(String... args) {
 *         SpringApplication.run(args);
 *         Featuros.notification();
 *     }
 * </pre>
 */
public class Featuros {

    private static final Logger log = LoggerFactory.getLogger(Featuros.class);


    /**
     * Notifies featuros dashboard using defaults:
     * <ol>
     * <li><code>featuros.uri</code> - from <code>application.properties</code> (default: <code>http://localhost:31113</code>)</li>
     * <li>metadata - from <code>featuros.properties</code></li>
     * </ol>
     */
    public static void notification() {
        notification(new UriLoader().determineUri());
    }

    /**
     * Notifies featuros dashboard running at the specified URI
     *
     * @param uri - featuros dashboard URI
     * @see #notification()
     */
    public static void notification(URI uri) {
        log.info("Using featuros.uri = {}", uri);

        Optional<Metadata> metadata = Metadata.load("/featuros.properties");
        if (!metadata.isPresent()) {
            log.error("Featuros plugin is on classpath, but 'featuros.properties' is missing or incomplete");
        }

        try {
            metadata.ifPresent(m -> {
                HttpResponse response = post(uri.toString())
                        .path("api/notifications")
                        .form(m.asMap())
                        .send();
                log.debug(response.toString());
            });
        } catch (HttpException e) {
            log.error("Could not connect to featuros dashboard. Nested exception is:", e);
        }
    }

}
