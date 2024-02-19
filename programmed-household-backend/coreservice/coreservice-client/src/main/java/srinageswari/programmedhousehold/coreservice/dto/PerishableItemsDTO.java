package srinageswari.programmedhousehold.coreservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class PerishableItemsDTO {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("storageDt")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date storageDt;

  @JsonProperty("useBy")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date useBy;

  @JsonProperty("quantity")
  private String quantity;

  public PerishableItemsDTO() {
    if (this.id == null) this.id = uuidToLong(UUID.randomUUID());
  }

  public static Long uuidToLong(UUID uuid) {
    long mostSigBits = uuid.getMostSignificantBits();
    long leastSigBits = uuid.getLeastSignificantBits();

    // Combine the most and least significant bits to create a long value
    return (mostSigBits & Long.MAX_VALUE) ^ (leastSigBits & Long.MAX_VALUE);
  }
}
