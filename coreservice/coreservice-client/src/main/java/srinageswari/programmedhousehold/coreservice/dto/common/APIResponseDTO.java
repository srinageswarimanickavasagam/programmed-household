package srinageswari.programmedhousehold.coreservice.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author smanickavasagam
 *     <p>Builds API response in a proper format with timestamp, message and data
 * @param <T>
 */
@Data
@AllArgsConstructor
public class APIResponseDTO<T> {

  private Long timestamp;
  private final String message;
  private final T data;

  public APIResponseDTO(Long timestamp, String message) {
    this.timestamp = timestamp;
    this.message = message;
    this.data = null;
  }
}
