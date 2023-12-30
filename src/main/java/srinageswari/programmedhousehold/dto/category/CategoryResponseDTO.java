package srinageswari.programmedhousehold.dto.category;

import lombok.Data;
import srinageswari.programmedhousehold.model.Category;

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
  private boolean isActive;

  public CategoryResponseDTO(Category category) {
    this.id = category.getCategoryId();
    this.name = category.getName();
    this.meal = category.getMeal().getLabel();
    this.sidedish = category.isSidedish();
    this.day = category.getDay().getLabel();
    this.difficulty = category.getDifficulty().getLabel();
    this.isActive = category.isActive();
  }
}
