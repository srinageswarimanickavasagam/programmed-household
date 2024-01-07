package srinageswari.programmedhousehold.coreservice.server.model;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import srinageswari.programmedhousehold.coreservice.client.enums.Unit;

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
  private int itemQty;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("recipeId")
  @JoinColumn(name = "recipe_id", referencedColumnName = "id")
  private RecipeEntity recipe;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("itemId")
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private ItemEntity item;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Unit unit;

  public RecipeItemEntity(RecipeEntity recipe, ItemEntity item, Unit unit, int itemQty) {
    this.recipeItemId = new RecipeItemId(recipe.getId(), item.getId());
    this.recipe = recipe;
    this.item = item;
    this.unit = unit;
    this.itemQty = itemQty;
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
