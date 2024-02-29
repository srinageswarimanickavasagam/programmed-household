package srinageswari.programmedhousehold;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.backend.controller.AbstractContainerBaseTest;
import srinageswari.programmedhousehold.backend.model.AppUserEntity;
import srinageswari.programmedhousehold.backend.model.CategoryEntity;
import srinageswari.programmedhousehold.backend.model.ItemEntity;
import srinageswari.programmedhousehold.backend.model.ItemtypeEntity;
import srinageswari.programmedhousehold.backend.repository.*;

@SpringBootTest
@Transactional
public class BaseIntegrationTest extends AbstractContainerBaseTest {

  @Autowired RecipeItemRepository recipeItemRepository;

  @Autowired RecipeRepository recipeRepository;

  @Autowired CategoryRepository categoryRepository;

  @Autowired AppUserRepository appUserRepository;

  @Autowired ItemtypeRepository itemtypeRepository;

  @Autowired ItemRepository itemRepository;

  private final String filePath = "data/integrationtest/";

  public void setupCategoryData(String fileName) throws IOException {
    CategoryEntity categoryEntity = jsonToObjectConverter(fileName, CategoryEntity.class);

    if (!TestTransaction.isActive()) TestTransaction.start();
    categoryRepository.save(categoryEntity);
    TestTransaction.flagForCommit();
    TestTransaction.end();
  }

  public AppUserEntity setupAppUserData(String fileName) throws IOException {
    AppUserEntity appUserEntity = jsonToObjectConverter(fileName, AppUserEntity.class);

    if (!TestTransaction.isActive()) TestTransaction.start();
    appUserEntity = appUserRepository.save(appUserEntity);
    TestTransaction.flagForCommit();
    TestTransaction.end();

    return appUserEntity;
  }

  public void setupItemData(String fileName) throws IOException {
    ItemEntity itemEntity = jsonToObjectConverter(fileName, ItemEntity.class);

    if (!TestTransaction.isActive()) TestTransaction.start();
    itemRepository.save(itemEntity);
    TestTransaction.flagForCommit();
    TestTransaction.end();
  }

  public void setupItemtypeData(String fileName) throws IOException {
    ItemtypeEntity itemtypeEntity = jsonToObjectConverter(fileName, ItemtypeEntity.class);

    if (!TestTransaction.isActive()) TestTransaction.start();
    itemtypeRepository.save(itemtypeEntity);
    TestTransaction.flagForCommit();
    TestTransaction.end();
  }

  /*
   * we use the static methods from TestTransaction to flag the current transaction for a commit and end it afterwards, resulting in a commit.
   * After the commit, we open a new transaction and try to play with the same table data within a new transaction started by TestTransaction.start() to see whether the instance has been persisted/saved/deleted
   */

  // deleting in the order, recipe item, item and then recipe & category due to mapping
  // constraints between the entities
  public void cleanupData() {
    if (!TestTransaction.isActive()) TestTransaction.start();

    recipeItemRepository.deleteAll();
    TestTransaction.flagForCommit();
    TestTransaction.end();

    TestTransaction.start();
    itemRepository.deleteAll();
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
