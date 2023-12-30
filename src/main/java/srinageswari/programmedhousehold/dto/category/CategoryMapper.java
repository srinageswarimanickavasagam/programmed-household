package srinageswari.programmedhousehold.dto.category;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.Category;

/**
 * @author smanickavasagam
 *     <p>Mapper for CategoryRequest
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

  Category toEntity(CategoryRequestDTO dto);

  CategoryRequestDTO toDto(Category entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget Category entity, CategoryRequestDTO dto) {
    entity.setName(WordUtils.capitalizeFully(dto.getName()));
  }
}
