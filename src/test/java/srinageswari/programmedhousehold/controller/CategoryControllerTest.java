package srinageswari.programmedhousehold.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class CategoryControllerTest {
  @Container
  private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");

  @Test
  void test() {
    assertThat(MY_SQL_CONTAINER.isRunning()).isTrue();
  }
}
