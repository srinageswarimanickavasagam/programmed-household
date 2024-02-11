package srinageswari.programmedhousehold.coreservice.service.leftover;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import srinageswari.programmedhousehold.coreservice.dto.PerishableItemsDTO;

/** Service used for adding, removing, updating leftovers. */
@Slf4j(topic = "LeftoverService")
@Service
@RequiredArgsConstructor
public class LeftoverService {
  private final String filepath = "coreservice/coreservice-server/transactional-data/leftover.json";
  private static final Logger logger = LoggerFactory.getLogger(LeftoverService.class);
  private static final ObjectMapper objectMapper =
      new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  public void addLeftover(PerishableItemsDTO leftover) {
    List<PerishableItemsDTO> leftovers = loadLeftoversFromFile();
    leftovers.add(leftover);
    saveLeftoversToFile(leftovers);
  }

  public List<PerishableItemsDTO> listLeftovers() {
    return loadLeftoversFromFile();
  }

  public void removeLeftover(Long id) {
    List<PerishableItemsDTO> leftovers = loadLeftoversFromFile();
    leftovers.removeIf(leftover -> Objects.equals(leftover.getId(), id));
    saveLeftoversToFile(leftovers);
  }

  private List<PerishableItemsDTO> loadLeftoversFromFile() {
    List<PerishableItemsDTO> leftovers = new ArrayList<>();
    try {
      File file = new File(filepath);
      leftovers = objectMapper.readValue(file.getAbsoluteFile(), new TypeReference<>() {});
    } catch (IOException e) {
      logger.error("Error loading leftovers from file: " + e.getMessage());
    }
    return leftovers;
  }

  private void saveLeftoversToFile(List<PerishableItemsDTO> leftovers) {
    try {
      File file = new File(filepath);
      ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
      writer.writeValue(file.getAbsoluteFile(), leftovers);
    } catch (IOException e) {
      logger.error("Error saving leftovers to file: " + e.getMessage());
    }
  }

  public void updateLeftover(PerishableItemsDTO updatedLeftover) {
    List<PerishableItemsDTO> leftovers = loadLeftoversFromFile();
    for (int i = 0; i < leftovers.size(); i++) {
      PerishableItemsDTO oldLeftover = leftovers.get(i);
      if (Objects.equals(oldLeftover.getId(), updatedLeftover.getId())) {
        leftovers.set(i, updatedLeftover);
        saveLeftoversToFile(leftovers);
        return; // Exit loop once the record is updated
      }
    }
    logger.error("Leftover with ID {} not found for editing", updatedLeftover.getId());
  }
}
