package com.example.spring2023;
import com.example.spring2023.Application.Services.FilmService;
import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.DAL.repositories.IFilmActorRepository;
import com.example.spring2023.DAL.repositories.IFilmRepository;
import com.example.spring2023.Domain.DTO.RequestDTO.FilmRequestDTO;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.models.UserPreferences;
import com.example.spring2023.Domain.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class FilmServiceTests {

    @InjectMocks
    private FilmService filmService;

    @Mock
    private IFilmRepository filmRepository;

    @Mock
    private IActorRepository actorRepository;

    @Mock
    private IActorService actorService;

    @Mock
    private IFilmActorRepository filmActorRepository;

    @Mock
    private IUserService userService;

    @Mock
    private IUserPreferencesService userPreferencesService;

    @Mock
    private IMailSender mailSender;

    @Test
    public void createFilm() {
        var film = new FilmRequestDTO( null, "testFilm", "testGenre", 2004, null);
        this.filmService.createOrUpdateFilm(film);
        Mockito.verify(filmRepository).saveFilm("testFilm", "testGenre", 2004);
    }

    @Test
    public void updateFilm() {
        var filmRequest = new FilmRequestDTO(1L, "testFilm", "testGenre", 2004, List.of(1L));
        var existingFilm = new Film(1L, "testFilm", "testGenre", 2004, null);
        Mockito.when(filmRepository.update(filmRequest.getId(), filmRequest.getName(), filmRequest.getGenre(), filmRequest.getReleaseYear()))
                .thenReturn(existingFilm);

        this.filmService.createOrUpdateFilm(filmRequest);
        Mockito.verify(filmRepository).update(1L, "testFilm", "testGenre", 2004);
        Mockito.verify(filmActorRepository).insertData(1L, 1L);
    }

    @Test
    public void updateFilmWithMailSender() {
        var filmRequest = new FilmRequestDTO(null,"testFilm", "testGenre", 2004, List.of(1L));
        var newFilm = new Film(1L, "testFilm", "testGenre", 2004, null);
        var user = new User(1L, "testLog", "testPass", "testMail@mail.ru", "testName",
                "testSurname", "testPatronymic");
        var actor = new Actor(1L, "testName", "testSurname", "testPatronimyc", 19);
        var preferences = new UserPreferences(List.of(), filmRequest.getActorsID());

        Mockito.when(filmRepository.saveFilm(filmRequest.getName(), filmRequest.getGenre(), filmRequest.getReleaseYear()))
                .thenReturn(newFilm);
        Mockito.when(userService.getUsers()).thenReturn(List.of(user));
        Mockito.when(userPreferencesService.getUserPreferences(user.getId())).thenReturn(Optional.of(preferences));
        Mockito.when(actorService.getActorByID(filmRequest.getActorsID().get(0))).thenReturn(actor);

        this.filmService.createOrUpdateFilm(filmRequest);
        Mockito.verify(mailSender).send(user.getEmail(),
               "New Film with beloved actors",
               "A new film \"testFilm\" was released starring testSurname testName testPatronimyc");
    }

    @Test
    public void getFilms() {
        filmRepository.findWithFilters(null, null, null, null, null);
        Mockito.verify(filmRepository).findWithFilters(null, null, null, null, null);
    }

    @Test
    public void getFilmByID() {
        var filmID = 1L;
        List<Long> actorsList = List.of();
        var film = new Film(1L, "testName", "testGenre", 2004, actorsList);
        Mockito.when(filmRepository.findById(filmID)).thenReturn(Optional.of(film));

        this.filmService.getFilmByID(filmID);
        Mockito.verify(filmRepository).findById(filmID);
        Mockito.verify(filmActorRepository).getAllFilmActors(filmID);
    }

    @Test
    public void deleteByID() {
        var filmID = 1L;
        this.filmService.deleteFilm(filmID);

        Mockito.verify(filmRepository).deleteById(filmID);
        Mockito.verify(filmActorRepository).deleteFilmActors(filmID);
    }

}
