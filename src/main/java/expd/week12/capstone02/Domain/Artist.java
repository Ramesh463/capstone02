package expd.week12.capstone02.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor


public class Artist extends BaseEntity {
    @EqualsAndHashCode.Include

    @NotBlank (message = "Name Cannot be blank")
    @NotNull(message = "Name Cannot be null")
    private String artistName;


    @NotNull(message = "Genre Cannot be null")
    @Enumerated(EnumType.STRING)
    private Genre genre;


    @NotBlank(message = "Country can't be blank")
    @NotNull(message = "Country can't be null")
    private String country;




}
