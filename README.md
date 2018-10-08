# Ourbatis

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.smallnico/ourbatis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.smallnico/ourbatis/)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## Introduce
**ourbatis**是一款轻量级、无侵入、灵活的**mybatis**辅工具，提供一套可配置的轻量级模板，使**mybatis**无**xml**化，让开发更便捷！

使用**Ourbatis**的好处：
 - **1**、表字段修改将不是问题。
 - **2**、通用和自定义的SQL标签完全隔离，让维护更加轻松。
 - **3**、完全无侵入性，Mybatis和Ourbatis可同时使用。
 - **4**、模板高灵活，高扩展性。
 
## Install
如果您的应用使用Spring Boot，您可以通过添加以下依赖来使用Ourbatis：
```
<dependency>
  <groupId>com.smallnico</groupId>
  <artifactId>ourbatis-spring-boot-starter</artifactId>
  <version>1.0.4</version>
</dependency>
```
如果您想使用独立的Ourbatis，可以添加另一个依赖：
```
<dependency>
  <groupId>com.smallnico</groupId>
  <artifactId>ourbatis</artifactId>
  <version>1.0.4</version>
</dependency>
```
成功的将Ourbatis嵌入您的应用之后，需要简单的配置一下：
```
ourbatis.template-locations=ourbatis.xml
ourbatis.domain-locations=org.nico.ourbatis.domain
```
 - ```template-locations```表示模板所在**classpath**下的相对路径。
 - ```domain-locations```代表实体类所在包

Ourbatis内部提供默认的通用模板和Mapper接口，您的Mapper只需要继承OurbatisMapper即可：
```
public interface UserMapper extends OurbatisMapper<User, Integer>{}
```
至此，Ourbatis的部署工作完成！Ourbatis是辅助Mybatis工作的，所以Mybatis部署必不可少，Ourbatis无任何侵入性，且Ourbatis的部署过程不会干扰Mybatis的部署，可以说，二者是完全相互独立的！

另外，如果您想自定义模板，需要重写```ourbatis.xml```和```OurbatisMapper.java```，可以参考以下文件：
```
https://github.com/ainilili/ourbatis/blob/master/src/main/resources/ourbatis.xml
https://github.com/ainilili/ourbatis/blob/master/src/main/java/org/nico/ourbatis/mapper/OurbatisMapper.java
```
## Simple
```
git clone https://github.com/ainilili/ourbatis-simple
```
## Doc
```
[https://github.com/ainilili/ourbatis/wiki](https://github.com/ainilili/ourbatis/wiki)
```