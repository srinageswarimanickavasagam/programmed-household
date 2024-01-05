package srinageswari.programmedhousehold.coreservice.model;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;

  @Column(nullable = false, length = 50, unique = true)
  private String name;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Meal meal;

  // by default 0 - maindish, if 1 - sidedish
  private boolean sidedish;

  @Enumerated(value = EnumType.STRING)
  private Day day;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Difficulty difficulty;

  @OneToMany(mappedBy = "category")
  private Set<RecipeEntity> recipes;

  public CategoryEntity(Long categoryId) {
    this.categoryId = categoryId;
  }

  public CategoryEntity(
      Long categoryId, String name, Meal meal, boolean sidedish, Day day, Difficulty difficulty) {
    this.categoryId = categoryId;
    this.name = name;
    this.meal = meal;
    this.sidedish = sidedish;
    this.day = day;
    this.difficulty = difficulty;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CategoryEntity)) return false;
    CategoryEntity categoryEntity = (CategoryEntity) o;
    return getCategoryId() != null
        && Objects.equals(getCategoryId(), categoryEntity.getCategoryId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
