package io.ask.bootstrap.ivy
import org.apache.ivy.Ivy
import org.apache.ivy.core.module.descriptor.DefaultDependencyDescriptor
import org.apache.ivy.core.module.descriptor.DefaultModuleDescriptor
import org.apache.ivy.core.module.id.ModuleRevisionId
import org.apache.ivy.core.settings.IvySettings

class IvyResolver {
    final IvyDependencies ivy

    public IvyResolver(IvyDependencies ivy) {
        this.ivy = ivy
    }

    void resolveDependencies() {
        IvySettings ivySettings = new IvySettings();
        ivySettings.setDefaultCache(new File(System.getProperty("user.home"), '.askit'))
        ivySettings.load(new File(ivy.configurationLocation))

        def ivy = Ivy.newInstance(ivySettings)

        DefaultModuleDescriptor md = DefaultModuleDescriptor
            .newDefaultInstance(ModuleRevisionId.newInstance(dep[0], dep[1] + "-caller"));
        DefaultDependencyDescriptor dd = new DefaultDependencyDescriptor(md,
            ModuleRevisionId.newInstance(dep[0], dep[1], dep[2]), false, false, true);

    }
}
