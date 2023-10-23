package com.example.spring2023.Application.Services;

import com.example.spring2023.Domain.DTO.RequestDTO.FilmRequestDTO;
import com.example.spring2023.Application.Mappers.Response.FilmResponseDTOMapper;
import com.example.spring2023.Domain.mappers.Request.IFilmRequestDTOMapper;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.DAL.repositories.IFilmActorRepository;
import com.example.spring2023.DAL.repositories.IFilmRepository;
import com.example.spring2023.Domain.services.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class FilmService implements IFilmService {

    private final IFilmRepository filmRepository;
    private final IActorRepository actorRepository;
    private final IFilmActorRepository filmActorRepository;
    private final IFilmRequestDTOMapper filmRequestDTOMapper;

    private final FilmResponseDTOMapper filmResponseDTOMapper;
    @Autowired
    public FilmService(
            IFilmRepository filmRepository,
            IActorRepository actorRepository,
            IFilmActorRepository filmActorRepository,
            IFilmRequestDTOMapper filmRequestDTOMapper,
            FilmResponseDTOMapper filmResponseDTOMapper) {
        this.filmRepository = filmRepository;
        this.actorRepository = actorRepository;
        this.filmActorRepository = filmActorRepository;
        this.filmRequestDTOMapper = filmRequestDTOMapper;
        this.filmResponseDTOMapper = filmResponseDTOMapper;
    }

    public SimpleEntry<Film, List<Actor>> createOrUpdateFilm(FilmRequestDTO film) {
        var mappedFilm = this.filmRequestDTOMapper.apply(film);
        Film updatedOrCreatedFilm;

        if (film.getId() != null)
            updatedOrCreatedFilm = this.filmRepository.update(film.getId(), film.getName(), film.getReleaseYear());
        else
            updatedOrCreatedFilm = this.filmRepository.saveFilm(film.getName(), film.getReleaseYear());

        if (film.getActorsID() != null && !film.getActorsID().isEmpty()) {
            film.getActorsID()
                    .forEach(actorID -> this.filmActorRepository.insertData(actorID, updatedOrCreatedFilm.getId()));
            updatedOrCreatedFilm.setActorsID(film.getActorsID());
        }
        return new SimpleEntry<>(updatedOrCreatedFilm, this.actorRepository.findAll());
    }
    public SimpleEntry<List<Film>, List<Actor>> getFilms(@Nullable String name) {
        var queryResult = name != null ? this.filmRepository.findBySubstring(name) : this.filmRepository.findAll();
        var films = new ArrayList<>(queryResult);
        films.forEach(film -> {
             var filmActors = this.filmActorRepository.getAllFilmActors(film.getId());
             film.setActorsID(filmActors);
        });
        return new SimpleEntry<>(films, this.actorRepository.findAll());
    }

    public SimpleEntry<Film, List<Actor>> getFilmByID(Long id) {
        var film = this.filmRepository.findById(id).orElse(null);
        if (film == null) {return null;}
            var filmActors = this.filmActorRepository.getAllFilmActors(film.getId());
            film.setActorsID(filmActors);
        return new SimpleEntry<>(film, this.actorRepository.findAll());
    }

    public void deleteFilm(Long id) {
        this.filmRepository.deleteById(id);
        this.filmActorRepository.getAllFilmActors(id);
    }
}
