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

## 学习总结(2021-03-07)

- Spring 提供了一个强大的 web 框架，称为 Spring MVC，可以用于开发 Spring 应用程序的 web 前端。

- Spring MVC 是基于注解的，可以使用 @RequestMapping、@GetMapping 和 @PostMapping 等注解来声明请求处理方法。

- 大多数请求处理方法通过返回视图的逻辑名称来结束，例如一个 Thymeleaf 模板，请求（以及任何模型数据）被转发到该模板。

- Spring MVC 通过 Java Bean Validation API 和 Hibernate Validator 等验证 API 的实现来支持验证。

- 视图控制器可以用来处理不需要模型数据或处理的 HTTP GET 请求。

- 除了 Thymeleaf，Spring 还支持多种视图选项，包括 FreeMarker、Groovy Templates 和 Mustache

## 第3.1章 使用 JDBC 读写数据

![](pic/flow_chart_v2.png)

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

### 定义schema并预加载数据

- 我们需要 Ingredient 表以及一些保存订单和设计信息的表

- 如果有一个名为 schema.sql 的文件。在应用程序的类路径根目录下执行 sql，然后在应用程序启动时对数据库执行该文件中的 SQL。写入一个名为 schema.sql 的文件中，然后放在项目的 src/main/resources 文件夹下。

- 用一些 Ingredient 数据来预加载数据库。幸运的是，Spring Boot 还将执行一个名为 data.sql 的文件，这个文件位于根路径下。因此，可以使用 src/main/resources/data.sql 中的下面程序清单中的 insert 语句来加载包含 Ingredient 数据的数据库。

### 插入数据

- JdbcIngredientRepository 中的 save() 方法使用 JdbcTemplate 的 update() 方法将 Ingredient 对象保存到数据库中

- dbcTemplate 保存数据的两种方法包括：

    - 直接使用 update() 方法保存一个 Taco
    
        - 保存一个 Taco 设计需要将与该 Taco 关联的 Ingredient 保存到 Taco_Ingredient 表中。同样，保存 Order 也需要将与 Order 关联的 Taco 保存到 Taco_Order_Tacos 表中。这使得保存 Taco 和 Order 比 保存 Ingredient 更有挑战性。
        
        - 这里用了一个不同的 update() 方法，通过使用 PreparedStatementCreator，可以调用 update()，传入 PreparedStatementCreator 和 KeyHolder，返回 Taco id，在 Taco 表中插入一行时，需要知道数据库生成的 id，以便在每个 Ingredient 中引用它。接着插入了 Taco_Ingredient 表。详细实现见[JdbcTacoRepository.java](src/main/java/tacocloud/data/JdbcTacoRepository.java)
           
        - 将 TacoRepository 注入到 DesignTacoController
    
    - 使用 SimpleJdbcInsert 包装类保存一个 Order
    
        - 在保存订单方面，也存在类似的情况。不仅必须将订单数据保存到 Taco_Order 表中，还必须引用 Taco_Order_Tacos 表中的每个 taco
        
        - SimpleJdbcInsert 是一个包装了 JdbcTemplate 的对象，它让向表插入数据的操作变得更容易
        
            - 使用 Jackson 的 ObjectMapper 及其 convertValue() 方法将 Order 转换为 Map
            
            - 使用 executeAndReturnKey() 将订单信息保存到 Taco_Order 表中，并将数据库生成的 id 作为一个 Number 对象返回
            
            - 使用 execute() 存储 Taco 映对 Taco_Order 的信息到 Taco_Order_Tacos 表中 
            
- 这里有个需要注意的重点就是：

    设计完 Taco 后，我们使用注入的 TacoRepository 来保存这个 Taco 对象到 Taco 表以及 Taco_Ingredient 表中，然后将 Taco 对象添加到保存于 session 中 Order 对象中，Order 对象仍然保留在 session 中，直到用户完成并提交 Order 表单才会保存到数据库中。其中 Order 对象在被提交保存到数据库前应该保存在会话中的并且可以跨多个请求使用。@SessionAttributes("order") 指定了这一点。
    
## 第3.2章 使用 Spring Data JPA 持久化数据

### 注解域作为实体

- 为了将其声明为 JPA 实体，必须使用 @Entity 注解

- id 属性必须使用 @Id 进行注解

- Taco 类因为依赖于数据库自动生成 id 值，所以还使用 @GeneratedValue 注解 id 属性，指定自动策略

- 要声明 Taco 及其相关 Ingredient 列表之间的关系，可以使用 @ManyToMany 注解 ingredient 属性。一个 Taco 可以有很多 Ingredient，一个 Ingredient 可以是很多 Taco 的一部分。

- Order 类级别有一个新的注解：@Table。这指定订单实体应该持久化到数据库中名为 Taco_Order 的表中。尽管可以在任何实体上使用这个注解，但它对于 Order 是必需的。没有它，JPA 将默认将实体持久化到一个名为 Order 的表中，但是 Order 在 SQL 中是一个保留字，会导致问题

### 声明 JPA repository

- 在存储库的 JDBC 版本中，显式地声明了希望 repository 提供的方法。但是使用 Spring Data，扩展 CrudRepository 接口

- CrudRepository 为 CRUD（创建create、读取read、更新update、删除delete）操作声明了十几个方法

- 当应用程序启动时，Spring Data JPA 会动态地自动生成一个实现。这意味着 repository 可以从一开始就使用。只需将它们注入到控制器中

- CrudRepository 提供的方法非常适合用于实体的通用持久化

### 自定义 JPA repository

- 在生成 repository 实现时，Spring Data 检查存储库接口中的任何方法，解析方法名称，并尝试在持久化对象的上下文中理解方法的用途（在本例中是 Order）。本质上，Spring Data 定义了一种小型的领域特定语言（DSL），其中持久化细节用 repository 中的方法签名表示 

## 学习总结(2021-03-18)

- 使用 JDBC 读写数据，JdbcTemplate 大大简化了 JDBC 的工作。

- dbcTemplate 用两种不同的 update() 方法来保存数据，当需要知道数据库生成的 id 时，可以同时使用 PreparedStatementCreator 和 KeyHolder。

- dbcTemplate 还可以为了方便执行数据插入，使用 SimpleJdbcInsert。

- Spring Data JPA 使得 JPA 持久化就像编写存储库接口一样简单。