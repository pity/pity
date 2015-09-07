package io.pity.api.reporting;

import io.pity.api.StopExecutionException;


/**
 * Interface that is used to publish a single result. There can be only a single call of this type. To use a new
 * reporter, create a property file in <code>META-INF/pity-plugins/*.properties</code>. The property file also needs to
 * define a value <code>default.publisher</code>
 */
public interface ReportPublisher {

    /**
     *
     * Called after all the results have been collected. This is where you would publish to Jira or a Github Issue for
     * example.
     *
     * @param collectedResults results that the collectors found.
     */
    void publishReport(CollectionResults collectedResults);

    /**
     * This method is used to ensure that the {@link ReportPublisher} has all the required parameters. This can be
     * useful if you need to provide a ticket number before publishing can take place.
     *
     * If the requirements aren't met, then you should throw a {@link StopExecutionException} to stop the program.
     * It will be caught, and the message will be displayed to the user, allowing them to correct the issues.
     *
     * @throws StopExecutionException
     */
    void validateRequirements() throws StopExecutionException;
}
