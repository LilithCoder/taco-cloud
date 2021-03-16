package tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacocloud.Order;
import tacocloud.data.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) { // 对提交的 Order 对象执行验证
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepo.save(order);
        /**
         * 在这里，表单中提交的 Order 对象（恰好也是在 session 中维护的 Order 对象）
         * 通过注入的 OrderRepository 上的 save() 方法保存
         * 一旦订单被保存，就不再需要它存在于 session 中了
         * 如果不清除它，订单将保持在 session 中，包括其关联的 tacos，下一个订单将从旧订单中包含的任何 tacos 开始
         * 因此需要 processOrder() 方法请求 SessionStatus 参数并调用其 setComplete() 方法来重置会话。
         * */
        sessionStatus.setComplete();
        log.info("Order submitted: " + order);
        return "redirect:/";
    }
}
