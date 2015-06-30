package io.fourfinanceit.featuros

import spock.lang.Specification

import static io.fourfinanceit.featuros.UriLoader.DEFAULT_URI

class UriLoaderSpec extends Specification {

    def 'should determine uri from application.properties'() {
        given:
            URI uri = new UriLoader().determineUri()
        expect:
            uri.toString() == 'http://google.com'
    }

    def 'should use defaults when property is not available or malformed'() {
        expect:
            new UriLoader('/application-empty.properties').determineUri().toString() == DEFAULT_URI
            new UriLoader('/application-wrong.properties').determineUri().toString() == DEFAULT_URI
            new UriLoader('/non-existent-42.properties').determineUri().toString() == DEFAULT_URI
    }

}
