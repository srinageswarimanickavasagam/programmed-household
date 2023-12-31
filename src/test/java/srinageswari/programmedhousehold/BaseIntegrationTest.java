package srinageswari.programmedhousehold;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.controller.AbstractContainerBaseTest;
import srinageswari.programmedhousehold.model.AppUser;
import srinageswari.programmedhousehold.model.Category;
import srinageswari.programmedhousehold.model.Item;
import srinageswari.programmedhousehold.model.Itemtype;
import srinageswari.programmedhousehold.repository.*;

@SpringBootTest
@Transactional
public class BaseIntegrationTest extends AbstractContainerBaseTest {

  @Autowired ScheduleRepository scheduleRepository;

  @Autowired RecipeIngredientRepository recipeIngredientRepository;

  @Autowired RecipeRepository recipeRepository;

  @Autowired CategoryRepository categoryRepository;

  @Autowired AppUserRepository appUserRepository;

  @Autowired ItemtypeRepository itemtypeRepository;

  @Autowired ItemRepository itemRepository;

  private final String filePath = "data/integrationtest/";

  public void setupCategoryData(String fileName) throws IOException {
    Category category = jsonToObjectConverter(fileName, Category.class);

    if (!TestTransaction.isActive()) TestTransaction.start();
    categoryRepository.save(category);
    TestTransaction.flagForCommit();
    TestTransaction.end();
  }

  public AppUser setupAppUserData(String fileName) throws IOException {
    AppUser appUser = jsonToObjectConverter(fileName, AppUser.class);

    if (!TestTransaction.isActive()) TestTransaction.start();
    appUser = appUserRepository.save(appUser);
    TestTransaction.flagForCommit();
    TestTransaction.end();

    return appUser;
  }

  public void setupItemData(String fileName) throws IOException {
    Item item = jsonToObjectConverter(fileName, Item.class);

    if (!TestTransaction.isActive()) TestTransaction.start();
    itemRepository.save(item);
    TestTransaction.flagForCommit();
    TestTransaction.end();
  }

  public void setupItemtypeData(String fileName) throws IOException {
    Itemtype itemtype = jsonToObjectConverter(fileName, Itemtype.class);

    if (!TestTransaction.isActive()) TestTransaction.start();
    itemtypeRepository.save(itemtype);
    TestTransaction.flagForCommit();
    TestTransaction.end();
  }

  /*
   * we use the static methods from TestTransaction to flag the current transaction for a commit and end it afterwards, resulting in a commit.
   * After the commit, we open a new transaction and try to play with the same table data within a new transaction started by TestTransaction.start() to see whether the instance has been persisted/saved/deleted
   */

  // deleting in the order schedule, recipe ingredient and then recipe & category due to mapping
  // constraints between the entities
  public void cleanupData() {
    if (!TestTransaction.isActive()) TestTransaction.start();

    scheduleRepository.deleteAll();
    TestTransaction.flagForCommit();
    TestTransaction.end();

    TestTransaction.start();
    recipeIngredientRepository.deleteAll();
    TestTransaction.flagForCommit();
    TestTransaction.end();

    TestTransaction.start();
    recipeRepository.deleteAll();
    TestTransaction.flagForCommit();
    TestTransaction.end();

    TestTransaction.start();
    categoryRepository.deleteAll();
    TestTransaction.flagForCommit();
    TestTransaction.end();
  }

  public <T> T jsonToObjectConverter(String fileName, Class<T> dtoType) throws IOException {
    // The class loader that loaded the class
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(filePath + fileName);

    // the stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + filePath + fileName);
    } else {
      return new ObjectMapper().readValue(inputStream, dtoType);
    }
  }
}
