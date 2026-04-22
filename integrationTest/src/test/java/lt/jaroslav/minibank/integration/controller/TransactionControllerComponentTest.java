package lt.jaroslav.minibank.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Duration;
import lt.jaroslav.minibank.transaction.api.model.request.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class TransactionControllerComponentTest {

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
  void transferShouldCreatePendingTransactionWhenBalanceIsSufficient() {
    TransactionRequest request = new TransactionRequest(1L, 2L, BigDecimal.valueOf(45.00));

    webTestClient.post()
        .uri("/transactions/transfer")
        .bodyValue(request)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("$.id").isNumber()
        .jsonPath("$.debtorAccountId").isEqualTo(1)
        .jsonPath("$.creditorAccountId").isEqualTo(2)
        .jsonPath("$.amount").isEqualTo(45.00)
        .jsonPath("$.status").isEqualTo("PENDING");
  }

  @Test
  void transferShouldCreateFailedTransactionWhenBalanceIsInsufficient() {
    TransactionRequest request = new TransactionRequest(2L, 1L, BigDecimal.valueOf(1500.00));

    webTestClient.post()
        .uri("/transactions/transfer")
        .bodyValue(request)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("$.status").isEqualTo("FAILED");
  }

  @Test
  void getTransactionShouldReturnExistingTransaction() {
    webTestClient.get()
        .uri("/transactions/{id}", 1)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isEqualTo(1)
        .jsonPath("$.debtorAccountId").isEqualTo(1)
        .jsonPath("$.creditorAccountId").isEqualTo(2)
        .jsonPath("$.status").isEqualTo("COMPLETED");
  }

  @Test
  void getTransactionsShouldReturnPrefilledTransactions() {
    webTestClient.get()
        .uri("/transactions")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.length()").value(size -> assertThat(((Number) size).intValue()).isGreaterThanOrEqualTo(2));
  }
}
