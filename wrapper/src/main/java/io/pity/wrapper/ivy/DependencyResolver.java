package io.pity.wrapper.ivy;

import org.apache.ivy.Ivy;
import org.apache.ivy.core.module.descriptor.Configuration;
import org.apache.ivy.core.module.descriptor.DefaultDependencyDescriptor;
import org.apache.ivy.core.module.descriptor.DefaultModuleDescriptor;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ArtifactDownloadReport;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DependencyResolver {

    private static final Logger logger = LoggerFactory.getLogger(DependencyResolver.class);
    private final Ivy ivyInstance;
    private final DependencyConfiguration dependencyConfiguration;

    public DependencyResolver(DependencyConfiguration dependencyConfiguration) throws IOException, ParseException {
        this(dependencyConfiguration, Ivy.newInstance(createIvySettings(dependencyConfiguration)));
    }

    public DependencyResolver(DependencyConfiguration dependencyConfiguration, Ivy ivyInstance) {
        this.dependencyConfiguration = dependencyConfiguration;
        this.ivyInstance = ivyInstance;
    }

    public List<URL> resolveDependencies() throws IOException, ParseException {
        if (dependencyConfiguration.getDependencies().isEmpty()) {
            return Collections.emptyList();
        }

        logger.info("Retrieving Dependencies...");

        DefaultModuleDescriptor md = createDefaultModuleDescriptor();
        addDependencies(md);
        ResolveOptions resolveOptions = new ResolveOptions().setConfs(new String[]{"default"}).setOutputReport(false).setValidate(false);

        final ResolveReport report = ivyInstance.resolve(md, resolveOptions);

        logger.info("Finished downloading {} dependencies", report.getAllArtifactsReports().length);
        logger.debug("Downloaded {} Kbytes in {} ms", report.getDownloadSize() >> 10, report.getDownloadTime());
        logger.trace(createTraceMessage(report.getAllArtifactsReports()));

        List<URL> artifacts = getUriFromReport(report);
        return artifacts;
    }

    String createTraceMessage(ArtifactDownloadReport[] reports){
        StringWriter stringWriter = new StringWriter();
        for (ArtifactDownloadReport report : reports) {
            stringWriter.append(report.toString());
            stringWriter.append(", ");
        }
        return stringWriter.toString();
    }

    private List<URL> getUriFromReport(ResolveReport resolveReport) throws MalformedURLException {
        List<URL> deps = new ArrayList<URL>();
        for (ArtifactDownloadReport report : resolveReport.getAllArtifactsReports()) {
            if (null != report.getLocalFile() && report.getLocalFile().exists()) {
                deps.add(report.getLocalFile().toURI().toURL());
            }
        }

        return deps;
    }

    private void addDependencies(DefaultModuleDescriptor md) {
        for (Dependency dep : dependencyConfiguration.getDependencies()) {
            ModuleRevisionId mrid = ModuleRevisionId.newInstance(dep.getGroup(), dep.getName(), dep.getVersion());
            DefaultDependencyDescriptor dd = new DefaultDependencyDescriptor(md, mrid, false, false, true);
            dd.addDependencyConfiguration("default", "default");
            md.addDependency(dd);
        }

    }

    private DefaultModuleDescriptor createDefaultModuleDescriptor() {
        long millis = System.currentTimeMillis();
        ModuleRevisionId moduleRevisionId = ModuleRevisionId.newInstance("caller", "all-caller", "working" + millis);
        DefaultModuleDescriptor md = new DefaultModuleDescriptor(moduleRevisionId, "integration", null, true);
        md.addConfiguration(new Configuration("default"));
        md.setLastModified(millis);
        return md;
    }

    private static IvySettings createIvySettings(DependencyConfiguration dependencyConfiguration) throws IOException, ParseException {
        IvySettings ivySettings = new IvySettings();
        ivySettings.load(dependencyConfiguration.getConfigurationFile());
        return ivySettings;
    }
}
