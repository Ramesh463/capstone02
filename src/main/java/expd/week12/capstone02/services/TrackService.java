package expd.week12.capstone02.services;

import expd.week12.capstone02.DAO.MusicDAO;
import expd.week12.capstone02.Domain.Genre;
import expd.week12.capstone02.Domain.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final MusicDAO<Track> trackDAO;


    public Track createTrack(Track track) {
        return trackDAO.save(track);
    }

    public boolean deleteTrack(Long id){
        Optional<Track> track = trackDAO.findById(id);
        return track.filter(trackDAO::delete).isPresent();
    }

    public boolean updateTrack(Track track) {
        Optional<Track> existingTrack = trackDAO.findById(track.getId());
        return existingTrack.isPresent() && existingTrack
                    .map(c -> {
                    c.setTrackName(track.getTrackName());
                    c.setGenre(track.getGenre());
                    c.setMovieName(track.getMovieName());
                    return trackDAO.update(c);
                })
                .orElse(false);
    }

    public Track getTrackById(Long id){

        return trackDAO.findById(id).orElse(null);
    }

    public List<Track> getAllTracks() {
        return trackDAO.findAll();
    }

    public List<Track> getTracksByGenre(Genre genre) {
        return trackDAO.findBy(c -> c.getGenre().equals(genre));
    }


}
