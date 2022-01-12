#!/bin/bash
set -e
set -x

G_BASE_PATH=$(cd `dirname $0`; pwd)
cd ${G_BASE_PATH}


#---- 参数配置 ---------------------------------------------------------

# docker label
DOCKER_LABEL=

#----------------------------------------------------------------------

LOWER_LABEL=${DOCKER_LABEL,,}
LABEL_NAME=${LOWER_LABEL:-$(date +%Y_%m_%d)}
IMAGE_NAME=vector-retrieval-merger:${LABEL_NAME}
TAR_NAME=vector-retrieval-merger_${LABEL_NAME}.tar
docker build . -t ${IMAGE_NAME}
docker save ${IMAGE_NAME} -o ${TAR_NAME}


exit 0
# docker 运行命令示例-----------------------------------------------

docker run -d --restart always \
    -p 8899:8899 \
    -v /home/ssm-user/engine_config.json:/root/engine_config.json \
    ${IMAGE_NAME}
