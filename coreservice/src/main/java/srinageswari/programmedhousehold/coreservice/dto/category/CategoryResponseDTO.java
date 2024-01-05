package srinageswari.programmedhousehold.coreservice.dto.category;

import lombok.Data;
import srinageswari.programmedhousehold.coreservice.model.CategoryEntity;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Category response
 */
@Data
public class CategoryResponseDTO {

  private Long id;
  private String name;
  private String meal;
  private boolean sidedish;
  private String day;
  private String difficulty;

  public CategoryResponseDTO(CategoryEntity categoryEntity) {
    this.id = categoryEntity.getCategoryId();
    this.name = categoryEntity.getName();
    this.meal = categoryEntity.getMeal().getLabel();
    this.sidedish = categoryEntity.isSidedish();
    this.day = categoryEntity.getDay().getLabel();
    this.difficulty = categoryEntity.getDifficulty().getLabel();
  }
}
