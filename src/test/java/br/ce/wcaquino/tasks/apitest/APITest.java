package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class APITest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend/todo";
	}

	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/")
		.then()
			.statusCode(200)
			;
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso()	{
		 String requestBody = "{ \"task\": \"tesete\", \"dueDate\": \"2022-12-30\" }";

		RestAssured.given()
            .header("Content-type", "application/json")
            .and()
            .body(requestBody)
            .when()
	            .post("/")
            .then()
            	.statusCode(201)
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida()	{
		 String requestBody = "{ \"task\": \"tesete\", \"dueDate\": \"2010-12-30\" }";

		RestAssured.given()
            .header("Content-type", "application/json")
            .and()
            .body(requestBody)
           .when()
	            .post("/")
            .then()
            	.log().all() 
            	.statusCode(400)
            	.body("message", CoreMatchers.is("Due date must not be in past"))
			;
	}
	
}

