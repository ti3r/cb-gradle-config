package org.caringbridge.gradle.config

import org.gradle.api.Plugin;
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler

/**
 * Gradle plugin to apply common configurations in the 
 * gradle projects.
 * @author Alexandro Blanco <ablanco@caringbridge.org>
 */
class CbConfigPlugin implements Plugin<Project> {

    private final static String ARTIFACTORY_BASE_URL = 'http://jenkins.caringbridge.org:8081/artifactory/'
    
    def void apply(Project project){
        project.logger.info 'Applying common configurations for caringbridge.org'
        
        project.task('cbConfigVersion') << {
            println 'Caringbridge configuration common plugin version 1.0'
        }
        
        applyRepositories(project)
        
        
        project.buildscript.metaClass.caringbridge = { def closure = null ->
            repositories {
                maven {
                    url 'http://jenkins.caringbridge.org:8081/artifactory/plugins-snapshot/'
                }
                jcenter()
                mavenCentral()
            }
            dependencies {
                classpath "org.jfrog.buildinfo:build-info-extractor-gradle:4.0.0"
            }
        }
        
    }
    
    def applyRepositories(Project project){
        project.logger.info 'Adding caringbridge artifactory repositories'
            
        //Add the metainformation for adding the caringbridge repositories
        project.repositories.metaClass.caringbridge = {def closure = null ->
            delegate.maven {
                name 'cb-artifactory-libs-release'
                url ARTIFACTORY_BASE_URL+'libs-release'
            }
            delegate.maven {
                name 'cb-artifactory-libs-snapshot'
                url ARTIFACTORY_BASE_URL+'libs-snapshot'
            }
            delegate.maven {
                name 'cb-artifactory-plugins-release'
                url ARTIFACTORY_BASE_URL+'plugins-release'
            }
            delegate.maven {
                name 'cb-artifactory-plugins-snapshot'
                url ARTIFACTORY_BASE_URL+'plugins-snapshot'
            }
            delegate.maven {
                name 'jcenter'
                url 'https://jcenter.bintray.com/'
            }
            return true
        }
    }
}

