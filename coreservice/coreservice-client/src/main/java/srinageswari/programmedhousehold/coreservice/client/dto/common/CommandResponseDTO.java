package srinageswari.programmedhousehold.coreservice.client.dto.common;

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
  Long id;
}
