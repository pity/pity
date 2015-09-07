package io.pity.tasks.collector
import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.pity.api.environment.EnvironmentCollector

@CompileStatic
class EnvironmentVariableCollector extends EnvironmentCollector {

    final Map<String, String> envVariables;

    @Inject
    EnvironmentVariableCollector() {
        this(System.getenv())
    }

    EnvironmentVariableCollector(Map<String, String> env) {
        super(EnvironmentVariableCollector.class)
        this.envVariables = env;
    }

    @Override
    boolean shouldCollect() {
        return true;
    }

    @Override
    void collect() {
        envVariables
                .findAll { key, value -> !key.startsWith('_') }
                .each { key, value -> environmentDataBuilder.addData(key, value) }
    }
}
