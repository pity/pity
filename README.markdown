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

## Plugins
Pity is a plugin based system. For more details how to write plugins check out [PLUGINS](docs/PLUGINS.markdown)
