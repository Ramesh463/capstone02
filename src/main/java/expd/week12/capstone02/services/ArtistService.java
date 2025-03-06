package expd.week12.capstone02.services;

import expd.week12.capstone02.DAO.MusicDAO;
import expd.week12.capstone02.Domain.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final MusicDAO<Artist> ArtistDAO;
    public static final String INVALID_INPUT_MESSAGE = "Invalid input provided for widget operation";

    public Artist createArtist(Artist artist) {
        if (artist == null || artist.getArtistName() == null || artist.getArtistName().isEmpty() || artist.getArtistName().length() > 255 || artist.getArtistName().length() < 3 ) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE);
        }
        return ArtistDAO.save(artist);
    }


    public boolean deleteArtist(Long id) {
        Optional<Artist> artist = ArtistDAO.findById(id);
        return artist.filter(ArtistDAO::delete).isPresent();
    }

    public boolean updateArtist(Artist artist) {
        Optional<Artist> existingArtist = ArtistDAO.findById(artist.getId());
        return existingArtist.isPresent() && existingArtist
                .map(c->{
                    c.setArtistName(artist.getArtistName());
                    c.setGenre(artist.getGenre());
                    c.setCountry(artist.getCountry());
                    return ArtistDAO.update(c);
                })
                .orElse(false);
    }

    public Artist getArtistById(Long id) {
        return ArtistDAO.findById(id).orElse(null);
    }

    public List<Artist> getAllArtists() {
        return ArtistDAO.findAll();
    }

}
