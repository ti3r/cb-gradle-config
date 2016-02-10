
import org.gradle.api.Project
import org.gradle.api.Task
import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import java.lang.Boolean
import static org.junit.Assert.assertTrue
/**
 * Test to check the default task added by the plugin
 * @author Alexandro Blanco <ablanco@caringbridge.org>
 */
class CbPluginTaskTest {
	
    @Test
    public void testProjectBuild(){
        Project project = ProjectBuilder.builder().build()
        project.repositories.clear()
        project.apply plugin: 'cbgradle'
        
        project.buildscript { caringbridge() }
        
        project.repositories { caringbridge() }
        
        //project{ caringbridgeArtifactory() }
        
        //Two repositories should be added to the repositories
        assertTrue project.repositories.findAll().size() >= 2
        
        project.repositories.each { it ->
            println it.name
        }
        
    }
    
    
    def testArtifact(Map others = [:], Project project, String aGroup, String aName, String aVersion) {
        def dep = project.dependencies.create(group: aGroup, name: aName, version: aVersion) {
            artifact {
                name = others.artifact ?: aName
                classifier = others.classifier
                type = others.type ?: 'jar'
            }
        }
        def file = project.configurations.detachedConfiguration(dep).singleFile
        println file
        assertTrue file.isFile()
    }
}

