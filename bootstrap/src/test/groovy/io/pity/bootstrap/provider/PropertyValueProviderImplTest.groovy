package io.pity.bootstrap.provider

import spock.lang.Specification

class PropertyValueProviderImplTest extends Specification {
    def 'adding multiple properties file override values'() {
        setup:
        def firstPropertyFile = new Properties()
        firstPropertyFile.put('override', 'nope')
        firstPropertyFile.put('value1', 'value1')

        def secondPropertyFile = new Properties()
        secondPropertyFile.put('override', 'override')
        secondPropertyFile.put('value2', 'value2')

        when:
        def provider = new PropertyValueProviderImpl([firstPropertyFile, secondPropertyFile])

        then:
        provider.getProperty('value1') == 'value1'
        provider.getProperty('value2') == 'value2'
        provider.getProperty('override') == 'override'
    }
}
