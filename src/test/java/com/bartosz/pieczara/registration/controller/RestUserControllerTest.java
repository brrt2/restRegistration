package com.bartosz.pieczara.registration.controller;

import static org.junit.Assert.*;

import com.bartosz.pieczara.registration.RegistrationApplication;
import com.bartosz.pieczara.registration.model.User;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestUserControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void shouldRetrieveTheAddedUser_WhenThisEndpointIsAccessed() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/accounts/1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{username:bartoszpieczara,password:letmeinletmein}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void shouldAddUser_WhenLaunched() {

        User user = new User("bartek","January7");

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/accounts"),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/accounts/2"));

    }

}