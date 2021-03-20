package tacocloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacocloud.Ingredient;
import tacocloud.data.IngredientRepository;

/**
 * 因为表单中的Ingredient都是String类型，而此时没有办法将String类型转化为Ingredient，
 * 需要配置Converter类将Sring转化为Ingredient类，添加IngredientByIdConverter类可解决此问题
 * */
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findOne(id);
    }

}
