package srinageswari.programmedhousehold.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import srinageswari.programmedhousehold.BaseIntegrationTest;
import srinageswari.programmedhousehold.dto.recipe.RecipeRequestDTO;
import srinageswari.programmedhousehold.model.AppUserEntity;
import srinageswari.programmedhousehold.service.appuser.AppUserServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean AppUserServiceImpl appUserServiceImpl;

  @BeforeEach
  public void before() throws IOException {
    AppUserEntity appUserEntity = setupAppUserData("AppUser1.json");
    Mockito.when(appUserServiceImpl.getCurrentLoggedInUser()).thenReturn(appUserEntity);
  }

  @Test
  void saveRecipeEntity() {
    try {
      // given
      setupCategoryData("Category1.json");
      setupItemtypeData("ItemType1.json");
      setupItemData("Item1.json");

      RecipeRequestDTO recipeRequestDTO =
          jsonToObjectConverter("Recipe1.json", RecipeRequestDTO.class);

      // when - action or behaviour that we are going test
      ResultActions response =
          mockMvc.perform(
              post("/api/v1/recipe")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(recipeRequestDTO)));

      // then - verify the result or output using assert statements
      response.andDo(print()).andExpect(status().isCreated());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @AfterEach
  public void cleanup() {
    cleanupData();
  }
}
