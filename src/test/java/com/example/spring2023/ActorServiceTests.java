package com.example.spring2023;
import com.example.spring2023.Application.Services.ActorService;
import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.Domain.DTO.RequestDTO.ActorFiltersRequestDTO;
import com.example.spring2023.Domain.DTO.RequestDTO.ActorRequestDTO;
import com.example.spring2023.Domain.mappers.Request.IActorRequestDTOMapper;
import com.example.spring2023.Domain.models.Actor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ActorServiceTests {

    @InjectMocks
    private ActorService actorService;

    @Mock
    private IActorRepository actorRepository;
    @Mock
    private IActorRequestDTOMapper actorRequestDTOMapper;

    @Test()
    public void createActor() {
        var actorRequest = new ActorRequestDTO(null, "testName", "testSurname", "testPatronymic", 19);
        var mappedActor = new Actor(null, actorRequest.getName(), actorRequest.getSurname(), actorRequest.getPatronic(), actorRequest.getAge());

        Mockito.when(this.actorRequestDTOMapper.apply(actorRequest)).thenReturn(mappedActor);
        this.actorService.createOrUpdateActor(actorRequest);
        Mockito.verify(actorRepository).save(mappedActor);
    }

    @Test()
    public void updateActor() {
        var actorRequest = new ActorRequestDTO(1L, "testName", "testSurname", "testPatronymic", 19);
        var mappedActor = new Actor(1L, actorRequest.getName(), actorRequest.getSurname(), actorRequest.getPatronic(), actorRequest.getAge());

        Mockito.when(this.actorRequestDTOMapper.apply(actorRequest)).thenReturn(mappedActor);
        this.actorService.createOrUpdateActor(actorRequest);
        Mockito.verify(actorRepository).update(mappedActor);
    }

    @Test()
    public void getActors() {
        var filters = new ActorFiltersRequestDTO(null, null, null, null);

        this.actorService.getActors(filters, Optional.empty());
        Mockito.verify(this.actorRepository).findWithFilters(filters.getSearchStr(), filters.getAge(), filters.getSkip(), filters.getTake());
    }
    @Test()
    public void getActorByID() {
        var actorID = 1L;

        this.actorService.getActorByID(actorID);
        Mockito.verify(this.actorRepository).findById(actorID);

    }
    @Test()
    public void deleteActor() {
        var actorID = 1L;

        this.actorService.deleteActor(actorID);
        Mockito.verify(this.actorRepository).deleteById(actorID);
    }

}
