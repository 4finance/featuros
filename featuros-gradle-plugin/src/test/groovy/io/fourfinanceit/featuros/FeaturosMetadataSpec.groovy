package io.fourfinanceit.featuros

import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.tasks.TaskExecutionException
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class FeaturosMetadataSpec extends Specification {

    private Project project

    def 'should add setupMetadata task'() {
        given:
            project = projectWithPlugin()
        expect:
            project.tasks.setupMetadata instanceof SetupMetadataTask
    }

    def 'processResources task should depend on setupMetadata task'() {
        given:
            project = projectWithPlugin()
        expect:
            project.tasks.setupMetadata in project.tasks.processResources.dependsOn
    }

    def 'should throw an exception when configuration is missing'() {
        given:
            project = projectWithPlugin()
        when:
            executeTask()
        then:
            def ex = thrown(TaskExecutionException.class)
            ex.cause instanceof ProjectConfigurationException
    }

    def 'should complete when configuration is present'() {
        given:
            project = configuredProject()
        when:
            executeTask()
        then:
            'featuros.properties' in jarSources()
    }

    private List jarSources() {
        project.jar.source.collect { it.name }
    }

    Project configuredProject() {
        Project project = projectWithPlugin()
        project.featuros.name = 'qualified-name'
        project.featuros.product = 'name'
        project.featuros.group = 'deployment-group'
        project.featuros.version = '0.1'
        project
    }

    Project projectWithPlugin() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply('java')
        project.pluginManager.apply('io.fourfinanceit.featuros')
        project
    }

    def executeTask() {
        project.tasks.setupMetadata.execute()
    }

}
