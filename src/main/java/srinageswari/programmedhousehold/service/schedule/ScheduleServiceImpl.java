package srinageswari.programmedhousehold.service.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.dto.category.CategoryRequestDTO;
import srinageswari.programmedhousehold.dto.category.CategoryResponseDTO;

/**
 * @author smanickavasagam
 */
@Slf4j(topic = "ScheduleService")
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements IScheduleService {
  @Override
  public CategoryResponseDTO findById(Long id) {
    return null;
  }

  @Override
  public Page<CategoryResponseDTO> findAll(SearchRequestDTO request) {
    return null;
  }

  @Override
  public CommandResponseDTO create(CategoryRequestDTO request) {
    return null;
  }

  @Override
  public CommandResponseDTO update(CategoryRequestDTO request) {
    return null;
  }

  @Override
  public void deleteById(Long id) {}
}
