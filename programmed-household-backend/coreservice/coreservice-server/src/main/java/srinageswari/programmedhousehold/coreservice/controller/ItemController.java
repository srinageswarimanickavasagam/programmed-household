package srinageswari.programmedhousehold.coreservice.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.dto.ItemDTO;
import srinageswari.programmedhousehold.coreservice.dto.PerishableItemsResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.service.item.IItemService;

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
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
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
        new APIResponseDTO<>(
            LocalDateTime.now(), Constants.SUCCESS, response, response.getTotalElements()));
  }

  /**
   * Creates a new item
   *
   * @return id of the created item
   */
  @PostMapping("/item")
  public ResponseEntity<APIResponseDTO<ItemDTO>> create(@Valid @RequestBody ItemDTO request) {
    final ItemDTO response = itemService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
  }

  /**
   * Updates given item
   *
   * @return id of the updated item
   */
  @PutMapping("/item")
  public ResponseEntity<APIResponseDTO<ItemDTO>> update(@Valid @RequestBody ItemDTO request) {
    final ItemDTO response = itemService.update(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
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

  @GetMapping("/perishableItems")
  public ResponseEntity<APIResponseDTO<PerishableItemsResponseDTO>> getPerishableItems() {
    final PerishableItemsResponseDTO response = itemService.getPerishableItems();
    return ResponseEntity.ok(
        new APIResponseDTO<>(
            LocalDateTime.now(),
            Constants.SUCCESS,
            response,
            response.getTypePerishableItemsMap().size()));
  }
}
