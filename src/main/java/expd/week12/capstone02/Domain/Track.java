package expd.week12.capstone02.Domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor

public class Track extends BaseEntity {
   @EqualsAndHashCode.Include

   @NotBlank(message = "TrackName cannot be Blank")
   @NotNull(message = "TrackName cannot be null")
   private String trackName;

   @NotBlank (message = "Movie Name cannot be blank")
   @NotNull(message = "Movie Name cannot be null")
   private String movieName;


   @NotNull(message = "Genre cannot be null")
   private Genre genre;

   @Getter
   private List<Artist> artists = new ArrayList<>();

   public void addArtist(Artist artist) {
      artists.add(artist);
   }

   public void removeArtist(Artist artist) {
      artists.remove(artist);
   }


}
