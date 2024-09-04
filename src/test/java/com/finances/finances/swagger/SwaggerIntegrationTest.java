package com.finances.finances.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.finances.finances.config.AbstractTestContainerConfig;
import com.finances.finances.config.TestsConfigConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SwaggerIntegrationTest extends AbstractTestContainerConfig {

  @DisplayName("Junit test for should display Swagger UI Page")
  @Test
  void testShouldDisplaySwaggerUiPage() {

    String content =
        given()
            .basePath("/swagger-ui/index.html")
            .port(TestsConfigConstants.SERVER_PORT)
            .when()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

    assertTrue(content.contains("Swagger UI"));
  }
}
