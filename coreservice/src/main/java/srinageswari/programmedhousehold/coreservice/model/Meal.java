package srinageswari.programmedhousehold.coreservice.model;

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
  SNACKS("Snacks");
  private String label;
}
