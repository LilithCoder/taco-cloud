package tacocloud;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//import javax.persistence.Entity;
//import javax.persistence.Id;


/**
 * JPA 要求实体有一个无参构造函数
 * 要是不希望使用它，可以通过将 access 属性设置为 AccessLevel.PRIVATE 来将其设置为私有。
 * 因为必须设置 final 属性，所以还要将 force 属性设置为 true，这将导致 Lombok 生成的构造函数将它们设置为 null
 * */
//@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
//@Entity // 注解域作为 JPA 实体
@Data // @Data 隐式地添加了一个必需的有参构造函数，但是当使用 @NoArgsConstructor 时，该构造函数将被删除
@RequiredArgsConstructor // 显式的 @RequiredArgsConstructor 确保除了私有无参数构造函数外，仍然有一个必需有参构造函数
public class Ingredient {
//    @Id // id 属性必须使用 @Id 进行注解，将其指定为惟一标识数据库中实体的属性
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}