package srinageswari.programmedhousehold.elasticsearch.dto;

import java.util.List;
import lombok.Data;

@Data
public class BulkInsertResponseDTO {
  private boolean errors;
  private List<IndexResponseDTO> items;
  private Long took;
}
