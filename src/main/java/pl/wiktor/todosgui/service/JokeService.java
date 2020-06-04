package pl.wiktor.todosgui.service;


import pl.wiktor.todosgui.model.ChuckNorrisJoke;

public interface JokeService {
    ChuckNorrisJoke getRandomJoke();
}
