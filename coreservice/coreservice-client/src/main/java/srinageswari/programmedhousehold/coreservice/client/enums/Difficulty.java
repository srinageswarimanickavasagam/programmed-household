package srinageswari.programmedhousehold.coreservice.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Difficulty {
  EASY("Easy"),
  MODERATE("Moderate"),
  HARD("Hard");

  private String label;
}
