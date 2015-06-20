package io.pity.wrapper.ivy

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.apache.ivy.Ivy
import org.apache.ivy.core.module.descriptor.Configuration
import org.apache.ivy.core.module.descriptor.DefaultDependencyDescriptor
import org.apache.ivy.core.module.descriptor.DefaultModuleDescriptor
import org.apache.ivy.core.module.id.ModuleRevisionId
import org.apache.ivy.core.report.ArtifactDownloadReport
import org.apache.ivy.core.report.ResolveReport
import org.apache.ivy.core.resolve.ResolveOptions
import org.apache.ivy.core.settings.IvySettings
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Slf4j
@CompileStatic
class DependencyResolver {

    private static final Logger logger = LoggerFactory.getLogger(DependencyResolver.class)

    final Ivy ivyInstance;
    final DependencyConfiguration dependencyConfiguration

    public DependencyResolver(DependencyConfiguration dependencyConfiguration) {
        this(dependencyConfiguration, Ivy.newInstance(createIvySettings(dependencyConfiguration)))
    }

    DependencyResolver(DependencyConfiguration dependencyConfiguration, Ivy ivyInstance) {
        this.dependencyConfiguration = dependencyConfiguration
        this.ivyInstance = ivyInstance
    }

    List<URL> resolveDependencies() {
        if (dependencyConfiguration.dependencies.isEmpty()) {
            return [];
        }

        logger.info("Retrieving Dependencies...")

        DefaultModuleDescriptor md = createDefaultModuleDescriptor()

        addDependencies(md)

        ResolveOptions resolveOptions = new ResolveOptions()
            .setConfs(['default'] as String[])
            .setOutputReport(false)
            .setValidate(false)

        ResolveReport report = ivyInstance.resolve(md, resolveOptions)


        logger.info("Finished downloading {} dependencies", report.allArtifactsReports.size())
        logger.debug("Downloaded ${report.downloadSize >> 10} Kbytes in ${report.downloadTime}ms")
        logger.trace("${report.allArtifactsReports*.toString().join(', ')}")

        List<URI> artifacts = getUriFromReport(report)

        return artifacts.collect { artifact -> artifact.toURL() }
    }

    private List<URI> getUriFromReport(ResolveReport resolveReport) {
        List<URI> deps = new ArrayList<>()
        for(ArtifactDownloadReport report: resolveReport.allArtifactsReports) {
            if(null != report.localFile && report.localFile.exists()) {
                deps.add(report.localFile.toURI())
            }
        }

        return deps;
    }

    private void addDependencies(DefaultModuleDescriptor md) {
        for(Dependency dep: dependencyConfiguration.dependencies) {
            def mrid = ModuleRevisionId.newInstance(dep.group, dep.name, dep.version)
            def dd = new DefaultDependencyDescriptor(md, mrid, false, false, true)
            dd.addDependencyConfiguration('default', 'default')
            md.addDependency(dd)
        }
    }

    private DefaultModuleDescriptor createDefaultModuleDescriptor() {
        def millis = System.currentTimeMillis()
        def md = new DefaultModuleDescriptor(ModuleRevisionId
            .newInstance("caller", "all-caller", "working" + millis.toString()[-2..-1]), "integration", null, true)
        md.addConfiguration(new Configuration('default'))
        md.setLastModified(millis)
        return md
    }

    static private IvySettings createIvySettings(DependencyConfiguration dependencyConfiguration) {
        IvySettings ivySettings = new IvySettings();
        ivySettings.load(dependencyConfiguration.configurationFile)
        return ivySettings
    }
}
