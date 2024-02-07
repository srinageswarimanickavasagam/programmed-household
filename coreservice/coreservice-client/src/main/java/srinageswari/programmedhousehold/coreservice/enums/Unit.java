package srinageswari.programmedhousehold.coreservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Unit {
  GRAM("g"),
  MILLILITER("ml"),
  TEASPOON("tsp"),
  TABLESPOON("tbsp"),
  CUP("cup"),
  COUNT("count"),
  NA("not applicable");

  private final String label;
}
