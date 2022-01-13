#!/bin/bash
set -e
set -x

G_BASE_PATH=$(cd `dirname $0`; pwd)
cd ${G_BASE_PATH}

ulimit -n 500000
java  -jar -server -Xmx4g -Xms4g -Xmn1g -Xss16m  /root/vector-retrieval-merge-*.jar
