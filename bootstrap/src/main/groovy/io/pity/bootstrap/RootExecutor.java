package io.pity.bootstrap;

import com.google.inject.Inject;
import io.pity.api.RootCollectorExecutor;
import java.util.Set;


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
