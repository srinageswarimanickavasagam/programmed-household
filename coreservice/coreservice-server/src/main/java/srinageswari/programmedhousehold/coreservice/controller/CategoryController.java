package srinageswari.programmedhousehold.coreservice.controller;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.service.category.ICategoryService;
import srinageswari.programmedhousehold.coreservice.dto.CategoryDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

  private final ICategoryService categoryService;

  /**
   * Fetches category by id
   *
   * @param id
   * @return A single category
   */
  @GetMapping("/category/{id}")
  public ResponseEntity<APIResponseDTO<CategoryDTO>> findById(@PathVariable long id) {
    final CategoryDTO response = categoryService.findById(id);
    return ResponseEntity.ok(
        new APIResponseDTO<>(Instant.now().toEpochMilli(), Constants.SUCCESS, response));
  }

  /**
   * Fetches all categories based on the given category filter parameters
   *
   * @param request
   * @return Paginated category data
   */
  @GetMapping("/categories")
  public ResponseEntity<APIResponseDTO<Page<CategoryDTO>>> findAll(
      @RequestBody SearchRequestDTO request) {
    final Page<CategoryDTO> response = categoryService.findAll(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(Instant.now().toEpochMilli(), Constants.SUCCESS, response));
  }

  /**
   * Creates a new category
   *
   * @return id of the created category
   */
  @PostMapping("/category")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> create(
      @RequestBody CategoryDTO request) {
    final CommandResponseDTO response = categoryService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(Instant.now().toEpochMilli(), Constants.SUCCESS, response));
  }

  /**
   * Updates given category
   *
   * @return id of the updated category
   */
  @PutMapping("/category")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> update(
      @RequestBody CategoryDTO request) {
    final CommandResponseDTO response = categoryService.update(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(Instant.now().toEpochMilli(), Constants.SUCCESS, response));
  }

  /**
   * Deletes category by id
   *
   * @return id of the deleted category
   */
  @DeleteMapping("/category/{id}")
  public ResponseEntity<APIResponseDTO<Void>> deleteById(@PathVariable long id) {
    categoryService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
