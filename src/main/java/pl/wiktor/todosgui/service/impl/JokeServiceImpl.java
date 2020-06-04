package pl.wiktor.todosgui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.wiktor.todosgui.model.ChuckNorrisJoke;
import pl.wiktor.todosgui.service.ExceptionHandler;
import pl.wiktor.todosgui.service.JokeService;

import java.util.Collections;

@Slf4j
@Service
public class JokeServiceImpl implements JokeService {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${rapidapi.key}")
    public String API_KEY;

    @Value("${rapidapi.host}")
    public String API_HOST;

    @Value("${rapidapi.randomJokeURL}")
    public String API_URL;

    @Override
    public ChuckNorrisJoke getRandomJoke() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("x-rapidapi-host", API_HOST);
            headers.add("x-rapidapi-key", API_KEY);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);

            ResponseEntity<?> tasksResponse = restTemplate.exchange(API_URL, HttpMethod.GET, entity, ChuckNorrisJoke.class);
//            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(API_URL, ChuckNorrisJoke.class);
            log.info("[getRandomJoke]: Trying to fetch data (random-joke)");
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                ChuckNorrisJoke fetched = (ChuckNorrisJoke) tasksResponse.getBody();
                log.info("[getRandomJoke]: Fetching success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[getRandomJoke]: Fetching failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
            ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[getRandomJoke]: Fetching failed. Caused by: " + e.getMessage());
            ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getMessage());
        }
        return null;
    }
}
