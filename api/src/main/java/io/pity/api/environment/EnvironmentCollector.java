package io.pity.api.environment;

import com.google.inject.Inject;

/**
 * Base class used for all environment collectors.
 */
public abstract class EnvironmentCollector {

    public final EnvironmentDataBuilder environmentDataBuilder;

    @Inject
    public EnvironmentCollector(Class<?> collectorClass){
        this.environmentDataBuilder = EnvironmentDataBuilder.Builder(collectorClass.getSimpleName());
    }

    public abstract boolean shouldCollect();
    public abstract void collect();

    final public EnvironmentData collectEnvironmentData() {

        if(shouldCollect()){
            collect();
        }
        return environmentDataBuilder.build();
    }
}
