package com.example.spring2023;
import com.example.spring2023.DAL.repositories.IFilmRepository;
import com.example.spring2023.Domain.models.Film;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FilmRepositoryTests {

    @Mock
    private IFilmRepository filmRepository;

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
        var dbSearchResult = this.filmRepository.findAll();
        assertEquals(5, dbSearchResult.size());
    }

}
