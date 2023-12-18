package com.example.spring2023;

import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.Domain.models.Actor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ActorRepositoryTests {


    @Mock
    private IActorRepository actorRepository;

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
        var dbSearchResult = this.actorRepository.findAll();
        assertEquals(5, dbSearchResult.size());
    }

}
