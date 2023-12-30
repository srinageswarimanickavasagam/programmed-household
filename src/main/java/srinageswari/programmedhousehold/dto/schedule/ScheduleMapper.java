package srinageswari.programmedhousehold.dto.schedule;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.Schedule;

/**
 * @author smanickavasagam
 *     <p>Mapper for ScheduleRequest
 */
@Mapper(componentModel = "spring")
public interface ScheduleMapper {

  ScheduleMapper MAPPER = Mappers.getMapper(ScheduleMapper.class);

  Schedule toEntity(ScheduleRequestDTO dto);

  ScheduleRequestDTO toDto(Schedule schedule);

  @AfterMapping
  default void capitalizeFully(@MappingTarget Schedule schedule, ScheduleRequestDTO dto) {
    schedule.setId(Long.valueOf(WordUtils.capitalizeFully(dto.getId().toString())));
  }
}
