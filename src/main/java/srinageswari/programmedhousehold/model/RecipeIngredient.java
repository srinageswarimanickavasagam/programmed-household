package srinageswari.programmedhousehold.model;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import srinageswari.programmedhousehold.common.enums.Unit;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

  @EmbeddedId private RecipeIngredientId recipeIngredientId;

  @Column(nullable = false)
  private int ingredientQty;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("recipeId")
  @JoinColumn(name = "recipe_id", referencedColumnName = "id")
  private Recipe recipe;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("itemId")
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private Item item;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Unit unit;

  public RecipeIngredient(Recipe recipe, Item item, Unit unit, int ingredientQty) {
    this.recipeIngredientId = new RecipeIngredientId(recipe.getId(), item.getId());
    this.recipe = recipe;
    this.item = item;
    this.unit = unit;
    this.ingredientQty = ingredientQty;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RecipeIngredient that = (RecipeIngredient) o;
    return recipeIngredientId.equals(that.recipeIngredientId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recipeIngredientId);
  }
}
