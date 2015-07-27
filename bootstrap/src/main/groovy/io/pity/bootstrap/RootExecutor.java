package io.pity.bootstrap;

import com.google.inject.Inject;
import io.pity.api.RootCollectorExecutor;
import java.util.Set;


/**
 * Can be used to add a new "RootExecutor"
 */
public class RootExecutor {

    private final Set<RootCollectorExecutor> collectorExecutorSet;

    @Inject
    RootExecutor(Set<RootCollectorExecutor> collectorExecutorSet){
        this.collectorExecutorSet = collectorExecutorSet;
    }

    public void executeAll() {
        for (RootCollectorExecutor rootCollectorExecutor : collectorExecutorSet) {
            rootCollectorExecutor.execute();
        }
    }
}
