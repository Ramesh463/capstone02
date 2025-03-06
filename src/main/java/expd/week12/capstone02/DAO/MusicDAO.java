package expd.week12.capstone02.DAO;

import expd.week12.capstone02.Domain.Artist;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public interface MusicDAO<T> {
    boolean update(T updateObjest);
    boolean delete(T deleteObject);
    T save(T saveObjest);
    Optional<T> findById(Long id);
    T getById(Long id);
    List<T> findAll();
    void resetDataStore();
    default List<T> findBy(Predicate<T> pred) {
        return findAll().stream()
                .filter(pred)
                .collect(toList());
    }
}
