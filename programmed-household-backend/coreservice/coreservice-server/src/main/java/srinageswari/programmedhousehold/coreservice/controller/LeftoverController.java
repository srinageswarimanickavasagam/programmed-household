package srinageswari.programmedhousehold.coreservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.dto.PerishableItemsDTO;
import srinageswari.programmedhousehold.coreservice.service.leftover.LeftoverService;

/**
 * @author smanickavasagam
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LeftoverController {
  private final LeftoverService leftoverService;

  @PostMapping("/leftover/add")
  public ResponseEntity<Void> addLeftover(@RequestBody PerishableItemsDTO leftover) {
    leftoverService.addLeftover(leftover);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/leftover/update")
  public ResponseEntity<Void> updateLeftover(@RequestBody PerishableItemsDTO leftover) {
    leftoverService.updateLeftover(leftover);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/leftovers")
  public ResponseEntity<List<PerishableItemsDTO>> listLeftovers() {
    List<PerishableItemsDTO> leftovers = leftoverService.listLeftovers();
    return ResponseEntity.ok(leftovers);
  }

  @DeleteMapping("/leftover/remove/{id}")
  public ResponseEntity<Void> removeLeftover(@PathVariable Long id) {
    leftoverService.removeLeftover(id);
    return ResponseEntity.noContent().build();
  }
}
