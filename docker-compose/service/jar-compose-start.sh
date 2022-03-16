#!/bin/sh

echo "启动配置中心服务"
docker-compose  -f /app/service/jdk-hsa-center/docker-compose.yml up -d
echo "启动定时任务"
docker-compose  -f /app/service/jdk-hsa-cron/docker-compose.yml up -d
echo "启动定时任务后台管理"
docker-compose  -f /app/service/jdk-xxl-job/docker-compose.yml up -d
echo "启动入出院启动函数"
docker-compose  -f /app/service/jdk-hsa-inpt/docker-compose.yml up -d
echo "启动医保管理模块启动函数"
docker-compose  -f /app/service/jdk-hsa-insure/docker-compose.yml up -d
echo "启动门急诊模块启动函数"
docker-compose  -f /app/service/jdk-hsa-outpt/docker-compose.yml up -d
echo "启动库存管理"
docker-compose  -f /app/service/jdk-hsa-stro/docker-compose.yml up -d
echo "启动系统管理"
docker-compose  -f /app/service/jdk-hsa-sys/docker-compose.yml up -d
echo "启动网关服务"
docker-compose  -f /app/service/jdk-hsa-web/docker-compose.yml up -d
