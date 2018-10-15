# Ourbatis

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.smallnico/ourbatis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.smallnico/ourbatis/)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## Introduce
```
 _____   _   _   _____    _____       ___   _____   _   _____  
/  _  \ | | | | |  _  \  |  _  \     /   | |_   _| | | /  ___/ 
| | | | | | | | | |_| |  | |_| |    / /| |   | |   | | | |___  
| | | | | | | | |  _  /  |  _  {   / / | |   | |   | | \___  \ 
| |_| | | |_| | | | \ \  | |_| |  / /  | |   | |   | |  ___| | 
\_____/ \_____/ |_|  \_\ |_____/ /_/   |_|   |_|   |_| /_____/ 
```
让Mybatis开发更加简洁轻松，提供通用的操作方法及自定义入口，让开发无XML化，提高工作效率，减少编码时间！

**Ourbatis**的特性：
 - **1**、简洁方便，可以让Mybatis无XML化开发。
 - **2**、优雅解耦，通用和自定义的SQL标签完全隔离，让维护更加轻松。
 - **3**、无侵入性，Mybatis和Ourbatis可同时使用，配置简洁。
 - **4**、灵活可控，通用模板可自定义及扩展。
 - **5**、部署快捷，只需要一个依赖，两个配置，即可直接运行。
 - **6**、多数据源，在多数据源环境下也可以照常使用。
 - **7**、项目活跃，Nico也会在日常开发中使用并优化Ourbatis，将会率先踩坑填坑。
 
## Install
以Spring Boot项目为例，添加一下依赖
```
<dependency>
  <groupId>com.smallnico</groupId>
  <artifactId>ourbatis-spring-boot-starter</artifactId>
  <version>1.0.5</version>
</dependency>
```
然后配置一下您的实体类所在的包路径：
```
ourbatis.domain-locations=org.nico.ourbatis.domain
```
如果您想查看Ourbatis启动日志，您可以追加以下配置：
```
logging.level.org.nico.ourbatis=debug
```
接下来，您的Mapper只需要继承SimpleMapper接口即可：
```
public interface UserMapper extends SimpleMapper<User, Integer>{}
```
## Simple
 - [Sprint-Boot案例](https://github.com/ainilili/ourbatis-simple)
## Wiki
 - [Wiki文档](https://github.com/ainilili/ourbatis/wiki)

## Feedback
 - ```作者QQ``` 473048656
 - ```QQ交流群``` 177563526
 - ```邮箱地址``` ainililia@163.com
