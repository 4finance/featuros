package io.fourfinanceit.featuros

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction

class FeaturosMetadata implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("featuros", FeaturosMetadataExtension)
        Task setupMetadata = project.task('setupMetadata', type: SetupMetadataTask)
        project.tasks.processResources.dependsOn(setupMetadata)
    }
}

class FeaturosMetadataExtension {
    String name
    String product
    String group
    String version
}

class SetupMetadataTask extends DefaultTask {

    @TaskAction
    def writeMetadataToFile() {
        Properties metadata = prepareMetadata()
        getLogger().debug('Writing properties to featuros/featuros.properties: {}', metadata)
        File outputFile = prepareOutputFile()
        metadata.store(outputFile.newOutputStream(), null)
        project.jar {
            from outputFile
        }
    }

    private File prepareOutputFile() {
        File outputFile = project.file("$project.buildDir/featuros/featuros.properties")
        outputFile.parentFile.mkdirs()
        outputFile
    }

    private Properties prepareMetadata() {
        Project project = project
        Set<String> featurosProperties = ['name', 'product', 'group', 'version']
        new Properties().with {
            featurosProperties.each { prop ->
                if (!project.featuros[prop]) {
                    throw new ProjectConfigurationException("Property '$prop' not configured in project 'featuros' closure", null)
                }
                setProperty(prop, project.featuros[prop])
            }
            it
        }
    }
}
