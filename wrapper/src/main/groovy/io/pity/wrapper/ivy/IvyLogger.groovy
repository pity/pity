package io.pity.wrapper.ivy

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import org.apache.ivy.util.AbstractMessageLogger
import org.apache.ivy.util.Message
import org.slf4j.LoggerFactory

class IvyLogger extends AbstractMessageLogger {

    Logger log = ((Logger) LoggerFactory.getLogger(IvyLogger));

    IvyLogger(String[] args) {
        if(args.contains('--ivy-log-info')) {
            log.setLevel(Level.INFO)
        } else if(args.contains('--ivy-log-debug')) {
            log.setLevel(Level.DEBUG)
        }
    }

    @Override
    protected void doProgress() {
        //NOP
    }

    @Override
    protected void doEndProgress(String msg) {
        //NOp
    }

    @Override
    void log(String msg, int levelInt) {
        if (levelInt == Message.MSG_WARN) {
            log.warn(msg)
        } else if (levelInt == Message.MSG_INFO) {
            log.info(msg)
        } else if (levelInt == Message.MSG_DEBUG) {
            log.debug(msg)
        } else if (levelInt == Message.MSG_VERBOSE) {
            log.info(msg)
        } else if (levelInt == Message.MSG_ERR) {
            log.error(msg)
        }
    }

    @Override
    void rawlog(String msg, int level) {
        log(msg, level)
    }
}
