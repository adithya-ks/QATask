package demoapi;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class Search {

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://dummyjson.com/products/search";
	}

	@Test
	public void testSearchWithQueryPhone() {
		RestAssured.given().queryParam("q", "phone").when().get().then().statusCode(200).body("products.size()",
				greaterThan(0));
	}

	@Test
	public void testSearchWithSpecificProductName() {

		RestAssured.given().queryParam("q", "iPhone").when().get().then().statusCode(200).body("products.title",
				everyItem(containsString("iPhone")));
	}

	@Test
	public void testSearchWithoutQuery() {
		RestAssured.when().get().then().statusCode(400);
	}

	@Test
	public void testSearchWithSpecialCharacters() {
		RestAssured.given().queryParam("q", "@#$%").when().get().then().statusCode(200);
	}

	@Test
	public void testSearchWithNonExistentProduct() {
		RestAssured.given().queryParam("q", "nonexistentproduct").when().get().then().statusCode(200)
				.body("products.size()", equalTo(0));
	}

	@Test
	public void testSearchWithLongString() {
		String longString = "longstring" + "a".repeat(1000);
		RestAssured.given().queryParam("q", longString).when().get().then().statusCode(200);
	}

	@Test
	public void testSearchWithNumericQuery() {
		RestAssured.given().queryParam("q", "123456").when().get().then().statusCode(200);
	}

	@Test
	public void testSearchWithMixedCaseQuery() {
		RestAssured.given().queryParam("q", "PhoNe").when().get().then().statusCode(200).body("products.title",
				everyItem(containsStringIgnoringCase("phone")));
	}
}