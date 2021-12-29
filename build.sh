#!/bin/bash
set -e
set -x

G_BASE_PATH=$(cd `dirname $0`; pwd)
cd ${G_BASE_PATH}

\rm -rf ~/.gradle

if [ -f gradle-*-bin.zip ]; then
    \rm -rf gradle-*-bin.zip
fi
aws s3 cp s3://htm-test/BaiXuefeng/FilterKnn/gradle-7.3.3-bin.zip .
unzip -u gradle-*-bin.zip

if [ -f release/vector-retrieval-merge-*.jar ]; then
    \rm -rf release/vector-retrieval-merge-*.jar
fi
./gradle-*/bin/gradle clean bootJar --no-daemon
\cp -rf ./build/libs/vector-retrieval-merge-*.jar ./release

\rm -rf ~/.gradle
