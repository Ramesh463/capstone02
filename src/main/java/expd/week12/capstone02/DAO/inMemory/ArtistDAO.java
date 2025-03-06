package expd.week12.capstone02.DAO.inMemory;

import expd.week12.capstone02.DAO.MusicDAO;
import expd.week12.capstone02.Domain.Artist;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

@Repository
public class ArtistDAO implements MusicDAO<Artist> {
        private Map<Long, Artist> artists = new ConcurrentHashMap<>();
        private AtomicLong  nextId = new AtomicLong(0);

    @Override
    public boolean update(Artist artist) {
        return artists.computeIfPresent(artist.getId(),(key, oldValue)->artist) !=null;
    }

    @Override
    public boolean delete(Artist deleteObject) {
        return artists.remove(deleteObject.getId()) !=null;
    }



    @Override
    public Artist save(Artist artist) {
       Long id = nextId.incrementAndGet();
       artist.setId(id);
       artists.put(id, artist);
       return artist;
    }

    @Override
    public Optional<Artist> findById(Long id) {
        return Optional.ofNullable(artists.get(id));
    }

    @Override
    public Artist getById(Long id) {
        return artists.get(id);
    }

    @Override
    public List<Artist> findAll() {
        return new ArrayList<>(artists.values());
    }

    @Override
    public void resetDataStore() {
        artists = new ConcurrentHashMap<>();
        nextId = new AtomicLong(1);
    }
}
