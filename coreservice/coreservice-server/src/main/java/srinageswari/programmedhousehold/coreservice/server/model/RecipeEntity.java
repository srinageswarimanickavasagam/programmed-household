package srinageswari.programmedhousehold.coreservice.server.model;

import jakarta.persistence.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import srinageswari.programmedhousehold.coreservice.client.enums.Cuisine;
import srinageswari.programmedhousehold.coreservice.client.enums.HealthLabel;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
public class RecipeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50, unique = true)
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
  private Set<RecipeItemEntity> recipeItems = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "user_id")
  AppUserEntity appUser;

  @ManyToOne
  @JoinColumn(name = "category_id")
  CategoryEntity category;

  private Date scheduledDt;

  private boolean isActive;

  public void addRecipeItem(RecipeItemEntity recipeItemEntity) {
    recipeItems.add(recipeItemEntity);
    recipeItemEntity.setRecipe(this);
  }

  public void removeRecipeItem(RecipeItemEntity recipeItemEntity) {
    recipeItems.remove(recipeItemEntity);
    recipeItemEntity.setRecipe(null);
  }

  public RecipeEntity(Long id) {
    this.id = id;
  }

  public RecipeEntity(
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

  public void setAppUser(AppUserEntity appUserEntity) {
    this.appUser = appUserEntity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RecipeEntity)) return false;
    RecipeEntity recipeEntity = (RecipeEntity) o;
    return getId() != null && Objects.equals(getId(), recipeEntity.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
