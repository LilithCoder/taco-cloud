package tacocloud.data;

//import org.springframework.data.repository.CrudRepository;
import tacocloud.Ingredient;
import tacocloud.Taco;

public interface TacoRepository {
    Taco save(Taco design);
}

// 声明 JPA repository
//public interface TacoRepository extends CrudRepository<Taco, Long> {
//}