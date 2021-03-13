package tacocloud;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
/**
 * Taco 是一个简单的 Java 域对象，具有两个属性。
 * Taco 类也使用 @Data 进行注释，以便在运行时自动生成基本的 JavaBean 方法。
 **/

@Data // 该关键字帮域类自动补全api
public class Taco {

    private Long id;

    private Date createdAt;

    @NotNull // 声明验证规则, 要验证的类声明验证规则 Java's Bean Validation API
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient") // 声明验证规则, 要验证的类声明验证规则
    private List<Ingredient> ingredients;
}