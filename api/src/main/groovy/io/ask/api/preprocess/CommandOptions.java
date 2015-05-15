package io.ask.api.preprocess;

import java.util.List;


public interface CommandOptions {
    String getCommand();
    List<String> getArguments();
}
