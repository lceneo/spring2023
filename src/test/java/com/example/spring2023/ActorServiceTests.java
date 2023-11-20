package com.example.spring2023;

import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.services.IActorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActorServiceTests {

    @MockBean
    private IActorRepository actorRepository;

    @Autowired
    private IActorService actorService;

    private final List<Actor> actors = List.of(
            new Actor(1L, "Nikita", "Osminin", "Borisovich", 19),
            new Actor(2L, "Ekaterina", "Safonova", "Sergeevna", 20),
            new Actor(3L, "Antoniy", "Yaskov", "Demodovich", 21),
            new Actor(4L, "Nikita", "Karpinskiy", "Anatolievich", 12),
            new Actor(5L, "Boris", "Lakin", "Dmitrevich", 32)
    );

    @Before
    public void initRepository() {
        Mockito.when(actorRepository.findAll()).thenReturn(actors);
    }

    @Test
    public void checkActorsSearchWithoutParams() {
        var searchResult = this.actorService.getActors(null, null);
        assertEquals(searchResult.size(), 5);
    }

    @Test
    public void checkActorsSearchWithAgeSpecified() {
        var searchResult = this.actorService.getActors(null, 19);
        var foundActor = this.actors.get(0);
        assertEquals("actors number of age 19 don't match", searchResult.size(), 1);
        assertEquals(searchResult.get(0), foundActor);
    }

    @Test
    public void checkActorsSearchWithNameSpecified() {
        var searchResult = this.actorService.getActors("Nikita", null).toArray();
        var foundActors = this.actors.stream().filter(actor -> actor.getFullName().contains("Nikita")).toArray();
        assertEquals("actors number with name Nikita don't match", searchResult.length, 2);
        assertArrayEquals(searchResult, foundActors);
    }

    @Test
    public void checkActorsSearchWithNameAndAgeSpecified() {
        var searchResult = this.actorService.getActors("Nikita", 12).toArray();
        var foundActors = this.actors.stream().filter(actor -> actor.getFullName().contains("Nikita") && actor.getAge() == 12).toArray();
        assertEquals("actors number with name Nikita and age 12 don't match", searchResult.length, 1);
        assertArrayEquals(searchResult, foundActors);
    }

}
