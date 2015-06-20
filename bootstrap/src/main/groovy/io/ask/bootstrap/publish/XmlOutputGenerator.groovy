package io.ask.bootstrap.publish
import groovy.util.logging.Slf4j
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import io.ask.api.environment.EnvironmentData
import io.ask.api.execution.CommandExecutionResult
import io.ask.api.reporting.CollectionResults

@Slf4j
class XmlOutputGenerator {

    Set<EnvironmentData> envData;
    List<CommandExecutionResult> executionData;

    XmlOutputGenerator(CollectionResults collectedResults) {
        this.envData = collectedResults.getEnvironmentData()
        this.executionData = collectedResults.getCommandExecutionResults()
    }

    String createXmlString() {
        def xmlMarkup = new StreamingMarkupBuilder()
        def result = xmlMarkup.bind { binding ->
            results {
                environment {
                    envData.each { collector ->
                        binding.collector(name: "$collector.collectorName") {
                            collector.environmentResults.each { envResult ->
                                binding.entry(name: envResult.key, (envResult.value as String)?.replace(27 as char, '^' as char))
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
                                    binding.data(name: key){ mkp.yieldUnescaped "<![CDATA[${value}]]>" }
                                }
                            }
                        }
                    }
                }
            }
        }

        return XmlUtil.serialize(result.toString())
    }

    void writeToFile(File file) {
        file.text = createXmlString()
    }
}
