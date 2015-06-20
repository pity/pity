import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.classic.Level

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}
logger('org.reflections.vfs', Level.ERROR)
logger('org.reflections', Level.ERROR)
root(Level.INFO, ["STDOUT"])
