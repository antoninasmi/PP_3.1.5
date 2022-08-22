package com.kata.rest;

import com.kata.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class Communication {

    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";
    private String sessionId;
    private String code = "";

    @Autowired
    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {});
        List<User> allUsers = responseEntity.getBody();
        sessionId = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        return allUsers;
    }

    public void saveUser(User user) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, getHttpEntity(user), String.class);
        code += responseEntity.getBody();
    }

    public void updateUser(User user) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, getHttpEntity(user), String.class);
        code += responseEntity.getBody();
    }

    public void deleteUser(User user) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + user.getId(), HttpMethod.DELETE,
                getHttpEntity(user), String.class);
        code += responseEntity.getBody();
        System.out.println("The code is: " + code);
    }

    public HttpEntity<Object> getHttpEntity(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Cookie", sessionId.substring(0, 43));
        return new HttpEntity<>(user, headers);
    }
}
