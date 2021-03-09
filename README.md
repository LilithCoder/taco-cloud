## taco-cloud
一个基于Spring, Spring MVC的墨西哥卷饼外卖平台

## 项目启动
如果没有maven环境，请先配置[maven](https://www.jianshu.com/p/191685a33786)

ps: (如果使用的是zsh，需要正确地在 ~/.zshrc 中 export maven 环境变量以及Java JDK 环境变量)

或使用Homebrew直接安装maven

```aidl
$ brew install maven
```

安装后命令行输入：
```aidl
$ cd taco-cloud
$ mvn spring-boot:run
```

接下来访问 ```localhost:8080```

## 项目概述

[待完善]

## 第1-2章阶段

![](pic/flow_chart_v1.png)

### 声明验证规则:

在 Spring MVC 中应用验证，需要这样做：

- 对要验证的类声明验证规则：特别是 Taco 类

- 指定验证应该在需要验证的控制器方法中执行，具体来说就是：DesignTacoController 的 processDesign() 方法和 OrderController 的 processOrder() 方法。

- 修改表单视图以显示验证错误。

### 控制器:

- 它们都用 @Controller 进行了注释，以表明它们是控制器类，应该由 Spring 组件扫描自动发现，并在 Spring 应用程序上下文中作为 bean 进行实例化

- 除了 HomeController 之外，所有的控制器都在类级别上使用 @RequestMapping 进行注释，以定义控制器将处理的基本请求模式

- 它们都有一个或多个方法，这些方法都用 @GetMapping 或 @PostMapping 进行了注释，以提供关于哪些方法应该处理哪些请求的细节

### 视图控制器:

- 不填充模型或处理输入

- 只将请求转发给视图的控制器

### 学习总结(2021-03-07)

- Spring 提供了一个强大的 web 框架，称为 Spring MVC，可以用于开发 Spring 应用程序的 web 前端。

- Spring MVC 是基于注解的，可以使用 @RequestMapping、@GetMapping 和 @PostMapping 等注解来声明请求处理方法。

- 大多数请求处理方法通过返回视图的逻辑名称来结束，例如一个 Thymeleaf 模板，请求（以及任何模型数据）被转发到该模板。

- Spring MVC 通过 Java Bean Validation API 和 Hibernate Validator 等验证 API 的实现来支持验证。

- 视图控制器可以用来处理不需要模型数据或处理的 HTTP GET 请求。

- 除了 Thymeleaf，Spring 还支持多种视图选项，包括 FreeMarker、Groovy Templates 和 Mustache

## 第3-?章阶段

### 为域适配持久化:

- 在将对象持久化到数据库时，通常最好有一个惟一标识对象的字段

- 使用 Lombok (@Data注释) 在运行时自动生成访问器方法，所以除了声明 id 和 createdAt 属性外，不需要做任何事情。它们将在运行时根据需要生成适当的 getter 和 setter 方法。

### 定义 JDBC 存储库

Ingredient repository 需要执行以下操作：

- 查询所有的 Ingredient 使之变成一个 Ingredient 的集合对象

- 通过它的 id 查询单个 Ingredient

- 保存一个 Ingredient 对象

完成了 JdbcIngredientRepository后，现在可以将其注入到 DesignTacoController 中，并使用它来提供一个 Ingredient 对象列表，而不是使用硬编码的值

showDesignForm() 方法的第 2 行现在调用了注入的 IngredientRepository 的 findAll() 方法。findAll() 方法从数据库中提取所有 Ingredient，然后将它们对应到到模型的不同类型中