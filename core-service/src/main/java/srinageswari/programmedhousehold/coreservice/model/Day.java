package srinageswari.programmedhousehold.coreservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Day {
  SUNDAY("Sunday"),
  MONDAY("Monday"),
  TUESDAY("Tuesday"),
  WEDNESDAY("Wednesday"),
  THURSDAY("Thursday"),
  FRIDAY("Friday"),
  SATURDAY("Saturday");

  private String label;
}
