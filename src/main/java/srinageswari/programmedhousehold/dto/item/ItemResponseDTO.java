package srinageswari.programmedhousehold.dto.item;

import java.util.Date;
import lombok.Data;
import srinageswari.programmedhousehold.model.ItemEntity;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Item response
 */
@Data
public class ItemResponseDTO {

  private Long id;
  private String name;
  private int itemStockQty;
  private String unit;
  private Date stockedDt;
  private String itemtype;

  public ItemResponseDTO(ItemEntity itemEntity) {
    this.id = itemEntity.getId();
    this.name = itemEntity.getName();
    this.itemStockQty = itemEntity.getItemStockQty();
    this.unit = itemEntity.getUnit().getLabel();
    this.stockedDt = itemEntity.getStockedDt();
    this.itemtype = itemEntity.getItemtype().getType();
  }
}
