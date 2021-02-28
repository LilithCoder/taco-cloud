package tacocloud;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class) // HomeController的Web Test
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc; // 注入MockMvc

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/")) // 测试将对根路径 "/" 执行一个 HTTP GET 请求
                .andExpect(status().isOk()) // 期望 HTTP 状态码200
                .andExpect(view().name("home")) // 期望视图名称为 home
                .andExpect(content().string(containsString("Welcome to ..."))); // 期望结果内容包含短语 “Welcome to…”
    }

}