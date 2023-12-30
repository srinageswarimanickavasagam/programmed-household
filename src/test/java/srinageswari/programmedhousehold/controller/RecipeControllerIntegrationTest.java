package srinageswari.programmedhousehold.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import srinageswari.programmedhousehold.AbstractContainerBaseTest;
import srinageswari.programmedhousehold.common.enums.*;
import srinageswari.programmedhousehold.dto.category.CategoryMapper;
import srinageswari.programmedhousehold.dto.category.CategoryRequestDTO;
import srinageswari.programmedhousehold.dto.recipe.RecipeRequestDTO;
import srinageswari.programmedhousehold.dto.recipeingredient.RecipeIngredientMapper;
import srinageswari.programmedhousehold.dto.recipeingredient.RecipeIngredientRequestDTO;
import srinageswari.programmedhousehold.model.AppUser;
import srinageswari.programmedhousehold.model.Category;
import srinageswari.programmedhousehold.model.Cuisine;
import srinageswari.programmedhousehold.repository.*;
import srinageswari.programmedhousehold.service.appuser.AppUserServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest extends AbstractContainerBaseTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired RecipeRepository recipeRepository;

  @Autowired CategoryRepository categoryRepository;

  @Autowired RecipeIngredientRepository recipeIngredientRepository;

  @Autowired CategoryMapper categoryMapper;

  @Autowired RecipeIngredientMapper recipeIngredientMapper;

  Category category;

  @Autowired AppUserRepository appUserRepository;

  @Autowired ScheduleRepository scheduleRepository;

  @MockBean AppUserServiceImpl appUserServiceImpl;

  @BeforeEach
  public void setup() {
    CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
    categoryRequestDTO.setId(1L);
    categoryRequestDTO.setName("Biryani");
    categoryRequestDTO.setDay(Day.SUNDAY);
    categoryRequestDTO.setDifficulty(Difficulty.HARD);
    categoryRequestDTO.setMeal(Meal.LUNCH);
    categoryRequestDTO.setActive(true);
    category = categoryRepository.save(categoryMapper.toEntity(categoryRequestDTO));

    AppUser appUser = new AppUser();
    appUser.setUserId(1L);
    appUser.setEmail("dummy@gmail.com");
    appUser.setProvider("google");
    appUser.setCreatedAt(LocalDateTime.now());
    appUser = appUserRepository.save(appUser);

    Mockito.when(appUserServiceImpl.getCurrentLoggedInUser()).thenReturn(appUser);
  }

  @Test
  void saveRecipeEntity() {
    // given
    RecipeRequestDTO recipe = new RecipeRequestDTO();
    recipe.setId(1L);
    recipe.setActive(true);
    recipe.setCuisine(Cuisine.SOUTHINDIAN);
    recipe.setAppUser(new AppUser(1L));
    recipe.setDescription("Chicken Biryani Description");
    recipe.setCookTime(30);
    recipe.setPrepTime(30);
    recipe.setTitle("Chicken Biryani");
    recipe.setInstructions("Cooking Instructions");
    recipe.setHealthLabel(HealthLabel.DEFAULT);
    recipe.setServings(2);

    List<RecipeIngredientRequestDTO> recipeIngredientList = new ArrayList<>();
    RecipeIngredientRequestDTO recipeIngredientRequestDTO = new RecipeIngredientRequestDTO();
    recipeIngredientRequestDTO.setIngredientId(0L);
    recipeIngredientRequestDTO.setIngredientName("Garam Masala");
    recipeIngredientRequestDTO.setIngredientQty(200);
    recipeIngredientRequestDTO.setUnit(Unit.GRAM);
    recipeIngredientList.add(recipeIngredientRequestDTO);
    recipe.setRecipeIngredients(recipeIngredientList);
    recipe.setCategory(category);

    // when - action or behaviour that we are going test
    ResultActions response;
    try {
      response =
          mockMvc.perform(
              post("/api/v1/recipe")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(recipe)));

      // then - verify the result or output using assert statements
      response.andDo(print()).andExpect(status().isCreated());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // deleting in the order schedule, recipe ingredient and then recipe & category due to mapping
  // constraints
  @AfterEach
  public void cleanup() {
    scheduleRepository.deleteAll();
    recipeIngredientRepository.deleteAll();
    recipeRepository.deleteAll();
    categoryRepository.deleteAll();
  }
}
