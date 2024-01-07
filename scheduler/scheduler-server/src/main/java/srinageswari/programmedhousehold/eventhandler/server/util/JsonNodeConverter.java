package srinageswari.programmedhousehold.eventhandler.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {
  private static final Logger logger = LoggerFactory.getLogger(JsonNodeConverter.class);

  /**
   * @param jsonNode
   * @return
   */
  @Override
  public String convertToDatabaseColumn(JsonNode jsonNode) {
    if (jsonNode == null) {
      logger.warn("jsonNode input is null, returning null");
      return null;
    }

    String jsonNodeString = jsonNode.toPrettyString();
    return jsonNodeString;
  }

  /**
   * @param jsonNodeString
   * @return
   */
  @Override
  public JsonNode convertToEntityAttribute(String jsonNodeString) {

    if (StringUtils.isEmpty(jsonNodeString)) {
      logger.warn("jsonNodeString input is empty, returning null");
      return null;
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readTree(jsonNodeString);
    } catch (JsonProcessingException e) {
      logger.error("Error parsing jsonNodeString", e);
    }
    return null;
  }
}
