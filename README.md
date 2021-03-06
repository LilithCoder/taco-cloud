## taco-cloud
一个基于Spring, Spring MVC的墨西哥卷饼外卖平台，

## 项目流程概述

## 声明验证规则
在 Spring MVC 中应用验证，需要这样做：

- 对要验证的类声明验证规则：特别是 Taco 类

- 指定验证应该在需要验证的控制器方法中执行，具体来说就是：DesignTacoController 的 processDesign() 方法和 OrderController 的 processOrder() 方法。

- 修改表单视图以显示验证错误。