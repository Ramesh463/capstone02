package expd.week12.capstone02.Controllers;

import expd.week12.capstone02.Domain.Artist;
import expd.week12.capstone02.Domain.Track;
import expd.week12.capstone02.services.MgmtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/mgmt")
@RequiredArgsConstructor
public class MgmtController {
    private final MgmtService mgmtService;

    @Operation(summary = "Link existing Artist to a track ")
    @PostMapping
    public ResponseEntity<Track> addArtistToTrack(@RequestParam Long artistId, @RequestParam Long trackId) throws Exception {
        return ResponseEntity.ok(mgmtService.addArtistToTrack(artistId, trackId));
    }

    @Operation(summary = "Get artist info by track ID ")
    @GetMapping("/{trackid}")
        public ResponseEntity<List<Artist>> getArtistByTrackId(@PathVariable Long trackid) {
        return ResponseEntity.ok(mgmtService.getArtistByTrackID(trackid));
    }

    @Operation(summary = "Delete linked artist from an existing track")
    @DeleteMapping
    public ResponseEntity<Track> removeArtistFromTrack(@RequestParam Long artistId, @RequestParam Long trackId) {
        return ResponseEntity.ok(mgmtService.removeArtistFromTrack(artistId, trackId));
    }
}
