package srinageswari.programmedhousehold.coreservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.enums.Unit;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Item
 */
@Data
@NoArgsConstructor
public class ItemDTO {

  @JsonProperty("itemId")
  private Long id;

  @JsonProperty("name")
  @NotNull
  private String name;

  @JsonProperty("itemStockQty")
  private int itemStockQty;

  @JsonProperty("unit")
  private Unit unit;

  @JsonProperty("stockedDt")
  private Date stockedDt;

  @JsonProperty("itemtype")
  @NotNull
  private ItemtypeDTO itemtype;

  @JsonProperty("essential")
  private boolean essential;
}
