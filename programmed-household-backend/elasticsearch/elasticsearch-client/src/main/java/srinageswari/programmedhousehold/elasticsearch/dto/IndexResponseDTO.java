package srinageswari.programmedhousehold.elasticsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IndexResponseDTO {

  @JsonProperty("_id")
  private String id;

  @JsonProperty("_index")
  private String index;

  @JsonProperty("_primary_term")
  private Long primaryTerm;

  private String result;

  @JsonProperty("_seq_no")
  private Long sequenceNumber;

  @JsonProperty("_shards")
  private ShardsDTO shards;

  @JsonProperty("_version")
  private Long version;

  @JsonProperty("_error")
  private String error;
}
