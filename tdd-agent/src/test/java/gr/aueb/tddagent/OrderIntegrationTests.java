package gr.aueb.tddagent;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import gr.aueb.tddagent.persistence.OrderRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.datasource.url=jdbc:tc:postgresql:16:///tdd_agent" 
    }
)
@Sql(
	scripts = "classpath:sql/seed-data.sql", 
	executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class OrderIntegrationTests {
    @LocalServerPort
    private Integer port;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

	@Test
	void successfulRegistrationAndGetOrders() {
		var registerRequest = Map.of(
			"email", "test@test.com",
			"username", "testuser",
			"password", "Test123!"
		);

		Response registerResponse = given()
			.contentType(ContentType.JSON)
			.body(registerRequest)
			.when()
			.post("/api/v1/register")
			.then()
			.extract().response();

		assertEquals(201, registerResponse.statusCode());
		String token = registerResponse.jsonPath().getString("access_token");
		assertNotNull(token);

		Response ordersResponse = given()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + token)
			.when()
			.get("/api/v1/orders")
			.then()
			.extract().response();

		assertEquals(200, ordersResponse.statusCode());
		List<Map<String, Object>> orders = ordersResponse.jsonPath().get("orders");
		assertTrue(orders.isEmpty()); // New user has no orders
	}

	@Test
	void unsuccessfulRegistrationWithDuplicateEmail() {
		var registerRequest = Map.of(
			"email", "user@qnr.com.gr", // Should be using existing email from seed data
			"username", "qnr-user", 
			"password", "Test123!"
		);

		Response registerResponse = given()
			.contentType(ContentType.JSON)
			.body(registerRequest)
			.when()
			.post("/api/v1/register")
			.then()
			.extract().response();

		assertEquals(409, registerResponse.statusCode());
	}

	@Test
	void unsuccessfulRegistrationWithDuplicateUsername() {
		var registerRequest = Map.of(
			"email", "qnr-user@qnr.com.gr", 
			"username", "user", // Should be using existing username from seed data
			"password", "Test123!"
		);

		Response registerResponse = given()
			.contentType(ContentType.JSON)
			.body(registerRequest)
			.when()
			.post("/api/v1/register")
			.then()
			.extract().response();

		assertEquals(409, registerResponse.statusCode());
	}

	@Test
	void successfulLoginAndOrderOperations() {
		var loginRequest = Map.of(
			"email", "user@qnr.com.gr",
			"password", "User123!"
		);

		Response loginResponse = given()
			.contentType(ContentType.JSON)
			.body(loginRequest)
			.when()
			.post("/api/v1/login")
			.then()
			.extract().response();

		assertEquals(200, loginResponse.statusCode());
		String token = loginResponse.jsonPath().getString("access_token");
		assertNotNull(token);

		// Get all orders
		Response allOrdersResponse = given()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + token)
			.when()
			.get("/api/v1/orders")
			.then()
			.extract().response();

		assertEquals(200, allOrdersResponse.statusCode());
		List<Map<String, Object>> allOrders = allOrdersResponse.jsonPath().get("orders");
		assertEquals(10, allOrders.size());

		// Get pending orders
		Response pendingOrdersResponse = given()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + token)
			.when()
			.get("/api/v1/orders?status=PENDING")
			.then()
			.extract().response();

		assertEquals(200, pendingOrdersResponse.statusCode());
		List<Map<String, Object>> pendingOrders = pendingOrdersResponse.jsonPath().get("orders");
		assertTrue(pendingOrders.stream().allMatch(order -> order.get("status").equals("PENDING")));
	}
}
