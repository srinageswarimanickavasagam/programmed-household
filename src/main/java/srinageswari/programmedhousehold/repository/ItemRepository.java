package srinageswari.programmedhousehold.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.model.ItemEntity;

/**
 * @author smanickavasagam
 */
@Repository
public interface ItemRepository
    extends JpaRepository<ItemEntity, Long>, JpaSpecificationExecutor<ItemEntity> {

  boolean existsByNameIgnoreCase(String name);
}
