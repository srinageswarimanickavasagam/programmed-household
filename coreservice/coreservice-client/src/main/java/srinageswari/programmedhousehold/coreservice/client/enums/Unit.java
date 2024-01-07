package srinageswari.programmedhousehold.coreservice.client.enums;

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
  CUP("cup"),
  COUNT("count");

  private String label;
}
