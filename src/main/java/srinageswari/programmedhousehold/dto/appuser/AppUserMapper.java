package srinageswari.programmedhousehold.dto.appuser;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.AppUser;

/**
 * @author smanickavasagam
 *     <p>Mapper for UserRequest
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper {

  AppUserMapper MAPPER = Mappers.getMapper(AppUserMapper.class);

  AppUser toEntity(AppUserRequestDTO dto);

  AppUserRequestDTO toDto(AppUser entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget AppUser entity, AppUserRequestDTO dto) {
    entity.setEmail((WordUtils.capitalizeFully(dto.getEmail())));
  }
}
