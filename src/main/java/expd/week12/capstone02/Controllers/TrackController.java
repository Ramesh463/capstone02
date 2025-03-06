package expd.week12.capstone02.Controllers;

import expd.week12.capstone02.DAO.inMemory.TrackDAO;
import expd.week12.capstone02.Domain.Genre;
import expd.week12.capstone02.Domain.Track;
import expd.week12.capstone02.services.TrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/admin/track")
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;

    @GetMapping
    public ResponseEntity<List<Track>> getAllTracks() {
         return ResponseEntity.ok(trackService.getAllTracks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
        return ResponseEntity.ok(trackService.getTrackById(id));
    }



    @PostMapping
    public ResponseEntity<Track> createTrack(@Valid @RequestBody Track track) {
        return ResponseEntity.ok(trackService.createTrack(track));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable Long id,@Valid @RequestBody Track track) {
        if(!Objects.equals(track.getId(), id)) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = trackService.updateTrack(track);
        return result ? ResponseEntity.ok(track) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Track> deleteTrack(@PathVariable Long id) {
        boolean result = trackService.deleteTrack(id);
        return result ? ResponseEntity.accepted().build() : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
