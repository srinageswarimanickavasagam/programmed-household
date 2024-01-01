package srinageswari.programmedhousehold.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;
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
@Table(name = "schedule")
public class ScheduleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date scheduledDt;

  @OneToOne
  @JoinColumn(name = "recipe_id")
  private RecipeEntity recipe;

  public void setId(Long id) {
    this.id = id;
  }

  public ScheduleEntity(RecipeEntity recipe) {
    this.recipe = recipe;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ScheduleEntity)) return false;
    ScheduleEntity scheduleEntity = (ScheduleEntity) o;
    return getId() != null && Objects.equals(getId(), scheduleEntity.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
