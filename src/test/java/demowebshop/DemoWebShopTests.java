package demowebshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemoWebShopTests extends TestBase {


    @Test
    @Tag("demowebshop")
    @DisplayName("User registration UI Page Object tests")
    void userRegistrationTest() {
        registrationPage.openPage()
                .setUserGender()
                .setFirstName(testData.firstName)
                .setUserLastName(testData.lastName)
                .setUserEmail(testData.userEmail)
                .setUserPassword(testData.password)
                .setConfirmUserPassword(testData.password)
                .setUserRegister()
                .checkResult();
    }

    @Test
    @Tag("demowebshop")
    @DisplayName("Add to shopping cart API test")
    void addToCart() {

        String cookieName = "Nop.customer=c41f50fe-b033-4922-ac6c-f458006014dc;";
        given()
               // .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(cookieName)
                .body("addtocart_13.EnteredQuantity=1")
                .log().all()
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/13/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));

    }

    @Test
    @Tag("demowebshop")
    @DisplayName("Successfully authorized user API+UI test")
    void userAuthTest() {
        String cookieName = "NOPCOMMERCE.AUTH";
        String authCookieValue = given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", testData.email)
                .formParam("Password", testData.password)
                .log().all()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(cookieName);

        open("/login");
        Cookie authCookie = new Cookie(cookieName, authCookieValue);
        getWebDriver().manage().addCookie(authCookie);
        open("");
        $(".account").shouldHave(text(testData.email));

    }
//RECHECK ABOVE




    @Test
    @Tag("demowebshop")
    @DisplayName("Edit user account API+UI lambda step tests")
    void userAccountEditTest() {
        String cookieName = "NOPCOMMERCE.AUTH";
        step("Open registered user account through API", () -> {
            String authCookieValue = given()
                  //  .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .formParam("Email", testData.email)
                    .formParam("Password", testData.password)
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract()
                    .cookie(cookieName);
            step("Open any content to set up cookie", () ->
                    open("/login"));
            step("Set up cookie to browser", () ->
                    getWebDriver().manage().addCookie(
                            new Cookie(cookieName, authCookieValue)));
        });
        step("Open user info page", () ->
                open("/customer/info"));
        step("Edit user first name", () ->
                $("#FirstName").setValue("Alex Malkovich"));
        step("Edit user last name", () ->
                $("#LastName").setValue("Ferher"));
        step("Save details", () ->
                $("[name=save-info-button]").click());
        step("Open user addresses page", () ->
                $(".inactive").click());
        step("Add new address button", () ->
                $(".add-button").click());
        step("Set first name", () ->
                $("#Address_FirstName").setValue("Jason Born"));
        step("Set last name", () ->
                $("#Address_LastName").setValue("Smith"));
        step("Set email", () ->
                $("#Address_Email").setValue(testData.email));
        step("Set country", () ->
                $("#Address_CountryId").$(byText("Canada")).click());
        step("Set city", () ->
                $("#Address_City").setValue("Woodshed"));
        step("Set address", () ->
                $("#Address_Address1").setValue("1 New Line Street"));
        step("Set zip code", () ->
                $("#Address_ZipPostalCode").setValue("567243"));
        step("Set phone number", () ->
                $("#Address_PhoneNumber").setValue("1034568760"));
        step("Save details", () ->
                $(".button-1.save-address-button").click());
        step("Check account info updated", () ->
                $(".page-body").shouldHave(text("Jason Born Smith")));

    }



}
