package srinageswari.programmedhousehold.model;

import jakarta.persistence.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import srinageswari.programmedhousehold.common.enums.HealthLabel;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(length = 100)
  private String description;

  private Integer prepTime;

  private Integer cookTime;

  private Integer servings;

  @Lob
  @Column(nullable = false)
  private String instructions;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Cuisine cuisine;

  @Enumerated(value = EnumType.STRING)
  private HealthLabel healthLabel;

  @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "user_id")
  AppUser appUser;

  @ManyToOne
  @JoinColumn(name = "category_id")
  Category category;

  @OneToOne(mappedBy = "recipe")
  private Schedule schedule;

  private boolean isActive;

  public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
    recipeIngredients.add(recipeIngredient);
    recipeIngredient.setRecipe(this);
  }

  public void removeRecipeIngredient(RecipeIngredient recipeIngredient) {
    recipeIngredients.remove(recipeIngredient);
    recipeIngredient.setRecipe(null);
  }

  public Recipe(Long id) {
    this.id = id;
  }

  public Recipe(
      Long id,
      String title,
      String description,
      Integer prepTime,
      Integer cookTime,
      Integer servings,
      String instructions,
      HealthLabel healthLabel,
      Cuisine cuisine,
      boolean isActive) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.prepTime = prepTime;
    this.cookTime = cookTime;
    this.servings = servings;
    this.instructions = instructions;
    this.healthLabel = healthLabel;
    this.cuisine = cuisine;
    this.isActive = isActive;
  }

  public void setAppUser(AppUser appUser) {
    this.appUser = appUser;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Recipe)) return false;
    Recipe recipe = (Recipe) o;
    return getId() != null && Objects.equals(getId(), recipe.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
