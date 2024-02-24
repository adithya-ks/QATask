package demoapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductAddTest {

	@DataProvider(name = "productData")
	public Object[][] createTestData() {
		return new Object[][] { {
				"{\"title\":\"New Product\", \"description\":\"A new product description\", \"price\":100, \"brand\":\"Test Brand\", \"category\":\"Test Category\"}",
				200 }, { "{\"description\":\"Missing title\", \"price\":100}", 400 },
				{ "{\"title\":\"Invalid Price\", \"description\":\"Test\", \"price\":\"one hundred\", \"category\":\"Test\"}",
						400 },
				{ "{\"title\":\"" + "Looooooooo".repeat(100)
						+ "\", \"description\":\"Long title\", \"price\":100, \"category\":\"Test\"}", 400 },
				{ "{\"title\":\"Special! @#%\", \"description\":\"Contains special characters *&^%\", \"price\":100, \"category\":\"Test\"}",
						200 },
				{ "{\"title\":\"Existing Product\", \"description\":\"A product with an existing name\", \"price\":150, \"brand\":\"Test Brand\", \"category\":\"Test Category\"}",
						200 },
				{ "{\"title\":\"Negative Price\", \"description\":\"A product with a negative price\", \"price\":-100, \"category\":\"Test\"}",
						400 } };
	}

	@Test(dataProvider = "productData")
	public void testAddProduct(String requestBody, int expectedStatusCode) {
		RestAssured.baseURI = "https://dummyjson.com/products/add";
		Response response = given().header("Content-type", "application/json").and().body(requestBody).when().post()
				.then().extract().response();

		Assert.assertEquals(response.statusCode(), expectedStatusCode, "Expected status code does not match!");
	}
}
