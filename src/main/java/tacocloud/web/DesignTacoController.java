package tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tacocloud.Ingredient;
import tacocloud.Ingredient.Type;
import tacocloud.Taco;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @GetMapping // 处理请求路径为 /design 的 HTTP GET 请求
    public String showDesignForm(Model model) {

        // 构建Ingredient列表
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        // 将请求和Ingredient数据提交给视图模板，以 HTML 的形式呈现并发送给请求的 web 浏览器
        // 根据原料类型过滤该列表。然后将成分类型列表作为属性添加到传递到 showDesignForm() 的 Model 对象。
        // Model 是一个对象，它在控制器和负责呈现数据的视图之间传输数据。
        // 最后，放置在 Model 类属性中的数据被复制到 servlet 响应属性中，视图可以在其中找到它们。
        Type[] types = Ingredient.Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());

        // showDesignForm() 方法最后返回 “design”，这是将用于向浏览器呈现 Model 的视图的逻辑名称。
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        // 元素流 -> 筛选 -> 聚合
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
