package srinageswari.programmedhousehold.coreservice.controller;

import static srinageswari.programmedhousehold.coreservice.common.Constants.SUCCESS;

import jakarta.validation.Valid;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.common.dto.APIResponseDTO;
import srinageswari.programmedhousehold.coreservice.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.itemtype.ItemtypeRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.itemtype.ItemtypeResponseDTO;
import srinageswari.programmedhousehold.coreservice.service.itemtype.IItemtypeService;

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
  public ResponseEntity<APIResponseDTO<ItemtypeResponseDTO>> findById(@PathVariable long id) {
    final ItemtypeResponseDTO response = itemtypeService.findById(id);
    return ResponseEntity.ok(new APIResponseDTO<>(Instant.now().toEpochMilli(), SUCCESS, response));
  }

  /**
   * Fetches all itemtypes based on the given filter parameters
   *
   * @param request
   * @return Paginated itemtype data
   */
  @GetMapping("/itemtypes")
  public ResponseEntity<APIResponseDTO<Page<ItemtypeResponseDTO>>> findAll(
      @RequestBody SearchRequestDTO request) {
    final Page<ItemtypeResponseDTO> response = itemtypeService.findAll(request);
    return ResponseEntity.ok(new APIResponseDTO<>(Instant.now().toEpochMilli(), SUCCESS, response));
  }

  /**
   * Creates a new itemtype
   *
   * @return id of the created itemtype
   */
  @PostMapping("/itemtype")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> create(
      @Valid @RequestBody ItemtypeRequestDTO request) {
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
      @Valid @RequestBody ItemtypeRequestDTO request) {
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
