package tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 设置 DataSource，以便 Spring Security 知道如何访问数据库
    @Autowired
    DataSource dataSource;

    /**
     * 使用给定 AuthenticationManagerBuilder 来指定在身份验证期间如何查找用户
     * 在给定的 AuthenticationManagerBuilder 上调用 jdbcAuthentication()
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource) // 这里使用的 dataSource 是由自动装配提供的
                .usersByUsernameQuery( // 身份验证查询
                        "select username, password, enabled from Users " + "where username=?")
                .authoritiesByUsernameQuery( // 基本授权查询
                        "select username, authority from UserAuthorities " + "where username=?")
                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
    }
}