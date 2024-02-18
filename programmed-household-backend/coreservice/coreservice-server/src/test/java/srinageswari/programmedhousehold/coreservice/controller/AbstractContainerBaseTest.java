package srinageswari.programmedhousehold.coreservice.controller;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/*
 * The singleton container is started only once when the base class is loaded. The container can then be used by all inheriting test classes. At the end of the test suite the Ryuk container that is started by Testcontainers core will take care of stopping the singleton container.
 *
 */
@Testcontainers
public abstract class AbstractContainerBaseTest {

  public static final MySQLContainer MY_SQL_CONTAINER;

  static {
    MY_SQL_CONTAINER = new MySQLContainer("mysql:8.2.0");
    MY_SQL_CONTAINER.start();
  }

  @DynamicPropertySource
  public static void configureTestProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
  }
}
