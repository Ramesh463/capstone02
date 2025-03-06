package expd.week12.capstone02.services;

import expd.week12.capstone02.DAO.MusicDAO;
import expd.week12.capstone02.DAO.inMemory.TrackDAO;
import expd.week12.capstone02.Domain.Artist;
import expd.week12.capstone02.Domain.Genre;
import expd.week12.capstone02.Domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class TrackServiceTest {

    private  Track track1;

    @InjectMocks
    private TrackService trackService;

    @Mock
    private MusicDAO<Track> trackDAO;

    @BeforeEach
    public void setUp() {
       track1 = Track.builder()
               .id(1L)
               .trackName("Track 1")
               .genre(Genre.POP)
               .movieName("Movie 1")
               .build();
    }

    @Test
    public void testCreateTrack() {
        Mockito.when(trackDAO.save(track1)).thenReturn(track1);
        Mockito.when(trackDAO.findById(1L)).thenReturn(Optional.ofNullable(track1));

        Track createTrack = trackService.createTrack(track1);

        Track result = trackService.getTrackById(createTrack.getId());

        assertEquals(track1, result);


        Mockito.verify(trackDAO, Mockito.times(1)).save(track1);
        Mockito.verify(trackDAO, Mockito.times(1)).findById(1L);


    }

    @Test
    void testDeleteTrack() {
        //Arrange
        Mockito.when(trackDAO.findById(1L)).thenReturn(Optional.ofNullable(track1));
        Mockito.when(trackDAO.delete(track1)).thenReturn(true);

        //Act
        boolean result = trackService.deleteTrack(1L);

        //Assert
        assertTrue(result);
        Mockito.verify(trackDAO, Mockito.times(1)).findById(1L);
        Mockito.verify(trackDAO, Mockito.times(1)).delete(track1);
    }

    @Test
    void testUpdateTrack() {
        //Arrange
        Mockito.when(trackDAO.findById(1L)).thenReturn(Optional.ofNullable(track1));
        Mockito.when(trackDAO.update(track1)).thenReturn(true);

        //Act
        boolean result = trackService.updateTrack(track1);

        //Assert
        assertTrue(result);
        Mockito.verify(trackDAO, Mockito.atMostOnce()).findById(1L);
        Mockito.verify(trackDAO, Mockito.atMostOnce()).update(track1);
    }

    @Test
    void testGetTrackById() {
        Mockito.when(trackDAO.findById(1L)).thenReturn(Optional.of(track1));

        Track results = trackService.getTrackById(1L);
        assertEquals(track1, results);

        Mockito.verify(trackDAO, Mockito.times(1)).findById(1L);
    }

    @Test
    void testGetAllTracks() {
        //Arrange
        Mockito.when(trackDAO.findAll()).thenReturn(List.of(track1));

        //Act
        List<Track> results = trackService.getAllTracks();

        //Assert
        assertAll(
                ()->assertNotNull(results),
                ()->assertEquals(1,results.size()),
                ()->assertEquals(track1,results.getFirst())

        );
    }
}