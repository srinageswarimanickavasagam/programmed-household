package srinageswari.programmedhousehold.coreservice.server.controller;

import jakarta.validation.Valid;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.client.dto.ItemDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.server.common.Constants;
import srinageswari.programmedhousehold.coreservice.server.service.item.IItemService;

/**
 * @author smanickavasagam
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {

  private final IItemService itemService;

  /**
   * Fetches item by id
   *
   * @param id
   * @return A single item
   */
  @GetMapping("/item/{id}")
  public ResponseEntity<APIResponseDTO<ItemDTO>> findById(@PathVariable long id) {
    final ItemDTO response = itemService.findById(id);
    return ResponseEntity.ok(
        new APIResponseDTO<>(Instant.now().toEpochMilli(), Constants.SUCCESS, response));
  }

  /**
   * Fetches all items based on the given filter parameters
   *
   * @param request
   * @return Paginated item data
   */
  @GetMapping("/items")
  public ResponseEntity<APIResponseDTO<Page<ItemDTO>>> findAll(
      @RequestBody SearchRequestDTO request) {
    final Page<ItemDTO> response = itemService.findAll(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(Instant.now().toEpochMilli(), Constants.SUCCESS, response));
  }

  /**
   * Creates a new item
   *
   * @return id of the created item
   */
  @PostMapping("/item")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> create(
      @Valid @RequestBody ItemDTO request) {
    final CommandResponseDTO response = itemService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(Instant.now().toEpochMilli(), Constants.SUCCESS, response));
  }

  /**
   * Updates given item
   *
   * @return id of the updated item
   */
  @PutMapping("/item")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> update(
      @Valid @RequestBody ItemDTO request) {
    final CommandResponseDTO response = itemService.update(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(Instant.now().toEpochMilli(), Constants.SUCCESS, response));
  }

  /**
   * Deletes item by id
   *
   * @return id of the deleted item
   */
  @DeleteMapping("/item/{id}")
  public ResponseEntity<APIResponseDTO<Void>> deleteById(@PathVariable long id) {
    itemService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
