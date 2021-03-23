package tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tacocloud.Ingredient;
import tacocloud.Ingredient.Type;
import tacocloud.Order;
import tacocloud.Taco;
import tacocloud.data.IngredientRepository;
import tacocloud.data.TacoRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order") // 类级别的 @SessionAttributes 注解指定了任何模型对象(比如应该保存在会话中的并且可以跨多个请求使用的 order 属性)
public class DesignTacoController {
    /**
     * 完成了 JdbcIngredientRepository后，
     * 将其注入到 DesignTacoController 中，
     * 并使用它来提供一个 Ingredient 对象列表
     * */
    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    /**
     * 将请求和Ingredient数据提交给视图模板，以 HTML 的形式呈现并发送给请求的 web 浏览器
     * 根据原料类型过滤该列表。然后将成分类型列表作为属性添加到传递到 showDesignForm() 的 Model 对象
     * Model 是一个对象，它在控制器和负责呈现数据的视图之间传输数据
     * 最后，放置在 Model 类属性中的数据被复制到 servlet 响应属性中，视图可以在其中找到它们
     */
    @GetMapping // 处理请求路径为 /design 的 HTTP GET 请求
    public String showDesignForm(Model model) {
//        // 构建Ingredient列表
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        );
        /**
         * 调用了注入的 IngredientRepository 的 findAll() 方法。
         * findAll() 方法从数据库中提取所有 Ingredient，
         * 然后将它们对应到到模型的不同类型中
         * */
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        Type[] types = Ingredient.Type.values();
        System.out.println("ingredients: " + ingredients.toString());
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
//        model.addAttribute("design", new Taco());
        // showDesignForm() 方法最后返回 “design”，这是将用于向浏览器呈现 Model 的视图的逻辑名称。
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        // 元素流 -> 筛选 -> 聚合
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }


    /**
     * @ModelAttribute 确保在模型中能够创建 Order 对象
     * 与 session 中的 Taco 对象不同，这里需要在多个请求间显示订单，因此可以创建多个 Taco 并将它们添加到订单中
     * */
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    /**
     * 提交表单时，表单中的字段被绑定到 Taco 对象的属性
     * 该对象作为参数传递给 processDesign()
     * processDesign() 方法可以对 Taco 对象做任何它想做的事情
     *
     * 表单中的这些字段直接对应于 Taco 类的 ingredients 和 name 属性
     *
     * processDesign() 通过返回一个 String 结束
     * 返回的值指示将显示给用户的视图
     * processDesign() 返回的值的前缀是 “redirect:”
     * 用户的浏览器应该被重定向到相对路径 /order/current
     *
     * 在表单绑定时执行验证
     * @Valid 注释告诉 Spring MVC 在提交的 Taco 对象绑定到提交的表单数据之后，
     * 以及调用 processDesign() 方法之前，对提交的 Taco 对象执行验证
     * 如果存在任何验证错误，这些错误的详细信息将在传递到 processDesign() 的错误对象中捕获。
     *
     * Order 参数使用 @ModelAttribute 进行注解，以指示其值应该来自模型，而 Spring MVC 不应该试图给它绑定请求参数
     * */
    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        /**
         * 有验证错误时，返回 “design” 视图名，以便重新显示表单。
         * */
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing design: " + design);
        /**
         * 使用注入的 TacoRepository 来保存 Taco
         * */
        Taco saved = designRepo.save(design);
        /**
         * 将 Taco 对象添加到保存于 session 中 Order 对象中
         * Order 对象仍然保留在 session 中，直到用户完成并提交 Order 表单才会保存到数据库中
         * */
        order.addDesign(saved);
        return "redirect:/orders/current";
    }
}
