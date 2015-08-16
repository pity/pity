package io.pity.gradle.plugin

import groovy.json.JsonSlurper
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class DependenciesPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        if(target.getRootProject() != target) {
            throw new GradleException('Cannot apply dependency plugin to a non-root project')
        }
        target.ext.deps = ((Map) new JsonSlurper().parse(target.file('dependencies.json')).dependencies)
    }
}
