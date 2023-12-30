package srinageswari.programmedhousehold.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.model.Schedule;

/**
 * @author smanickavasagam
 */
@Repository
public interface ScheduleRepository
    extends JpaRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule> {}
