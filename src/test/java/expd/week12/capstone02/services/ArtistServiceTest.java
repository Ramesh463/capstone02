package expd.week12.capstone02.services;

import expd.week12.capstone02.DAO.MusicDAO;
import expd.week12.capstone02.DAO.inMemory.ArtistDAO;
import expd.week12.capstone02.Domain.Artist;
import expd.week12.capstone02.Domain.Genre;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
@Tag("Unit")
class ArtistServiceTest {
    private Artist artist1;

    @InjectMocks
    private ArtistService artistService;

    @Mock
    private MusicDAO<Artist> artistDAO;

    @BeforeEach
    public void setUp() {
        artist1 = Artist.builder()
                .artistName("Ramesh")
                .genre(Genre.valueOf("POP"))
                .country("IND")
                .build();
        artist1.setId(1L);
    }

    @Test
    public void testFindAllArtists() {
        Mockito.when(artistDAO.findAll()).thenReturn(List.of(artist1));

        List<Artist> results = artistService.getAllArtists();

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(1, results.size()),
                () -> assertEquals(artist1, results.getFirst())

        );

        Mockito.verify(artistDAO, Mockito.times(1)).findAll();
    }

    @Test
    public void testCreateArtist() {
        Mockito.when(artistDAO.save(artist1)).thenReturn(artist1);
        Mockito.when(artistDAO.findById(1L)).thenReturn(Optional.of(artist1));

        Artist createdArtist = artistService.createArtist(artist1);

        Artist result = artistService.getArtistById(createdArtist.getId());
        assertEquals(artist1, result);

        Mockito.verify(artistDAO, Mockito.times(1)).save(artist1);
        Mockito.verify(artistDAO, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testUpdateArtist() {
        Mockito.when(artistDAO.findById(1L)).thenReturn(Optional.of(artist1));
        Mockito.when(artistDAO.update(artist1)).thenReturn(true);

        boolean result = artistService.updateArtist(artist1);

        assertTrue(result);

        Mockito.verify(artistDAO, Mockito.times(1)).findById(1L);
        Mockito.verify(artistDAO, Mockito.times(1)).update(artist1);
    }

    @Test
    public void testUpdateNonExistentArtist() {
        artist1.setId(2L);
        Mockito.when(artistDAO.findById(2L)).thenReturn(Optional.empty());


        boolean result = artistService.updateArtist(artist1);

        assertFalse(result);


        Mockito.verify(artistDAO).findById(2L);
        Mockito.verify(artistDAO, never()).update(any());
    }

    @Test
    public void testDeleteArtist() {
        Mockito.when(artistDAO.findById(1L)).thenReturn(Optional.ofNullable(artist1));
        Mockito.when(artistDAO.delete(artist1)).thenReturn(true);

        boolean result = artistService.deleteArtist(1L);

        assertTrue(result);

        Mockito.verify(artistDAO, Mockito.times(1)).findById(1L);
        Mockito.verify(artistDAO, Mockito.times(1)).delete(artist1);
    }

    @Test
    public void testDeleteNonExistentArtist() {
        Mockito.when(artistDAO.findById(2L)).thenReturn(Optional.empty());

        boolean result = artistService.deleteArtist(2L);

        assertFalse(result);

        Mockito.verify(artistDAO).findById(2L);
        Mockito.verify(artistDAO, never()).delete(any());
    }

    @Test
    public void testFindById() {
        Mockito.when(artistDAO.findById(1L)).thenReturn(Optional.of(artist1));

        Artist results = artistService.getArtistById(1L);
        assertEquals(artist1, results);

        Mockito.verify(artistDAO, Mockito.times(1)).findById(1L);

    }

}