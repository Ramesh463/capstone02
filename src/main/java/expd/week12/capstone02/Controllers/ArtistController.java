package expd.week12.capstone02.Controllers;

import expd.week12.capstone02.Domain.Artist;
import expd.week12.capstone02.services.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/admin/artists")
@RequiredArgsConstructor

public class ArtistController {


    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @Operation(summary = "Genre List = Rock,JAZZ,POP ")
    @PostMapping
    public ResponseEntity<Artist> saveArtist(@Valid @RequestBody Artist artist) {
        return ResponseEntity.ok(artistService.createArtist(artist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist( @PathVariable Long id,@Valid @RequestBody Artist artist) {
        if(!Objects.equals(artist.getId(), id)) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = artistService.updateArtist(artist);
        return result ? ResponseEntity.ok(artistService.getArtistById(id)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable Long id) {
        boolean result = artistService.deleteArtist(id);
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
