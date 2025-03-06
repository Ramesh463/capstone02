package expd.week12.capstone02.services;

import expd.week12.capstone02.DAO.MusicDAO;
import expd.week12.capstone02.DAO.inMemory.ArtistDAO;
import expd.week12.capstone02.DAO.inMemory.TrackDAO;
import expd.week12.capstone02.Domain.Artist;
import expd.week12.capstone02.Domain.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MgmtService {
    private final MusicDAO<Artist> artistDAO;
    private final MusicDAO<Track> trackDAO;

    public Track addArtistToTrack(Long artistID, Long trackID) throws Exception {
        Track track = trackDAO.getById(trackID);
        Artist artist =artistDAO.getById(artistID);
        if (artist == null || track == null) {
            throw new Exception("Artist or track does not exist");
        }
        track.addArtist(artist);
        return track;
    }

    public List<Artist> getArtistByTrackID(Long trackID) {
        Track track = trackDAO.getById(trackID);
        if (track == null) {
            return null;
        }
        return track.getArtists();
    }

    public Track removeArtistFromTrack(Long artistID, Long trackID) {
        Track track = trackDAO.getById(trackID);
        Artist artist = artistDAO.getById(artistID);
        if (artist == null || track == null) {
            return null;
        }
        track.removeArtist(artist);
        return track;
    }
}
