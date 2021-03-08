package tacocloud;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data // 该关键字帮域类自动补全api
public class Order {

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
}
