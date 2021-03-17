package tacocloud;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

//import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data // 该关键字帮域类自动补全api
//@Entity
/**
 * 指定订单实体应该持久化到数据库中名为 Taco_Order 的表中
 * 尽管可以在任何实体上使用这个注解，但它对于 Order 是必需的。没有它，JPA 将默认将实体持久化到一个名为 Order 的表中
 * Order 在 SQL 中是一个保留字，会导致问题
 * */
//@Table(name = "Taco_Order")
public class Order implements Serializable {
//    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date placedAt;

    @NotBlank(message = "Name is required") // 声明验证规则
    private String name;

    @NotBlank(message = "Street is required") // 声明验证规则
    private String street;

    @NotBlank(message = "City is required") // 声明验证规则
    private String city;

    @NotBlank(message = "State is required") // 声明验证规则
    private String state;

    @NotBlank(message = "Zip is required") // 声明验证规则
    private String zip;

    @CreditCardNumber(message = "Not a valid credit card number") // 声明验证规则: 有效信用卡号
    private String ccNumber;

    // 声明验证规则: 正则表达式，以确保属性值符合所需的格式
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([1-9][0-9])$", message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV") // 声明验证规则: 确保值恰好包含三个数字
    private String ccCVV;

//    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

//    @PrePersist
//    void placedAt() {
//        this.placedAt = new Date();
//    }
}
