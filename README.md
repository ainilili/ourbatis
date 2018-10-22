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
To make the development of Mybatis more simple and easy, to provide common operation methods and custom entrance, to make the development free from XML, to improve work efficiency and reduce coding time!

Features of **Ourbatis**
- **1**, simple and convenient, can make Mybatis develop without XML.
- **2**, elegant decoupling, complete isolation of generic and custom SQL tags, making maintenance easier.
- **3**, non-invasive, Mybatis and Ourbatis can be used simultaneously, and the configuration is simple.
- **4**, flexible and controllable, general template can be customized and extended.
- **5**, quick deployment, only one dependency and two configurations are needed to run directly.
- **6**, multi-data source. It can also be used as usual in multi-data source environment.
- **7**, the project is active, Nico will also be used in daily development and optimize Ourbatis, and will be the first to step on the pit and fill the pit.
 
## Install
Take the Spring Boot project for example, and add a dependency
```
<dependency>
  <groupId>com.smallnico</groupId>
  <artifactId>ourbatis-spring-boot-starter</artifactId>
  <version>1.0.5</version>
</dependency>
```
Then configure the package path for your entity class:
```
ourbatis.domain-locations=org.nico.ourbatis.domain
```
If you want to see Ourbatis startup logs, you can add the following configuration:
```
logging.level.org.nico.ourbatis=debug
```
Next, your Mapper simply inherits the SimpleMapper interface:
```
public interface UserMapper extends SimpleMapper<User, Integer>{}
```
## Simple
 - [Sprint Boot Case](https://github.com/ainilili/ourbatis-simple)
## Wiki
 - [Wiki documentation](https://github.com/ainilili/ourbatis/wiki)

## Feedback
 - ```QQ``` 473048656
 - ```QQ Group``` 177563526
 - ```Email``` ainililia@163.com
