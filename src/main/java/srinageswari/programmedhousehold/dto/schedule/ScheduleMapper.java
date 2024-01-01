package srinageswari.programmedhousehold.dto.schedule;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.ScheduleEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for ScheduleRequest
 */
@Mapper(componentModel = "spring")
public interface ScheduleMapper {

  ScheduleMapper MAPPER = Mappers.getMapper(ScheduleMapper.class);

  ScheduleEntity toEntity(ScheduleRequestDTO dto);

  ScheduleRequestDTO toDto(ScheduleEntity scheduleEntity);

  @AfterMapping
  default void capitalizeFully(
      @MappingTarget ScheduleEntity scheduleEntity, ScheduleRequestDTO dto) {
    scheduleEntity.setId(Long.valueOf(WordUtils.capitalizeFully(dto.getId().toString())));
  }
}
