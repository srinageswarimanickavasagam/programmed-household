package srinageswari.programmedhousehold.coreservice.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object used for returning id value of the saved entity
 */
@Value
@RequiredArgsConstructor
@Builder
public class CommandResponseDTO {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  Long id;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  int size;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  String response;
}
