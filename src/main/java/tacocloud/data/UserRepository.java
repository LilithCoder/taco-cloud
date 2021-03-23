package tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import tacocloud.User;

public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 除了通过扩展 CrudRepository 提供的 CRUD 操作之外，
     * UserRepository 还定义了一个 findByUsername() 方法，
     * 将在用户详细信息服务中使用该方法根据用户名查找 User。
     * Spring Data JPA 将在运行时自动生成该接口的实现
     * */
    User findByUserName(String username);
}