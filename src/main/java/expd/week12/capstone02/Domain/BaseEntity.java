package expd.week12.capstone02.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class BaseEntity {
    @EqualsAndHashCode.Include
    private Long id;
}

