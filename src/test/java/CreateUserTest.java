import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserTest {

    public String baseURI = "https://demoqa.com";
    public JSONObject requestBody;

    @Test
    public void metodaTest(){

        System.out.println("====== STEP 1: Create account ======");
        createAccount();

        System.out.println("====== STEP 2: Generate token ======");
        generateToken();

    }

    public void createAccount(){
        // Definesc configurarea clientului
        //RestAssured.baseURI = "https://demoqa.com/";

        //Definim un request
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(baseURI);

        String username = "CorinaK" + System.currentTimeMillis();
        requestBody = new JSONObject();
        requestBody.put("userName", username);
        requestBody.put("password", "!Welcome123");

        // Adaugam request body la request
        request.body(requestBody.toString());

        // Executam requestul de tip POST la un endpoint specific
        Response response = request.post("/Account/v1/User");

        // Validam response status code
        System.out.println(response.getStatusCode());
        //response.getBody().prettyPrint(); // prettyPrint() are deja sout in interior
        Assert.assertEquals(response.getStatusCode(), 201);

        Assert.assertTrue(response.getStatusLine().contains("Created"));

        ResponseBody responseBody = response.getBody();
        Assert.assertTrue(responseBody.asPrettyString().contains(username));
    }

    public void generateToken(){

        //definesc configurarea clientului
        //definim un request
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(baseURI);

        //adaugam request body
        request.body(requestBody.toString());

        //executam requestul de tip POST la un endpoint specific
        Response response = request.post("/Account/v1/GenerateToken");

        //validam response status code
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertTrue(response.getStatusLine().contains("OK"));

        ResponseBody responseBody = response.getBody();
        Assert.assertTrue(responseBody.asPrettyString().contains("token"));
    }

}
