# hsa-his

云HIS V2.0分布式微服务框架，存放后端代码

结构介绍

````
1. hsa-com（系统公共模块）
    系统工具（util）、entity、DTO、BO接口、Service接口、Dubbo API接口（如果微服务需要对其他提供接口，则需在该模块中申明接口）
2. hsa-web（系统统一对外接口模块）
    统一提供前端接口服务模块，调用多模块需要在调用模块中实现Dubbo API接口，并将接口注册dubbo生产者，web模块注册dubbo消费者进行调用
3. hsa-base（基础数据子系统）
    3.1. hsa-base-db
        mybatis的sql mapper文件
    3.2. hsa-base-biz
        业务逻辑，包括业务代码，公共功能引入，例如hsaf的core包，数据源配置xml
    3.3. hsa-main-ali
        阿里云适配层，包括应用启动，适配层pom引入，配置相关xml，properties
    3.4. hsa-main-generic
        开源云适配层，包括应用启动，适配层pom引入，配置相关xml，properties
    3.5. hsa-main-tencent
        腾讯云适配层，包括应用启动，适配层pom引入，配置相关xml，properties
````

工程依赖

````
hsa-his (parent pom)
└── hsa-com （系统公共模块）
    └── hsa-web 
    └── hsa-base （基础数据子系统） 
        ├── hsa-base-biz
        ├── hsa-base-db
        ├── hsa-base-ali
        ├── hsa-base-generic
        └── hsa-base-tencent 
    └── hsa-sys （系统管理子系统）
        ├── hsa-sys-biz
        ├── hsa-sys-db
        ├── hsa-sys-ali
        ├── hsa-sys-generic
        └── hsa-sys-tencent 
    └── hsa-stro （药库管理子系统）
        ├── hsa-stro-biz
        ├── hsa-stro-db
        ├── hsa-stro-ali
        ├── hsa-stro-generic
        └── hsa-stro-tencent 
````

开发准备

````
本机开发需要安装下面的环境和工具
1. 安装JDK 1.8
2. 安装Maven 3.6
3. 安装开发IDE，推荐使用Intellij IDEA
   必备插件（Lombok,Git,Maven）
4. 运行环境
   a. 阿里本地环境
   必备：Edas(edas-lite-configcenter)
   可选：Mysql,Redis集群。可配置连接阿里云上的服务。
   b. 腾讯本地环境
   必备：Consul
   可选：Mysql,Redis集群。可配置连接腾讯云上的服务。
   c. 开源本地环境
   必备：Nacos
   可选：Dubbo Admin,Mysql,Redis集群,Kafka,FastDfs
````

工程开发(IDEA)

````
1. 将根目录下的docs/maven/powersi_settings.xml放到本地maven配置路径下
   (注意修改powersi_settings.xml文件中localRepository为本地路径)
2. 开启IDEA自动编译
3. 安装Lombok插件，并开启编译Lombok设置
4. 在hsa-biz编写业务逻辑代码或公共功能
5. 在hsa-main-xxx里添加配置xml或properties
6. 各环境配置参数需提取到hsa-main-xxx的properties中
````

编译打包

his

<<<<<<<<< Temporary merge branch 1
add this is to test release branch work status 模拟新版本发布的修改工作
=========
模拟 fix xxx bug，测试热修复分支 
>>>>>>>>> Temporary merge branch 2
