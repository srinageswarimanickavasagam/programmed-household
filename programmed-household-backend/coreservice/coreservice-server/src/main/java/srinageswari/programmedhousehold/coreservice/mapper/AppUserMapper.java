package srinageswari.programmedhousehold.coreservice.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import srinageswari.programmedhousehold.coreservice.dto.AppUserDTO;
import srinageswari.programmedhousehold.coreservice.model.AppUserEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for UserRequest
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper {
  AppUserEntity toEntity(AppUserDTO dto);

  AppUserDTO toDto(AppUserEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget AppUserEntity entity, AppUserDTO dto) {
    entity.setUserId(Long.valueOf((WordUtils.capitalizeFully(String.valueOf(dto.getUserId())))));
  }
}
