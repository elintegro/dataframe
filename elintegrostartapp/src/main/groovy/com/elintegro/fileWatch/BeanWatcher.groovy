/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.fileWatch

import grails.spring.BeanBuilder
import grails.util.Environment
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.grails.core.exceptions.GrailsConfigurationException
import org.grails.spring.DefaultRuntimeSpringConfiguration
import org.grails.spring.RuntimeSpringConfigUtilities
import org.springframework.beans.factory.support.BeanDefinitionRegistry

import java.nio.file.FileSystems
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.nio.file.WatchService
import java.nio.file.attribute.BasicFileAttributes
import java.util.concurrent.atomic.AtomicBoolean

@Slf4j
class BeanWatcher extends Thread{

    private final WatchService watchService
    private long sleepTime = 1000
    private AtomicBoolean stop = new AtomicBoolean(false)

    public BeanWatcher(Path path){
        watchService = FileSystems.getDefault().newWatchService()
        walkAndRegisterDirectories(path)

    }

    private static void onChange(File file) {
        processBeanDefinitionRegistry()
    }

    @Override
    void run() {

        try {
            WatchKey key
            try {
                while ((key = watchService.take()) != null) {
                    List<WatchEvent<?>> watchEvents = key.pollEvents()
                    for (WatchEvent<?> event : watchEvents) {
                        WatchEvent.Kind<?> kind = event.kind()
                        WatchEvent<Path> pathWatchEvent = cast(event)
                        Path name = pathWatchEvent.context()
                        Path dir = (Path) key.watchable()
                        Path child = dir.resolve(name).toAbsolutePath()
                        File childFile = child.toFile()
                        if(kind == StandardWatchEventKinds.ENTRY_MODIFY){
                            onChange(childFile)
                        }
                    }
                    key.reset()
                }
            } catch (InterruptedException e) {
                e.printStackTrace()
            }
        } catch (IOException e) {
            e.printStackTrace()
        }

    }

    static void configureBeanWatcher(){
        Environment environment = Environment.current
        File baseDir = new File(environment.getReloadLocation()).canonicalFile
        String location = baseDir.canonicalPath
        File watchDir = new File(location, "grails-app/conf/spring")
        Path path = watchDir.toPath()
        BeanWatcher beanWatcher = new BeanWatcher(path)
        beanWatcher.start()
    }

    private void registerDirectory(dir){

        dir.register(
                watchService,
                StandardWatchEventKinds.ENTRY_MODIFY)
    }

    private void walkAndRegisterDirectories(final Path start){
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs){
                registerDirectory(dir)
                return FileVisitResult.CONTINUE
            }
        })
    }

    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event
    }

    public static void processBeanDefinitionRegistry(){
        def springConfig = new DefaultRuntimeSpringConfiguration()
        def application = Holders.grailsApplication
        def context = application.mainContext
        def beanResources = context.getResource(RuntimeSpringConfigUtilities.SPRING_RESOURCES_GROOVY)
        if (beanResources?.exists()) {
            def gcl = new GroovyClassLoader(application.classLoader)
            try {
                RuntimeSpringConfigUtilities.reloadSpringResourcesConfig(springConfig, application, gcl.parseClass(new GroovyCodeSource(beanResources.URL)))
            } catch (Throwable e) {
                log.error("Error loading spring/resources.groovy file: ${e.message}", e)
                throw new GrailsConfigurationException("Error loading spring/resources.groovy file: ${e.message}", e)
            }
        }
        def bb = new BeanBuilder(null, springConfig, application.classLoader)
        bb.registerBeans((BeanDefinitionRegistry)application.getMainContext())
    }
}
