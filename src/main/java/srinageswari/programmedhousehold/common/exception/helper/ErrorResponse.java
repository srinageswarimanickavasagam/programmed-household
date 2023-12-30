package srinageswari.programmedhousehold.common.exception.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;

/**
 * @author smanickavasagam
 *     <p>Builds exception handler response in a proper format with status, message, stackTrace and
 *     errors
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

  private final int status;
  private final String message;
  private String stackTrace;
  private List<ValidationError> errors;

  @Data
  private static class ValidationError {
    private final String field;
    private final String message;
  }

  /**
   * Adds validation error for handling unprocessable entity
   *
   * @param field
   * @param message
   */
  public void addValidationError(String field, String message) {
    if (Objects.isNull(errors)) {
      errors = new ArrayList<>();
    }
    errors.add(new ValidationError(field, message));
  }
}
