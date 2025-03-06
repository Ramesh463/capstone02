package expd.week12.capstone02.dao.inMemory;


import expd.week12.capstone02.DAO.inMemory.TrackDAO;

import expd.week12.capstone02.Domain.Genre;
import expd.week12.capstone02.Domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UNIT")
public class TrackDAOTest {

    private Track track1;
    private Track track2;
    private TrackDAO dao = new TrackDAO();

    @BeforeEach
    void setUp() {
        dao = new TrackDAO();
        dao.resetDataStore();

        track1 = createTestTrack("Ramesh","XXX","ROCK");
        track2 = createTestTrack("Jam","BLUES","POP");

        track1 = dao.save(track1);
        track2 = dao.save(track2);


        }

    private Track createTestTrack(String trackName, String movieName, String genre ) {
        return Track.builder()
                .trackName(trackName)
                .movieName(movieName)
                .genre(Genre.POP)
                .build();

    }

    @Test
    public void testFindAll() {
        List<Track> tracks = dao.findAll();

        // Assert
        assertAll(
                ()->assertNotNull(tracks),
                ()->assertEquals(2,tracks.size()),
                ()->assertEquals(0,tracks.stream().filter(Track -> Track.getId()==0).count()),
                () -> assertEquals(track1, tracks.getFirst())
        );
    }

    @Test
    public void testFindById() {

        Optional<Track> Track = dao.findById(track1.getId());

        assertTrue(Track.isPresent());
        assertEquals(track1,Track.get());
    }

    @Test
    public void testGetById(){
        Track Track = dao.getById(track1.getId());
        assertNotNull(Track);
        assertEquals(track1,Track);

    }

    @Test
    public void testUpdateTrack() {
        Track trackToUpdate = Track.builder()
                .trackName("Ramesh1")
                .movieName("ROCK2")
                .genre(Genre.ROCK)
                .build();
        trackToUpdate.setId(track1.getId());
        boolean success = dao.update(trackToUpdate);
        Optional<Track> verifyTrack = dao.findById(trackToUpdate.getId());
        assertTrue(success);
        assertNotEquals(track1.getTrackName(), verifyTrack.get().getTrackName());
        assertNotEquals(track1.getGenre(), verifyTrack.get().getGenre());
        assertNotEquals(track1.getMovieName(), verifyTrack.get().getMovieName());
        assertEquals(trackToUpdate, verifyTrack.get());
    }

    @Test
    public void testDeleteTrack() {
        boolean deleted = dao.delete(track1);
        List<Track> Tracks = dao.findAll();

        assertAll(
                () ->assertTrue(deleted),
                ()->assertNotNull(Tracks),
                ()->assertEquals(1,Tracks.size()),
                () -> assertEquals(track2, Tracks.getFirst())
        );

    }

    @Test
    public void testAttemptToUpdateNonExistentTrack() {
        Track nonExistTrack = Track.builder().build();
        nonExistTrack.setId(5L);

        boolean updated= dao.update(nonExistTrack);

        assertFalse(updated);

    }

    @Test
    public void testAttemptToDeleteNonExistingTrack(){
        Track nonExistTrack = Track.builder().build();
        nonExistTrack.setId(5L);

        boolean deleted = dao.delete(nonExistTrack);

        assertFalse(deleted);
    }


}
