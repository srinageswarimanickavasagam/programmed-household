package srinageswari.programmedhousehold.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.backend.enums.Unit;

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

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("stockedDt")
  private Date stockedDt;

  @JsonProperty("itemtype")
  @NotNull
  private ItemtypeDTO itemtype;

  @JsonProperty("inStock")
  private boolean inStock;

  @JsonProperty("stockUnit")
  @NotNull
  private Unit stockUnit;

  @JsonProperty("recipeUnit")
  @NotNull
  private Unit recipeUnit;
}
