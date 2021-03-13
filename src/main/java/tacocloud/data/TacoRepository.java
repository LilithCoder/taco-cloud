package tacocloud.data;

import tacocloud.Taco;

public interface TacoRepository {
    Taco save(Taco design);
}