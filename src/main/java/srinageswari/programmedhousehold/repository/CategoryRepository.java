package srinageswari.programmedhousehold.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.model.Category;

/**
 * @author smanickavasagam
 */
@Repository
public interface CategoryRepository
    extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {}
