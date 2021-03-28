package tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tacocloud.User;
import tacocloud.data.UserRepository;

@Service // 该类标记为包含在 Spring 的组件扫描中
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;

    /**
     * UserRepositoryUserDetailsService 通过自己的构造器 被 UserRepository 实例 注入
     * */
    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User '" + username + "' Not Found");
    }
}
