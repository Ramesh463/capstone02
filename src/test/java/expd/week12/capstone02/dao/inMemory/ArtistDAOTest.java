package expd.week12.capstone02.dao.inMemory;

import expd.week12.capstone02.DAO.inMemory.ArtistDAO;
import expd.week12.capstone02.Domain.Artist;
import expd.week12.capstone02.Domain.Genre;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Unit")
class ArtistDAOTest {
    private Artist artist1;
    private Artist artist2;
    private ArtistDAO dao = new ArtistDAO();

    @BeforeEach
    void setUp() {
        dao = new ArtistDAO();
        dao.resetDataStore();

        artist1 = createTestArtist("Ramesh","ROCK","IND");
        artist2 = createTestArtist("J","BLUES","PO");

        artist1 = dao.save(artist1);
        artist2 = dao.save(artist2);

    }

    private Artist createTestArtist(String name,  String genre, String country){
      return Artist.builder()
              .artistName(name)
              .genre(Genre.valueOf(genre))
              .country(country)
              .build();
    }

    @Test
    public void testFindAll() {
        List<Artist> artists = dao.findAll();

        // Assert
        assertAll(
                ()->assertNotNull(artists),
                ()->assertEquals(2,artists.size()),
                ()->assertEquals(0,artists.stream().filter(artist -> artist.getId()==0).count()),
                () -> assertEquals(artist1, artists.getFirst())
        );
    }

    @Test
    public void testFindById() {

        Optional<Artist> artist = dao.findById(artist1.getId());

        assertTrue(artist.isPresent());
        assertEquals(artist1,artist.get());
    }

    @Test
    public void testGetById(){
        Artist artist = dao.getById(artist1.getId());
        assertNotNull(artist);
        assertEquals(artist1,artist);
    }

    @Test
    public void testUpdateArtist() {
        Artist artisttoupdate = Artist.builder()
                .artistName("Ramesh1")
                .genre(Genre.POP)
                .country("IND1")
                .build();
        artisttoupdate.setId(artist1.getId());

        boolean updated = dao.update(artisttoupdate);
        Optional<Artist> verifyArtist = dao.findById(artist1.getId());
        assertTrue(verifyArtist.isPresent());
        assertTrue(updated);
        assertNotEquals(artist1.getArtistName(), verifyArtist.get().getArtistName());
        assertNotEquals(artist1.getGenre(), verifyArtist.get().getGenre());
        assertNotEquals(artist1.getCountry(), verifyArtist.get().getCountry());
        assertEquals(artisttoupdate, verifyArtist.get());
    }

    @Test
    public void testDeleteArtist() {
       boolean deleted = dao.delete(artist1);
       List<Artist> artists = dao.findAll();

       assertAll(
               () ->assertTrue(deleted),
               ()->assertNotNull(artists),
               ()->assertEquals(1,artists.size()),
               () -> assertEquals(artist2, artists.getFirst())
       );
    }

    @Test
    public void testAttemptToUpdateNonExistentArtist() {
        Artist nonExistArtist = Artist.builder().build();
        nonExistArtist.setId(5L);

        boolean updated= dao.update(nonExistArtist);

        assertFalse(updated);
    }

    @Test
    public void testAttemptToDeleteNonExistingArtist() {
        Artist nonExistArtist = Artist.builder().build();
        nonExistArtist.setId(5L);

        boolean deleted = dao.delete(nonExistArtist);

        assertFalse(deleted);
    }


}