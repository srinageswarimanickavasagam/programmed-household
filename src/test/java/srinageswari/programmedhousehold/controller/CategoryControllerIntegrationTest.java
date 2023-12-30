package srinageswari.programmedhousehold.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import srinageswari.programmedhousehold.AbstractContainerBaseTest;
import srinageswari.programmedhousehold.common.enums.Day;
import srinageswari.programmedhousehold.common.enums.Difficulty;
import srinageswari.programmedhousehold.common.enums.Meal;
import srinageswari.programmedhousehold.dto.category.CategoryRequestDTO;
import srinageswari.programmedhousehold.repository.CategoryRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest extends AbstractContainerBaseTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired CategoryRepository categoryRepository;

  @BeforeEach
  void cleanup() {
    categoryRepository.deleteAll();
  }

  @Test
  void saveCategoryEntity() {
    // given
    CategoryRequestDTO category = new CategoryRequestDTO();
    category.setId(1L);
    category.setName("Biryani");
    category.setDay(Day.SUNDAY);
    category.setDifficulty(Difficulty.HARD);
    category.setMeal(Meal.LUNCH);
    category.setActive(true);

    // when - action or behaviour that we are going test
    ResultActions response;
    try {
      response =
          mockMvc.perform(
              post("/api/v1/category")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(category)));

      // then - verify the result or output using assert statements
      response.andDo(print()).andExpect(status().isCreated());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
