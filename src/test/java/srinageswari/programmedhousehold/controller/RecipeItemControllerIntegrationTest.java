package srinageswari.programmedhousehold.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import srinageswari.programmedhousehold.BaseIntegrationTest;
import srinageswari.programmedhousehold.dto.category.CategoryRequestDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RecipeItemControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  // @Test
  void saveCategoryEntity() {
    // given
    try {
      // given
      CategoryRequestDTO category =
          jsonToObjectConverter("Category1.json", CategoryRequestDTO.class);

      // when - action or behaviour that we are going test
      ResultActions response =
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

  @AfterEach
  void cleanup() {
    cleanupData();
  }
}
