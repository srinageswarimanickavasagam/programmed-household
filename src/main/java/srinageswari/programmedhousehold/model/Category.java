package srinageswari.programmedhousehold.model;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import srinageswari.programmedhousehold.common.enums.Day;
import srinageswari.programmedhousehold.common.enums.Difficulty;
import srinageswari.programmedhousehold.common.enums.Meal;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {

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

  private boolean isActive;

  @OneToMany(mappedBy = "category")
  private Set<Recipe> recipes;

  public Category(Long categoryId) {
    this.categoryId = categoryId;
  }

  public Category(
      Long categoryId,
      String name,
      Meal meal,
      boolean sidedish,
      Day day,
      Difficulty difficulty,
      boolean isActive) {
    this.categoryId = categoryId;
    this.name = name;
    this.meal = meal;
    this.sidedish = sidedish;
    this.day = day;
    this.difficulty = difficulty;
    this.isActive = isActive;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Category)) return false;
    Category category = (Category) o;
    return getCategoryId() != null && Objects.equals(getCategoryId(), category.getCategoryId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
