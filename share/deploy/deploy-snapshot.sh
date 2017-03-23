#!/bin/bash

set -e

setup_git() {
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis CI"
}

if [ "$TRAVIS_BRANCH" == "master" ] ; then 
  mvn -DskipTests=true -B --settings share/deploy/travis-settings.xml deploy
  setup_git
  mvn -B javadoc:javadoc scm-publish:publish-scm > /dev/null
  echo "Deployed to snapshot release"
else
  echo "Not master, did not deploy snapshot"
fi