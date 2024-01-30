package srinageswari.programmedhousehold.coreservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Meal {
  BREAKFAST("Breakfast"),
  LUNCH("Lunch"),
  DINNER("Dinner"),
  MISC("Misc");
  private String label;
}
