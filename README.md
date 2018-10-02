# outbatis
General XML solution based on mybatis
# Ourbatis

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.smallnico/ourbatis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.smallnico/ourbatis/)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## Introduce
**Ourbatis**为简化**Mybatis**而生，拥有无侵入性、小巧灵活及快速入手的特点，可以帮助使用者节省编写通用数据操作所花费的时间，让同学们不会因为表里增加了一个字段而大费周折的去重新定义**SQL**。

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
  <version>1.0.2</version>
</dependency>
```
如果您想使用独立的Ourbatis，可以添加另一个依赖：
```
<dependency>
  <groupId>com.smallnico</groupId>
  <artifactId>ourbatis</artifactId>
  <version>1.0.2</version>
</dependency>
```
成功的将Ourbatis嵌入您的应用之后，需要简单的配置一下：
```
ourbatis.template-locations=ourbatis.xml
ourbatis.domain-locations=com.nico.ourbatis.entity.po
```
 - ```template-locations```表示模板所在**classpath**下的相对路径。
 - ```domain-locations```代表实体类所在包

Ourbatis坚持灵活自定义的风格，在Jar包内不提供默认的模板配置，您可以通过下载我们提供的配置来使用：
```
https://github.com/ainilili/ourbatis-simple/blob/master/src/main/resources/ourbatis.xml
```
另外，我们也提供配合模板的一套BaseMapper操作接口父类：
```
https://github.com/ainilili/ourbatis-simple/blob/master/src/main/java/org/nico/ourbatis/mapper/BaseMapper.java
```
至此，Ourbatis的部署工作完成！Ourbatis是辅助Mybatis工作的，所以Mybatis部署必不可少，Ourbatis无任何侵入性，且Ourbatis的部署过程不会干扰Mybatis的部署，可以说，二者是完全相互独立的！

Next, 启动&运行！

## Simple
```
git clone https://github.com/ainilili/ourbatis-simple
```
## Doc
```
待续
```