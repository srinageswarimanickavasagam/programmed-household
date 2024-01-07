package srinageswari.programmedhousehold.coreservice.client;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author smanickavasagam
 *     <p>Constant variables used in the project
 */
public final class Constants {
  private Constants() {}

  @Value("${spring.jackson.date-format}")
  private static String dateFormat;

  public static final String FIELD_PARSE_ERROR = "Failed parse field type DATE {}";
  public static final String FIELD_TYPE_ERROR = "Can not use between for {} field type";

  public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(dateFormat);
}
