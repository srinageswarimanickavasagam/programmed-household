package srinageswari.programmedhousehold.dto.item;

import java.util.Date;
import lombok.Data;
import srinageswari.programmedhousehold.model.Item;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Item response
 */
@Data
public class ItemResponseDTO {

  private Long id;
  private String name;
  private int stockQty;
  private String unit;
  private Date stockedDt;
  private String itemtype;

  public ItemResponseDTO(Item item) {
    this.id = item.getId();
    this.name = item.getName();
    this.stockQty = item.getStockQty();
    this.unit = item.getUnit().getLabel();
    this.stockedDt = item.getStockedDt();
    this.itemtype = item.getItemtype().getType();
  }
}
