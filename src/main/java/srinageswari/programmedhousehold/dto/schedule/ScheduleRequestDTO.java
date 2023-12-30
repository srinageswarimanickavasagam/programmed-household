package srinageswari.programmedhousehold.dto.schedule;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.model.Recipe;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Schedule request
 */
@Data
@NoArgsConstructor
public class ScheduleRequestDTO {
  private Long id;

  @NotNull private Date scheduledDt;

  @NotNull private Recipe recipe;
}
