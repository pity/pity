package io.ask.bootstrap.ivy

import org.apache.ivy.Ivy
import org.apache.ivy.core.module.descriptor.Configuration
import org.apache.ivy.core.module.descriptor.DefaultDependencyDescriptor
import org.apache.ivy.core.module.descriptor.DefaultModuleDescriptor
import org.apache.ivy.core.module.id.ModuleRevisionId
import org.apache.ivy.core.report.ResolveReport
import org.apache.ivy.core.resolve.ResolveOptions
import org.apache.ivy.core.settings.IvySettings
import org.apache.ivy.plugins.resolver.IBiblioResolver
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Resolver {

    private static final Logger logger = LoggerFactory.getLogger(Resolver.class)

    final DependencyConfiguration dependencyConfiguration
    final Ivy ivyInstance;

    public Resolver(DependencyConfiguration dependencyConfiguration) {
        this(dependencyConfiguration, Ivy.newInstance(createIvySettings(dependencyConfiguration)))
    }

    Resolver(DependencyConfiguration dependencyConfiguration, Ivy ivyInstance) {
        this.dependencyConfiguration = dependencyConfiguration
        this.ivyInstance = ivyInstance
    }

    void resolveDependencies() {
        if(dependencyConfiguration.dependencies.isEmpty()){
            return
        }

        DefaultModuleDescriptor md = createDefaultModuleDescriptor()

        dependencyConfiguration.dependencies.each {
            def mrid = ModuleRevisionId.newInstance(it.group, it.name, it.version)
            def dd = new DefaultDependencyDescriptor(md, mrid, false, false, true)
            dd.addDependencyConfiguration('default', '*')
            md.addDependency(dd)
        }

        ResolveOptions resolveOptions = new ResolveOptions()
            .setConfs(['default'] as String[])
            .setOutputReport(false)
            .setValidate(false)

        ResolveReport report = ivyInstance.resolve(md, resolveOptions)

        logger.info("Downloaded ${report.downloadSize >> 10} Kbytes in ${report.downloadTime}ms")
        logger.info("${report.allArtifactsReports*.toString().join(', ')}")

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
        // create an ivy instance
        IvySettings ivySettings = new IvySettings();
        if (dependencyConfiguration?.configurationFile?.exists()) {
            ivySettings.load(dependencyConfiguration.configurationFile)
        } else {
            IBiblioResolver br = new IBiblioResolver();
            br.setM2compatible(true);
            br.setUsepoms(true);
            br.setName("central");

            ivySettings.addResolver(br);
            ivySettings.setDefaultResolver(br.getName());
            ivySettings.setDefaultCache(dependencyConfiguration.cacheDir)
        }
        return ivySettings
    }
}
