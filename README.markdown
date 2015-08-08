# Pity That Bug Report

[![Circle CI](https://circleci.com/gh/pity/pity/tree/master.svg?style=svg)](https://circleci.com/gh/pity/pity/tree/master) [ ![Download](https://api.bintray.com/packages/ethankhall/maven/pity/images/download.svg) ](https://bintray.com/ethankhall/maven/pity/_latestVersion)

## Why?

Have you ever gotten a bug report that was essentially useless? Pity aims to prevent that from happening. Pity provides
a way to collect any data about an environment. Pity also allows for execution of commands though it. By executing a command
using Pity, more data can be collected. For example, automatically enabling debug logging.

### Collecting Bug Reports
Pity provides a way to collect bug reports in an automated mechanism. Pity allows the maintains of projects to configure
what needs to be collected for a project.

### Collecting Important Emergency Metrics
When the site is down, you don't have time to collect all the data about whats happening, you're working on on getting
it back up. Pity can allow you to collect data about a machine to be used to create better results as a system runs.

## Try
For ease of use, there is a script that is runnable on linux that will download the required jar (wrapper) then execute it.
This can be helpful if you want to run this on a remote system quickly without having to install any software. No special
privileges are needed other than Internet access and Java 7.

    bash <(curl https://dl.bintray.com/ethankhall/maven/io/pity/wrapper/1.0.7/wrapper-1.0.7.sh)

    ... (download) ...

    2015-08-08 08:26:55 (4.08 MB/s) - '/Users/ethan/.pity/cache/wrapper-1.0.7-all.jar' saved [2049037/2049037]

    08:26:56.325 INFO  i.p.wrapper.ivy.DependencyResolver - Retrieving Dependencies...
    08:27:04.317 INFO  i.p.wrapper.ivy.DependencyResolver - Finished downloading 20 dependencies
    08:27:05.235 INFO  io.pity.bootstrap.AskBootstrapMain - Loading version 1.0.7

You will see that a file gets created called `generated-data.xml`. In the future this will be an html file or markdown.

Once the script has been run once, you can manually execute the program by running
`java -jar $HOME/.pity/cache/wrapper-1.0.7-all.jar`

## Plugins
Pity is a plugin based system. For more details how to write plugins check out [PLUGINS](docs/PLUGINS.markdown)
