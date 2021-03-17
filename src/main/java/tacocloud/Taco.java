package tacocloud;

import lombok.Data;

//import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
/**
 * Taco 是一个简单的 Java 域对象，具有两个属性。
 * Taco 类也使用 @Data 进行注释，以便在运行时自动生成基本的 JavaBean 方法。
 **/

@Data // 该关键字帮域类自动补全api
//@Entity
public class Taco {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO) // 因为依赖于数据库自动生成 id 值，所以还使用 @GeneratedValue 注解 id 属性，指定AUTO策略
    private Long id;

    private Date createdAt;

    @NotNull // 声明验证规则, 要验证的类声明验证规则 Java's Bean Validation API
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    /**
     * 要声明 Taco 及其相关 Ingredient 列表之间的关系，可以使用 @ManyToMany 注解 ingredient 属性。
     * 一个 Taco 可以有很多 Ingredient，一个 Ingredient 可以是很多 Taco 的一部分。
     * */
//    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "You must choose at least 1 ingredient") // 声明验证规则, 要验证的类声明验证规则
    private List<Ingredient> ingredients;

    // 将 createdAt 属性设置为保存 Taco 之前的当前日期和时间
//    @PrePersist
//    void createdAt() {
//        this.createdAt = new Date();
//    }
}