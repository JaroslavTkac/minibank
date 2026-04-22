package lt.jaroslav.minibank.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Duration;
import lt.jaroslav.minibank.account.api.model.request.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@Sql(scripts = "classpath:db/testdata-integration.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AccountControllerComponentTest {

  @LocalServerPort
  private int port;

  private WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    this.webTestClient = WebTestClient.bindToServer()
        .baseUrl("http://localhost:" + port)
        .responseTimeout(Duration.ofSeconds(10))
        .build();
  }

  @Test
  void createAccountShouldReturnCreatedAccount() {
    AccountRequest request = new AccountRequest("Integration User", BigDecimal.valueOf(325.75));

    webTestClient.post()
        .uri("/accounts")
        .bodyValue(request)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("$.id").isNumber()
        .jsonPath("$.ownerName").isEqualTo("Integration User")
        .jsonPath("$.balance").isEqualTo(325.75);
  }

  @Test
  void getAccountShouldReturnExistingAccount() {
    webTestClient.get()
        .uri("/accounts/{id}", 1)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isEqualTo(1)
        .jsonPath("$.ownerName").isEqualTo("Alice Johnson")
        .jsonPath("$.balance").isEqualTo(1500.00);
  }

  @Test
  void getAccountShouldReturnNotFoundForMissingAccount() {
    webTestClient.get()
        .uri("/accounts/{id}", 99999)
        .exchange()
        .expectStatus().isNotFound()
        .expectBody()
        .jsonPath("$.status").isEqualTo(404)
        .jsonPath("$.error").isEqualTo("Not Found")
        .jsonPath("$.message").value(message -> assertThat((String) message).contains("Account not found"))
        .jsonPath("$.path").isEqualTo("/accounts/99999");
  }

  @Test
  void getAccountsShouldReturnPrefilledAccounts() {
    webTestClient.get()
        .uri("/accounts")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.length()").value(size -> assertThat(((Number) size).intValue()).isGreaterThanOrEqualTo(3));
  }
}
