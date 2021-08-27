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

````
1. 方式一：
  a.将hsa-api安装到本地maven仓库
      mvn clean install
  b.将hsa-biz安装到本地maven仓库
      mvn clean install
  c.将hsa-db安装到本地maven仓库
      mvn clean install      
  d.在hsa-main-xxx中，打包
      mvn clean package
2. 方式二（推荐）
  在根目录下打包，运行打包命令，会自动依赖，并会将三套环境下的包都生成
    mvn clean package
````

项目运行

````
1. 在阿里开发环境下
   a. 运行edas
   b. （可选）设置启动启动参数：-Dpandora.location=/Users/localuser/.m2/repository/com/taobao/pandora/taobao-hsf.sar/dev-SNAPSHOT/taobao-hsf.sar-dev-SNAPSHOT.jar
      运行hsa-main-ali目录下的cn.hsa.xxx.HsaAliApplication
2. 在腾讯开发环境下
   a. 运行consul
   b. 运行hsa-main-tencent目录下的cn.hsa.xxx.HsaTencentApplication
3. 在开源开发环境下
   a. 启动Nacos
   b. 运行hsa-main-generic目录下的cn.hsa.xxx.HsaGenericApplication
````

测试地址

````
打开Swagger页面
http://127.0.0.1:8080/hsa-web/swagger-ui.html
````

开源方案中的加解密

````
1. 通过jasypt，加密明文字符串root
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI password=G0CvDz7oJn6 algorithm=PBEWithMD5AndDES input=root
2. 假设生成的加密串为6eaMh/RX5oXUVca9ignvtg==
将加密后的值配置到配置中心或application.properties文件中,例如
spring.datasource.username=ENC(6eaMh/RX5oXUVca9ignvtg==)
````