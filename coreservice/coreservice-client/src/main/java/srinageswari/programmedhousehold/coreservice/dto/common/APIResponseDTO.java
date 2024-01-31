package srinageswari.programmedhousehold.coreservice.dto.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
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

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;

  private final String message;
  private final T data;

  public APIResponseDTO(LocalDateTime timestamp, String message) {
    this.timestamp = timestamp;
    this.message = message;
    this.data = null;
  }
}
