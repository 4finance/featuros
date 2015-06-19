package io.fourfinanceit.featuros

import com.fasterxml.jackson.annotation.JsonRootName
import spock.lang.Specification

class JsonRootAwareRelProviderSpec extends Specification {

    JsonRootAwareRelProvider subject = new JsonRootAwareRelProvider()

    def 'should resolve relation from @JsonRootName(value)'() {
        expect:
            subject.getItemResourceRelFor(Bar) == 'bar'
            subject.getCollectionResourceRelFor(Bar) == 'bars'
    }

    def 'should use default rel provider for classes not annotated with @JsonRootName(value)'() {
        expect:
            subject.getItemResourceRelFor(Foo) == 'foo'
            subject.getCollectionResourceRelFor(Foo) == 'fooLists'
    }

    @JsonRootName(value = 'bar')
    private static class Bar {}

    private static class Foo {}

}
