package srinageswari.programmedhousehold.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShardsDTO {
  private Number failed;
  private Number successful;
  private Number total;
}
