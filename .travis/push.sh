#!/bin/bash

git config --local user.name $GH_USER_NAME
git config --local user.email $GH_USER_EMAIL

git status

mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} versions:commit

git remote set-url origin https://$GITHUB_API_KEY@github.com/pstickne/Echo.git
git add .
git commit -m "automated version bump [skip ci]"
git push origin development
