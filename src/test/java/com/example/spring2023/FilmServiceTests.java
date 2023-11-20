package com.example.spring2023;

import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.DAL.repositories.IFilmRepository;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import com.example.spring2023.Domain.services.IActorService;
import com.example.spring2023.Domain.services.IFilmService;
import jakarta.validation.constraints.Max;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmServiceTests {

    @MockBean
    private IFilmRepository filmRepository;

    @Autowired
    private IFilmService filmService;

    private final List<Film> films = List.of(
            new Film(1L, "Terrifier", "Horror", 2012, null),
            new Film(2L, "Mog", "Thriller", 2007, null),
            new Film(3L, "American Psycho", "Thriller", 2000, new ArrayList<>(List.of(1L,2L))),
            new Film(4L, "Alive", "Drama", 1997, null),
            new Film(5L, "Terrifier 2", "Horror", 1666, null)
    );

    @Before
    public void initRepository() {
        Mockito.when(filmRepository.findAll()).thenReturn(films);
    }

    @Test
    public void checkFilmsSearchWithoutParams() {
        var searchResult = this.filmService.getFilms(null, null, null);
        assertEquals(searchResult.getKey().size(), 5);
    }

    @Test
    public void checkActorsSearchWithNameSpecified() {
        var searchResult = this.filmService.getFilms("Terrifier", null, null);
        var foundActors = this.films.stream().filter(film -> film.getName().toLowerCase().contains("terrifier")).toArray();
        assertEquals("films with name Terrifier don't match", searchResult.getKey().size(), 2);
        assertArrayEquals(searchResult.getKey().toArray(), foundActors);
    }

    @Test
    public void checkActorsSearchWithGenreSpecified() {
        var searchResult = this.filmService.getFilms(null, "drama", null);
        var foundFilm = this.films.stream().filter(film -> film.getGenre().toLowerCase().contains("drama")).toArray()[0];
        assertEquals("films with genre drama don't match", searchResult.getKey().size(), 1);
        assertEquals(foundFilm, this.films.get(3));
    }

    @Test
    public void checkActorsSearchWithReleaseYearSpecified() {
        var searchResult = this.filmService.getFilms(null, null, 2000);
        var foundFilm = this.films.stream().filter(film -> film.getReleaseYear() == 2000).toArray()[0];
        assertEquals("films with genre drama don't match", searchResult.getKey().size(), 1);
        assertEquals(foundFilm, this.films.get(2));
    }

    @Test
    public void checkActorsSearchWithAllParamsSpecified() {
        var searchResult = this.filmService.getFilms("American Psycho", "Thriller", 2000);
        var foundFilm = this.films.stream().filter(film ->
                film.getName().toLowerCase().contains("american psycho") &&
                film.getReleaseYear() == 2000
                && film.getGenre().toLowerCase().equals("thriller")
        ).toArray()[0];
        assertEquals("films with genre drama don't match", searchResult.getKey().size(), 1);
        assertEquals(foundFilm, this.films.get(2));
    }
}
