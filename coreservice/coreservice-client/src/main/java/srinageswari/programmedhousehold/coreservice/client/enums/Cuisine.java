package srinageswari.programmedhousehold.coreservice.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Cuisine {
  SOUTHINDIAN("South-Indian"),
  EUROPEAN("European"),
  NORTHINDIAN("North-Indian");
  private String label;
}
