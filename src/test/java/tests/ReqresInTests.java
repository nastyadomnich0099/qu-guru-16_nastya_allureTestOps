package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInTests {

 /*
        1. make request to  https://reqres.in/api/login
        2. get response {"total":20,"used":0,"queued":0,"pending":0,"browsers":{"android":{"8.1":{}},"chrome":
        {"100.0":{},"99.0":{}},"chrome-mobile":{"86.0":{}},"firefox":{"97.0":{},"98.0":{}},"opera":{"84.0":{},"85.0":{}}}}
        3. check total is 20
     */
 @Test
    void LoginTests(){
     String body ="{ \"email\": \"eve.holt@reqres.in\","+" \"password\": \"cityslicka\" }"; //todo bad practice

     given()
             .log().uri()
             .log().body()
             .contentType(JSON)
             .body(body)
             .when()
             .post("https://reqres.in/api/login")
             .then()
             .log().status()
             .log().body()
             .statusCode(200)
             .body("token", is ("QpwL5tke4Pnpja7X4"));
 }


 @Test
 void negative415LoginTest() {
  given()
          .log().uri()
          .log().body()
          .when()
          .post("https://reqres.in/api/login")
          .then()
          .log().status()
          .log().body()
          .statusCode(415);
 }

 @Test
 void negative400LoginTest() {
  given()
          .log().uri()
          .log().body()
          .body("123")
          .when()
          .post("https://reqres.in/api/login")
          .then()
          .log().status()
          .log().body()
          .statusCode(400)
          .body("error", is("Missing email or username"));
 }

 @Test
 void negativeMissingPasswordLoginTest() {
  String body = "{ \"email\": \"eve.holt@reqres.in\"}"; // todo bad practice

  given()
          .log().uri()
          .log().body()
          .contentType(JSON)
          .body(body)
          .when()
          .post("https://reqres.in/api/login")
          .then()
          .log().status()
          .log().body()
          .statusCode(400)
          .body("error", is("Missing password"));
 }



 @Test
 public void createUserTest1() {
  given()
          .log().uri()
          .log().body()
          .contentType(JSON)
          .body("{ \"name\": \"morpheus\"}")
          .when()
          .post("https://reqres.in/api/users")
          .then()
          .log().all()
          .statusCode(201)
          .body("name", is("morpheus"));

 }


 @Test
 public void createUserTestFirst() {
  given()
          .log().uri()
          .log().body()
          .contentType(JSON)
          .body("{ \"name\": \"morpheus\"}")
          .when()
          .post("https://reqres.in/api/users")
          .then()
          .log().all()
          .statusCode(201)
          .body("name", is("morpheus"));

 }

 @Test
 public void deleteUserTestSecond() {
  given()
          .log().uri()
          .log().body()
          .contentType(JSON)
          .when()
          .delete("https://reqres.in/api/users/2")
          .then()
          .log().all()
          .statusCode(204);
 }

 @Test
 public void updateUserTestSecond() {
  given()
          .log().uri()
          .log().body()
          .contentType(JSON)
          .body("{ \"name\": \"lulu\"}")
          .when()
          .put("https://reqres.in/api/users/2")
          .then()
          .log().all()
          .statusCode(200);
 }


 @Test
 void RegesterTests(){
  String body ="{ \"email\": \"eve.holt@reqres.in\","+" \"password\": \"pistol\" }"; //todo bad practice

  given()
          .log().uri()
          .log().body()
          .contentType(JSON)
          .body(body)
          .when()
          .post("https://reqres.in/api/register")
          .then()
          .log().status()
          .log().body()
          .statusCode(200)
          .body("token", is ("QpwL5tke4Pnpja7X4"));
 }

 @Test
 void RegesterUnsuccessfulTests(){
  String body ="{ \"email\": \"sydney@fife\","+" \"password\": \"pistol\" }"; //todo bad practice

  given()
          .log().uri()
          .log().body()
          .contentType(JSON)
          .body(body)
          .when()
          .post("https://reqres.in/api/register")
          .then()
          .log().status()
          .log().body()
          .statusCode(400)
          .body("error", is("Note: Only defined users succeed registration"));
 }



}
