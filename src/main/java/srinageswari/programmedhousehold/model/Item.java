package srinageswari.programmedhousehold.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
@Table(name = "item")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 50)
  private String name;

  @Enumerated(value = EnumType.STRING)
  private Unit unit;

  @ManyToOne
  @JoinColumn(name = "type_id")
  Itemtype itemtype;

  private int stockQty;

  private float amount;

  private Date stockedDt;

  private boolean isEssential;

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

  public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
    recipeIngredients.add(recipeIngredient);
    recipeIngredient.setItem(this);
  }

  public void removeRecipeIngredient(RecipeIngredient recipeIngredient) {
    recipeIngredients.remove(recipeIngredient);
    recipeIngredient.setItem(null);
  }

  public Item(Long id) {
    this.id = id;
  }

  public Item(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item that = (Item) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
