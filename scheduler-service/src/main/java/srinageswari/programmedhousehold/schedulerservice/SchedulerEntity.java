package srinageswari.programmedhousehold.schedulerservice;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import srinageswari.programmedhousehold.schedulerservice.util.JsonNodeConverter;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scheduler")
public class SchedulerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date scheduledDt;

  @Convert(converter = JsonNodeConverter.class)
  private JsonNode recipe;

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SchedulerEntity)) return false;
    SchedulerEntity schedulerEntity = (SchedulerEntity) o;
    return getId() != null && Objects.equals(getId(), schedulerEntity.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
