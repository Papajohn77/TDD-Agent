package gr.aueb.tddagent;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    public void testGetOrders() {
		// Get all orders
		Response allOrdersResponse = given()
			.contentType(ContentType.JSON)
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
			.when()
			.get("/api/v1/orders?status=PENDING")
			.then()
			.extract().response();

		assertEquals(200, pendingOrdersResponse.statusCode());
		List<Map<String, Object>> pendingOrders = pendingOrdersResponse.jsonPath().get("orders");
		assertTrue(pendingOrders.stream().allMatch(order -> order.get("status").equals("PENDING")));
    }

    @Test
	public void testUpdateOrderStatus() {
		Response updateResponse = given()
			.contentType(ContentType.JSON)
			.body(Map.of("status", "CONFIRMED"))
			.when()
			.patch("/api/v1/orders/123e4567-e89b-12d3-a456-426614174000/status")
			.then()
			.extract().response();

		assertEquals(200, updateResponse.statusCode());
	}

    @Test
	public void testUpdateNonExistentOrder() {
		Response updateResponse = given()
			.contentType(ContentType.JSON)
			.body(Map.of("status", "CONFIRMED"))
			.when()
			.patch("/api/v1/orders/123e4567-e89b-12d3-a456-426614173999/status")
			.then()
			.extract().response();

		assertEquals(404, updateResponse.statusCode());
	}
}
