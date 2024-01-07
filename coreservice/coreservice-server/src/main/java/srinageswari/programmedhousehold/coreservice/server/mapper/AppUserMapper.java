package srinageswari.programmedhousehold.coreservice.server.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.coreservice.client.dto.AppUserDTO;
import srinageswari.programmedhousehold.coreservice.server.model.AppUserEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for UserRequest
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper {

  AppUserMapper MAPPER = Mappers.getMapper(AppUserMapper.class);

  AppUserEntity toEntity(AppUserDTO dto);

  AppUserDTO toDto(AppUserEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget AppUserEntity entity, AppUserDTO dto) {
    entity.setEmail((WordUtils.capitalizeFully(dto.getEmail())));
  }
}
