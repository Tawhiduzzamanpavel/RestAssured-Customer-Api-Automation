import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class Customer {
    Properties prop=new Properties();
    FileInputStream file;

    {
        try {
            file = new FileInputStream("./src/test/resources/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String token;

    public void callingLoginAPI() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .body(
                                "{\"username\":\"salman\",\n" +
                                        "    \"password\":\"salman1234\"}"
                        ).
                        when()
                        .post("/customer/api/v1/login").
                        then()
                        .assertThat().statusCode( 200 ).extract().response();
        System.out.println(res.asString());
        JsonPath jsonpath = res.jsonPath();
        token = jsonpath.get("token");
        Utils.setEnvVariable("token",token);

    }

    public void customerList() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization",prop.getProperty("token")).
                                when()
                        .get("/customer/api/v1/list").
                                then()
                        .assertThat().statusCode( 200 ).extract().response();

        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("Customers[0].id").toString(),"101");
        System.out.println(res.asString());
    }

    public void searchCustomer() throws IOException {
        prop.load(file);

        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization",prop.getProperty("token")).
                                when()
                        .get("/customer/api/v1/get/101").
                                then()
                        .assertThat().statusCode( 200 ).extract().response();

        JsonPath jsonPath = res.jsonPath();
        //Assert.assertEquals(jsonPath.get("name").toString(),"Mr. Kamal");
        System.out.println(res.asString());
    }

    public void createCustomer() throws IOException, ConfigurationException {

        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json").header("Authorization",prop.getProperty("token"))
                        .body(
                                "{\n" +
                                        "    \"id\":"+prop.getProperty("id")+",\n" +
                                        "    \"name\":\""+prop.getProperty("name")+"\",\n" +
                                        "    \"email\":\""+prop.getProperty("email")+"\",\n" +
                                        "    \"address\":\""+prop.getProperty("address")+"\",\n" +
                                        "    \"phone_number\":\""+prop.getProperty("phone_number")+"\"\n" +
                                        "}"
                        ).

                        when()
                        .post("/customer/api/v1/create").
                        then()
                        .assertThat().statusCode( 201 ).extract().response();
        JsonPath jsonPath = res.jsonPath();
        Assert.assertEquals(jsonPath.get("message").toString(),"Success");
        System.out.println(res.asString());
//        Utils.setEnvVariable("id",jsonPath.get("Customers.id").toString());
//        Utils.setEnvVariable("name",jsonPath.get("Customers.name"));
//        Utils.setEnvVariable("email",jsonPath.get("Customers.email"));
//        Utils.setEnvVariable("phone_number",jsonPath.get("Customers.phone_number"));
//        Utils.setEnvVariable("address",jsonPath.get("Customers.phone_number"));






    }
    public void NewCustomer() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl1");
        Response res1 =
                (Response) given()
                        .contentType("application/json").
                                when()
                        .get("/").
                                then()
                        .assertThat().statusCode( 200 ).extract().response();

        JsonPath jsonPath = res1.jsonPath();
        System.out.println(res1.asString());
        int idd = (int)(Math.random()*(10000-100+1)+100);
        String id = String.valueOf(idd);
        String fname = jsonPath.get("results[0].name.first").toString();
       String lname = jsonPath.get("results[0].name.last").toString();
       String name =fname+" "+lname;
       String email =jsonPath.get("results[0].email").toString();
        String phone_number =jsonPath.get("results[0].phone").toString();
        String city=jsonPath.get("results[0].location.city").toString();
        String country=jsonPath.get("results[0].location.country").toString();
        String address=city + " " +country;
       Utils.setEnvVariable("id", id);
       Utils.setEnvVariable("name",name);
       Utils.setEnvVariable("email",email);
       Utils.setEnvVariable("phone_number",phone_number);
        Utils.setEnvVariable("address",address);

        //System.out.println(name);

    }
    public void updateCustomer() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        String id = prop.getProperty("id");
        Response res =
                given()
                        .contentType("application/json").header("Authorization",prop.getProperty("token"))
                        .body(
                                "{\n" +
                                        "    \"id\":"+id+",\n" +
                                        "    \"name\":\"Rahim Udddin\",\n" +
                                        "    \"email\":\"rahimm@test.com\",\n" +
                                        "    \"address\":\"Gulsshan, Dhaka\",\n" +
                                        "    \"phone_number\":\"011502212471\"\n" +
                                        "}"
                        ).
                        when()
                        .put("customer/api/v1/update/"+id).
                        then()
                        .assertThat().statusCode( 200 ).extract().response();
        System.out.println(res.asString());
        JsonPath jsonpath = res.jsonPath();

    }
    public void deleteCustomer() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        String id = prop.getProperty("id");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization",prop.getProperty("token")).
                                when()
                        .delete("/customer/api/v1/delete/"+id).
                                then()
                        .assertThat().statusCode( 200 ).extract().response();

        JsonPath jsonPath = res.jsonPath();
        Assert.assertEquals(jsonPath.get("message").toString(),"Customer deleted!");
        System.out.println(res.asString());

    }


}
