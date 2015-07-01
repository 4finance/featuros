package io.fourfinanceit.featuros

import spock.lang.Specification

class MetadataSpec extends Specification {

    def 'should load metadata from given resource path'() {
        given:
            String resourcePath = '/featuros.properties'
        when:
            Map<String, Object> values = Metadata.load(resourcePath).get().asMap()
        then:
            values.name == 'test'
            values.product == 'test-product'
            values.group == 'test-group'
            values.version == 'test-version'
    }

    def 'should resolve to empty when only partial metadata is available'() {
        given:
            String resourcePath = '/featuros-partial.properties'
        when:
            Optional<Metadata> metadata = Metadata.load(resourcePath)
        then:
            !metadata.present
    }

    def 'should resolve to empty when no metadata is available'() {
        expect:
            !Metadata.load('/non-existent-42.properties').present
    }
}
