package io.pity.wrapper
import groovy.transform.CompileStatic
import io.pity.wrapper.ivy.DependencyConfiguration
import io.pity.wrapper.ivy.DependencyResolver
import io.pity.wrapper.ivy.IvyConfiguration
import org.codehaus.groovy.tools.RootLoader

@CompileStatic
public class WrapperMain {

    public static void main(String[] args) {

        def ivyConfiguration = new IvyConfiguration()
        ivyConfiguration.update()
        def configuration = new DependencyConfiguration(ivyConfiguration.ivySettingUrl, null, ivyConfiguration.dependencies)

        def urls = new DependencyResolver(configuration).resolveDependencies().toArray(new URL[0])
        def rootLoader = new RootLoader(urls, WrapperMain.getClassLoader())

        def mainMethod = rootLoader.loadClass('io.pity.bootstrap.AskBootstrapMain').getDeclaredMethod('main', args.getClass())
        mainMethod.invoke(null, args)
    }
}
