package pl.artist.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import pl.artist.persistence.id.ArtistId;
import pl.vinyl.persistence.entity.VinylEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArtistEntity implements Serializable
{
    @EmbeddedId
    @EqualsAndHashCode.Include
    private ArtistId id;

    private String name;

    private Integer formationYear;

    private Boolean isActive;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private List<VinylEntity> records = new ArrayList<>();

    public List<VinylEntity> getRecords()
    {
        if (this.records == null) {
            this.records = new ArrayList<>();
        }
        return this.records;
    }

    public ArtistEntity(ArtistId id, String name, Integer formationYear, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.formationYear = formationYear;
        this.isActive = isActive;
    }
}
