package com.swagger;

import com.api.dataobjects.TestList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class PetsStoreTest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String USER_URI = "/user";
    private static final String USER_LOGIN_URI = "/user/login";
    private static final String CREATE_USER_LIST = "/user/createWithList";
    private static final String USER_LOGOUT = "/user/logout";

    private static final String ADD_NEW_PET = "/pet";


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    private static final String username = "Fastuser13";
    private static final String password = "testpassword";
    private static final String email = "testuser@example.com";

    @Test
    public void testCreateUser() {

        String requestBody = "{\n" +
                "  \"id\": 0,\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"firstName\": \"Test\",\n" +
                "  \"lastName\": \"User\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\",\n" +
                "  \"phone\": \"1234567890\"\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(USER_URI)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post();

        Response response1 = getUser(username);

        assertEquals(200, response1.getStatusCode(), "Failed to retrieve user information.");
        assertEquals(200, response.getStatusCode(), "Failed to create a user.");
    }

    private Response getUser(String username) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(USER_URI + "/" + username)
                .get();
    }

    @Test
    public void getUserLogin() {

        String requestBody = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + password + "\",\n" +
                "}";

        Response response =
                RestAssured.given()
                        .baseUri(BASE_URL)
                        .basePath(USER_LOGIN_URI)
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .get();
        assertEquals(200, response.getStatusCode(), "Failed to Login.");
    }

    @Test
    public void createListOfUsers() {
        List<TestList> userList = createUserDataList();

        Response response = createUserList(userList);

        assertEquals(200, response.getStatusCode(), "Failed to create a list of users." + response.getBody());
    }

    private static List<TestList> createUserDataList() {

        List<TestList> userList = new ArrayList<>();
        userList.add(new TestList("user1", "first1", "last1", "user1@example.com", "pass1", "12345678"));
        userList.add(new TestList("user2", "first2", "last2", "user2@example.com", "pass2", "12345678"));
        return userList;
    }

    private Response createUserList(List<TestList> userList) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(CREATE_USER_LIST)
                .contentType(ContentType.JSON)
                .body(userList)
                .post();
    }

    @Test
    public void userLogout() {
        String requestBody = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + password + "\",\n" +
                "}";

        Response response =
                RestAssured.given()
                        .baseUri(BASE_URL)
                        .basePath(USER_LOGIN_URI)
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .get();
        assertEquals(200, response.getStatusCode(), "Failed to Login.");
        Response response1 =
                RestAssured.given()
                        .baseUri(BASE_URL)
                        .basePath(USER_LOGOUT)
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .get();
        assertEquals(200, response1.getStatusCode(), "Failed to Logout User." + response1.getBody());
    }

    @Test
    public void addNewPet() {
        String petName = "Opossumnessis";//Need to change for next tests
        Random random = new Random();
        Integer randId = random.nextInt(9000000);

        String requestBody = "{\n" +
                "  \"id\": " + randId + ",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"" + petName + "\"\n" +
                "  },\n" +
                "  \"name\": \"" + petName + "\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = addNewPet(requestBody);
        Long newPetId = response.jsonPath().getLong("id");
        {
            System.out.println(newPetId);
        }
        Response response2 =
                RestAssured.given()
                        .baseUri(BASE_URL)
                        .basePath(ADD_NEW_PET + "/" + newPetId)
                        .get();
        String petNameAdded = response2.jsonPath().getString("name");

        assertEquals(200, response.getStatusCode(), "Failed to add a new pet." + newPetId);
        assertEquals(petName, petNameAdded, "Added pet name" + petNameAdded);
    }

    private Response addNewPet(String requestBody) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(ADD_NEW_PET)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post();
    }

    @Test
    public void updatePhotoUrl() throws IOException {
        String petName = "Wolveriness";//Need to change for next tests
        String newURL = "https://testurl/cs452sffp"; //New URL
        Random random = new Random();
        Integer randId = random.nextInt(9000000);

        String requestBody = "{\n" +
                "  \"id\": " + randId + ",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"" + petName + "\"\n" +
                "  },\n" +
                "  \"name\": \"" + petName + "\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = addNewPet(requestBody);
        Long newPetId = response.jsonPath().getLong("id");
        {
            System.out.println(newPetId);
        }
        Response response2 =
                RestAssured.given()
                        .baseUri(BASE_URL)
                        .basePath(ADD_NEW_PET + "/" + newPetId)
                        .get();
        String petNameAdded = response2.jsonPath().getString("name");
        String petPhotoUrl = response2.asString();
        System.out.println(petPhotoUrl);

        //middle Assertions to check created Data
        assertEquals(200, response.getStatusCode(), "Failed to add a new pet." + newPetId);
        assertEquals(petName, petNameAdded, "Added pet name" + petNameAdded);

        //Updating PhotoUrl
        String updatedJson = updateUrls(petPhotoUrl, newURL);

        Response response3 = updatePetPut(updatedJson);
        String newPhotoUrl = response3.jsonPath().getString("photoUrls");
        {
            System.out.println(newPhotoUrl);
        }
        assertEquals(200, response3.getStatusCode());

        //Getting updated pet from DB info
        Response response4 =
                RestAssured.given()
                        .baseUri(BASE_URL)
                        .basePath(ADD_NEW_PET + "/" + newPetId)
                        .get();
        String petUpdatedPhotoUrl = response4.jsonPath().getString("photoUrls");
        assertEquals("[" + newURL + "]", petUpdatedPhotoUrl, "Url from DB is same as updated!");
        assertNotEquals(petPhotoUrl, petUpdatedPhotoUrl, "Url is changed from default");
    }

    private Response updatePetPut(String requestBody) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(ADD_NEW_PET)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put();
    }

    private static String updateUrls(String jsonString, String... newUrls) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        List<String> updatedUrls = new ArrayList<>();
        for (String newUrl : newUrls) {
            updatedUrls.add(newUrl);
        }
        ((com.fasterxml.jackson.databind.node.ObjectNode) jsonNode).putPOJO("photoUrls", updatedUrls);
        return objectMapper.writeValueAsString(jsonNode);
    }

    @Test
    public void updateNameStatus() throws IOException {
        String petName = "Cowerss";//Need to change for next tests
        String newPetName = "Flowers"; //New Name
        String newPetStatus = "sold";
        Random random = new Random();
        Integer randId = random.nextInt(9000000);

        String requestBody = "{\n" +
                "  \"id\": " + randId + ",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"" + petName + "\"\n" +
                "  },\n" +
                "  \"name\": \"" + petName + "\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = addNewPet(requestBody);
        Long newPetId = response.jsonPath().getLong("id");
        {
            System.out.println(newPetId);
        }
        Response response2 =
                RestAssured.given()
                        .baseUri(BASE_URL)
                        .basePath(ADD_NEW_PET + "/" + newPetId)
                        .get();
        String petNameAdded = response2.jsonPath().getString("name");
        String petNameStatus = response2.jsonPath().getString("status");
        String petResponseBody = response2.asString();
        System.out.println(petResponseBody);

        //middle Assertions to check created Data
        assertEquals(200, response.getStatusCode(), "Failed to add a new pet." + newPetId);
        assertEquals(petName, petNameAdded, "Added pet name" + petNameAdded);

        //Updating name and status
        String updatedJsonName = updateJson(petResponseBody, "name", newPetName);
        String updatedJsonStatus = updateJson(updatedJsonName, "status", newPetStatus);


        Response response3 = updatePetPut(updatedJsonStatus);
        String updatedStatus = response3.jsonPath().getString("status");
        String updatedName = response3.jsonPath().getString("name");
        {
            System.out.println("New status is: " + updatedStatus + " New Name is: " + updatedName);
        }
        assertEquals(200, response3.getStatusCode());
        assertNotEquals(updatedName, petName, "Name is not Updated!");
        assertNotEquals(updatedStatus, petNameStatus, "Name is not Updated!");

        //Getting updated pet from DB info
        Response response4 =
                RestAssured.given()
                        .baseUri(BASE_URL)
                        .basePath(ADD_NEW_PET + "/" + newPetId)
                        .get();
        String petUpdatedStatus = response4.jsonPath().getString("status");
        String petUpdatedName = response4.jsonPath().getString("name");
        assertEquals(updatedStatus, petUpdatedStatus, "Status from DB is same as updated!");
        assertNotEquals(petNameAdded, petUpdatedName, "Url is changed from default");
    }

    private static String updateJson(String jsonString, String fieldName, String newValue) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        ((com.fasterxml.jackson.databind.node.ObjectNode) jsonNode).putPOJO(fieldName, newValue);
        return objectMapper.writeValueAsString(jsonNode);
    }

    @Test
    public void deletePet() {
        String petName = "Wolveriness";//Need to change for next tests
        String newURL = "https://testurl/cs452sffp"; //New URL
        Random random = new Random();
        Integer randId = random.nextInt(9000000);

        String requestBody = "{\n" +
                "  \"id\": " + randId + ",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"" + petName + "\"\n" +
                "  },\n" +
                "  \"name\": \"" + petName + "\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = addNewPet(requestBody);
        Long newPetId = response.jsonPath().getLong("id");
        {
            System.out.println(newPetId);
        }
        Response deletePet = deletePet(newPetId);
        System.out.println(deletePet.asString());
        assertEquals(200, deletePet.getStatusCode(), "Looks there was something wrong");
    }

    private Response deletePet(Long petId) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(ADD_NEW_PET + "/" + petId)
                .contentType(ContentType.JSON)
                .header("api_key", "api_key")
                .delete();
    }

    @AfterEach
    public void tearDown() {
    }
}

