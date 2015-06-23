package io.fourfinanceit.featuros.core

import org.springframework.hateoas.*
import spock.lang.Specification

class ResourceSupportSpec extends Specification {

    EntityLinks entityLinks = Mock(EntityLinks)
    ResourceSupport<Foo> subject = new ResourceSupport<>(Foo, entityLinks)

    def 'should make resource with self link'() {
        given:
            entityLinks.linkToSingleResource(_ as Foo) >> new Link("/foo", "self")
        when:
            Resource<Foo> resource = subject.resource(new Foo())
        then:
            resource.getLink('self').href == '/foo'
    }

    def 'should make collection resource with both self links and an aggregate'() {
        given:
            entityLinks.linkToSingleResource(_ as Foo) >> new Link("/foo", "self")
            entityLinks.linkToCollectionResource(Foo) >> new Link("/foos", "self")
        when:
            Resources<Resource<Foo>> resources = subject.collection([new Foo(), new Foo()])
        then:
            resources.getLink('self').href == '/foos'
            resources.content.size() == 2
            resources.content.every { it.getLink('self').href == '/foo' }
    }

    private static class Foo implements Identifiable<Long> {
        @Override
        Long getId() {
            5
        }
    }
}
