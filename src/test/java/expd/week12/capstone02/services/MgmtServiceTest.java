package expd.week12.capstone02.services;

import expd.week12.capstone02.DAO.inMemory.ArtistDAO;
import expd.week12.capstone02.DAO.inMemory.TrackDAO;
import expd.week12.capstone02.Domain.Artist;
import expd.week12.capstone02.Domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class MgmtServiceTest {

    private Artist artist;
    private Track track;

    @InjectMocks
    private MgmtService mgmtService;

    @Mock
    private TrackDAO trackDAO;
    private ArtistDAO artistDAO;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void addArtistToTrack() {
    }

    @Test
    public void getArtistByTrackID() {
    }

    @Test
    public void removeArtistFromTrack() {
    }
}