#!/bin/bash -eux

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

VERSION=$(awk '{ printf "%s", $0 }' $DIR/../VERSION)
echo "Tagging version $VERSION"
git tag "V$VERSION"

$DIR/../gradlew -Pbintray.publish=true -Pbintray_user=$BINTRAY_USER -Pbintray_key=$BINTRAY_PASSWORD bintrayUpload

VERSION=`echo $VERSION | awk -F. '{$NF = $NF + 1;} 1' | sed 's/ /./g'`
echo "Next version is $VERSION"
echo $VERSION > $DIR/../VERSION

git commit -a -m 'Bumping version to $VERSION

[ci skip]'
git push
git push --tags

