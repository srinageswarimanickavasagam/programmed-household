package srinageswari.programmedhousehold.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Unit {
  KILOGRAM("kg"),
  GRAM("g"),
  MILLILITER("ml"),
  LITER("l"),
  TEASPOON("tsp"),
  TABLESPOON("tbsp"),
  CUP("cup");

  private String label;
}
