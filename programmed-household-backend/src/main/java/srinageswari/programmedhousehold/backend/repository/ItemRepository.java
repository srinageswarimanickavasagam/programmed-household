package srinageswari.programmedhousehold.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.backend.model.ItemEntity;

/**
 * @author smanickavasagam
 */
@Repository
public interface ItemRepository
    extends JpaRepository<ItemEntity, Long>, JpaSpecificationExecutor<ItemEntity> {

  boolean existsByNameIgnoreCase(String name);

  @Query(
      value =
          "SELECT i.* FROM item i JOIN itemtype it ON i.type_id = it.type_id WHERE i.in_stock = true AND it.fresh_fridge = true",
      nativeQuery = true)
  List<ItemEntity> findItemsInStockAndInFridge();
}
