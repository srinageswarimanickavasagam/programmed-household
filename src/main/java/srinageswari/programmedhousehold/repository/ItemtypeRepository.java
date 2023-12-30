package srinageswari.programmedhousehold.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.model.Itemtype;

/**
 * @author smanickavasagam
 */
@Repository
public interface ItemtypeRepository
    extends JpaRepository<Itemtype, Long>, JpaSpecificationExecutor<Itemtype> {}
