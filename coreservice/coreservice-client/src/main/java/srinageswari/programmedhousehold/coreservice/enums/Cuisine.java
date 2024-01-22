package srinageswari.programmedhousehold.coreservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Cuisine {
  EUROPEAN("European"),
  INDIAN("Indian");
  private String label;
}
