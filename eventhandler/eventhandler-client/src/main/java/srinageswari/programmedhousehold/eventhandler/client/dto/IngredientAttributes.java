package srinageswari.programmedhousehold.elasticsearch.client.dto;

import java.util.Date;
import lombok.Data;

@Data
public class IngredientAttributes {
  private int ingredientId;
  private String ingredientName;
  private int stockQty;
  private String unit;
  private String ingredientType;
  private int requiredQty;
  private Date stockedDt;
}
