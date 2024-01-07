package srinageswari.programmedhousehold.coreservice.controller;

import static srinageswari.programmedhousehold.coreservice.common.Constants.SUCCESS;

import jakarta.validation.Valid;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.service.itemtype.IItemtypeService;
import srinageswari.programmedhousehold.coreservice.dto.ItemtypeDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemtypeController {

  private final IItemtypeService itemtypeService;

  /**
   * Fetches itemtype by id
   *
   * @param id
   * @return A single itemtype
   */
  @GetMapping("/itemtype/{id}")
  public ResponseEntity<APIResponseDTO<ItemtypeDTO>> findById(@PathVariable long id) {
    final ItemtypeDTO response = itemtypeService.findById(id);
    return ResponseEntity.ok(new APIResponseDTO<>(Instant.now().toEpochMilli(), SUCCESS, response));
  }

  /**
   * Fetches all itemtypes based on the given filter parameters
   *
   * @param request
   * @return Paginated itemtype data
   */
  @GetMapping("/itemtypes")
  public ResponseEntity<APIResponseDTO<Page<ItemtypeDTO>>> findAll(
      @RequestBody SearchRequestDTO request) {
    final Page<ItemtypeDTO> response = itemtypeService.findAll(request);
    return ResponseEntity.ok(new APIResponseDTO<>(Instant.now().toEpochMilli(), SUCCESS, response));
  }

  /**
   * Creates a new itemtype
   *
   * @return id of the created itemtype
   */
  @PostMapping("/itemtype")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> create(
      @Valid @RequestBody ItemtypeDTO request) {
    final CommandResponseDTO response = itemtypeService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(Instant.now().toEpochMilli(), SUCCESS, response));
  }

  /**
   * Updates given itemtype
   *
   * @return id of the updated itemtype
   */
  @PutMapping("/itemtype")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> update(
      @Valid @RequestBody ItemtypeDTO request) {
    final CommandResponseDTO response = itemtypeService.update(request);
    return ResponseEntity.ok(new APIResponseDTO<>(Instant.now().toEpochMilli(), SUCCESS, response));
  }

  /**
   * Deletes itemtype by id
   *
   * @return id of the deleted itemtype
   */
  @DeleteMapping("/itemtype/{id}")
  public ResponseEntity<APIResponseDTO<Void>> deleteById(@PathVariable long id) {
    itemtypeService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
