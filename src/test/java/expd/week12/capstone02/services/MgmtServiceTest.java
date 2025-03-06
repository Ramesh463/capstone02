package expd.week12.capstone02.services;

import expd.week12.capstone02.DAO.MusicDAO;
import expd.week12.capstone02.DAO.inMemory.ArtistDAO;
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
@Tag("Unit")
class MgmtServiceTest {
    private Artist artist1;
    private Track track1;

    @InjectMocks
    private MgmtService mgmtService;

    @Mock
    private MusicDAO<Artist> artistDAO;

    @Mock
    private MusicDAO<Track> trackDAO;

    @BeforeEach
    void setUp() {
       artist1 = Artist.builder()
               .id(1L)
               .artistName("Artist 1")
               .country("Country 1")
               .genre(Genre.POP)
               .build();
                artist1.setId(1L);
        track1 = Track.builder()
                .id(1L)
                .trackName("Track 1")
                .genre(Genre.POP)
                .movieName("Movie 1")
                .build();
        track1.setId(1L);
    }

    @Test
    void testAddArtistToTrack() {




    }

    @Test
    void getArtistByTrackID() {
    }

    @Test
    void removeArtistFromTrack() {
    }
}