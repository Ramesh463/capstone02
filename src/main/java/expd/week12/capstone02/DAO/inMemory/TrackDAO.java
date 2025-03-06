package expd.week12.capstone02.DAO.inMemory;

import expd.week12.capstone02.DAO.MusicDAO;
import expd.week12.capstone02.Domain.Artist;
import expd.week12.capstone02.Domain.Track;
import org.springframework.stereotype.Repository;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TrackDAO implements MusicDAO<Track> {

    private Map<Long, Track> tracks = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    @Override
    public boolean update(Track track) {
        return    tracks.computeIfPresent(track.getId(),(key, oldValue)->track) !=null;
    }

    @Override
    public boolean delete(Track track) {
        return tracks.remove(track.getId())!=null;
    }

    @Override
    public Track save(Track track) {

        Long id = nextId.getAndIncrement();
        track.setId(id);
        tracks.put(id, track);
        return track;
    }

    @Override
    public Optional<Track> findById(Long id) {
        return Optional.ofNullable(tracks.get(id));
    }

    @Override
    public Track getById(Long id) {
        return tracks.get(id);
    }

    @Override
    public List<Track> findAll() {
        return new ArrayList<>(tracks.values());
    }

    @Override
    public void resetDataStore() {
        tracks = new ConcurrentHashMap<>();
        nextId = new AtomicLong(1);
    }
}
