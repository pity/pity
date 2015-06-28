#!/bin/bash

VERSION="@version@"
DEST=~/.pity/cache/wrapper-$VERSION-all.jar

if [ ! -e "$DEST" ]; then
    mkdir -p `dirname "$DEST"`
    wget https://dl.bintray.com/ethankhall/maven/io/pity/wrapper/$VERSION/wrapper-$VERSION-all.jar -O $DEST
fi

java -jar $DEST "$@"
