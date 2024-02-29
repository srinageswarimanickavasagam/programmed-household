package srinageswari.programmedhousehold.backend.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.backend.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.backend.model.RecipeItemEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeItemRequest
 */
@Mapper(
    componentModel = "spring",
    uses = {ItemMapper.class})
@Component
public interface RecipeItemMapper {

  @Mappings({
    @Mapping(target = "recipe", ignore = true), // Ignore the recipe property during mapping
    @Mapping(
        target = "recipeItemId.recipeId",
        source = "dto.recipe.id"), // Map the recipeId property of RecipeItemId
    @Mapping(
        target = "recipeItemId.itemId",
        source = "dto.item.id") // Map the itemId property of RecipeItemId
  })
  RecipeItemEntity toEntity(RecipeItemDTO dto);

  @Mappings({@Mapping(target = "recipe", ignore = true)})
  RecipeItemDTO toDto(RecipeItemEntity entity);
}
