package srinageswari.programmedhousehold.coreservice.enums;

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
  LITRE("l"),
  TEASPOON("tsp"),
  TABLESPOON("tbsp"),
  CUP("cup"),
  COUNT("count"),
  PACK("pack");

  private String label;
}
