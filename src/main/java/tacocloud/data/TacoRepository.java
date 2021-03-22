package tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import tacocloud.Taco;

/**
 * 第一个参数是存储库要持久化的实体类型，第二个参数是实体 id 属性的类型
 * 声明 JPA repository
 * */
public interface TacoRepository extends CrudRepository<Taco, Long> {

    /**
     * jdbcTemplate 的 API
     */
//    Taco save(Taco design);
}
