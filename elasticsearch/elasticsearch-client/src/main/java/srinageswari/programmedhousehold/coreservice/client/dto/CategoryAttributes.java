package srinageswari.programmedhousehold.coreservice.client.dto;

import lombok.Data;

@Data
public class CategoryAttributes {
  private int categoryId;
  private String categoryName;
  private String scheduleDay;
  private String meal;
  private String difficulty;
}
