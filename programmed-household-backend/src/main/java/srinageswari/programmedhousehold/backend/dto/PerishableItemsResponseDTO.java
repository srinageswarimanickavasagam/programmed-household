package srinageswari.programmedhousehold.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class PerishableItemsResponseDTO {

  @JsonProperty("perishableItems")
  Map<String, List<PerishableItemsDTO>> typePerishableItemsMap = new HashMap<>();
}
