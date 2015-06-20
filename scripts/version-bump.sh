#!/bin/bash

VERSION=$1
echo "Commit bumping version to $VERSION"
git config user.name "circleci"
git config user.email "ci@pity.io"
git commit -a -m "Bumping version to $VERSION \n[ci skip]"
