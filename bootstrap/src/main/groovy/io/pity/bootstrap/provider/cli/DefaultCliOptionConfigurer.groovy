package io.pity.bootstrap.provider.cli

import io.pity.api.cli.CliOptionConfigurer

class DefaultCliOptionConfigurer implements CliOptionConfigurer {

    @Override
    void configureCli(CliBuilder cliBuilder) {
        cliBuilder._(longOpt: 'disable-env-collection', 'Disable collection of environmental data')
        cliBuilder._(longOpt: 'execute', args: 1, 'Execute the following command')
        cliBuilder._(longOpt: 'from', args: 1, 'The directory which you want to run against. By default this is the current directory.')
        cliBuilder._(longOpt: 'debug', 'Enable debug logging.')
        cliBuilder._(longOpt: 'ivy-log-info', 'Enable Ivy\'s logging at info.')
        cliBuilder._(longOpt: 'ivy-log-debug', 'Enable Ivy\'s logging at debug.')
        cliBuilder._(longOpt: 'silent', 'Disable all logging (except error).')
        cliBuilder.h(longOpt: 'help', 'Show usage information')
    }
}
