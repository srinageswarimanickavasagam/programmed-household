package srinageswari.programmedhousehold.coreservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import srinageswari.programmedhousehold.coreservice.enums.Unit;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "item")
public class ItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 50)
  private String name;

  @ManyToOne
  @JoinColumn(name = "type_id")
  ItemtypeEntity itemtype;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private Unit stockUnit;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private Unit recipeUnit;

  private int itemStockQty;

  private BigDecimal amount;

  private Date stockedDt;

  private boolean inStock;

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  private Set<RecipeItemEntity> recipeItems = new HashSet<>();

  public void addRecipeItem(RecipeItemEntity recipeItemEntity) {
    recipeItems.add(recipeItemEntity);
    recipeItemEntity.setItem(this);
  }

  public void removeRecipeItem(RecipeItemEntity recipeItemEntity) {
    recipeItems.remove(recipeItemEntity);
    recipeItemEntity.setItem(null);
  }

  public ItemEntity(Long id) {
    this.id = id;
  }

  public ItemEntity(Long id, String name, ItemtypeEntity itemtype) {
    this.id = id;
    this.name = name;
    this.itemtype = itemtype;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemEntity that = (ItemEntity) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
