package srinageswari.programmedhousehold.coreservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import srinageswari.programmedhousehold.coreservice.enums.CulinaryStep;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recipe_item")
public class RecipeItemEntity {

  @EmbeddedId private RecipeItemId recipeItemId;

  @Column(nullable = false)
  private BigDecimal requiredQty;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("recipeId")
  @JoinColumn(name = "recipe_id", referencedColumnName = "id", columnDefinition = "bigint UNSIGNED")
  private RecipeEntity recipe;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("itemId")
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private ItemEntity item;

  @Enumerated(value = EnumType.STRING)
  CulinaryStep culinaryStep;

  public RecipeItemEntity(
      RecipeEntity recipe, ItemEntity item, BigDecimal requiredQty, CulinaryStep culinaryStep) {
    this.recipeItemId = new RecipeItemId(recipe.getId(), item.getId());
    this.recipe = recipe;
    this.item = item;
    this.requiredQty = requiredQty;
    this.culinaryStep = culinaryStep;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RecipeItemEntity that = (RecipeItemEntity) o;
    return recipeItemId.equals(that.recipeItemId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recipeItemId);
  }
}
