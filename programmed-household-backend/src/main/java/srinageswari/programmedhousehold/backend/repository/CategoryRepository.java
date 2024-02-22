package srinageswari.programmedhousehold.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.backend.model.CategoryEntity;

/**
 * @author smanickavasagam
 */
@Repository
public interface CategoryRepository
    extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {}
