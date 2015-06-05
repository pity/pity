package io.ask.bootstrap

import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import io.ask.api.environment.EnvironmentData
import io.ask.api.execution.CommandExecutionResults

class OutputGenerator {
    Set<EnvironmentData> envData;
    List<CommandExecutionResults> executionData;

    OutputGenerator(Set<EnvironmentData> envData, List<CommandExecutionResults> executionData) {
        this.envData = envData
        this.executionData = executionData
    }

    String createXmlString() {
        def xmlMarkup = new StreamingMarkupBuilder()
        def result = xmlMarkup.bind { binding ->
            results {
                environment {
                    envData.each { collector ->
                        binding.collector(name: "$collector.collectorName") {
                            collector.environmentResults.each { envResult ->
                                binding.entry(name: envResult.key, envResult.value)
                            }
                        }
                    }
                }
                execution {
                    executionData.each { execution ->
                        binding.'execution-result'(name: execution.commandExecutorClass) {
                            binding.stdOut(execution.stdOut)
                            binding.stdErr(execution.stdError)
                            binding.'command-executed'(execution.commandExecuted)
                            binding.'result-dir'(execution.resultDir.absolutePath)
                            binding.'exception-thrown'(execution.exceptionThrown)
                            binding.'other-data' {
                                execution.otherResults.each { key, value ->
                                    binding.data(name: key, value)
                                }
                            }
                        }
                    }
                }
            }
        }

        return XmlUtil.serialize(result)
    }

    void writeToString(File file) {
        file.text = createXmlString()
    }
}
