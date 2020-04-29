package legoset.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class Profile {

    @Id
    private String number;

    @Column(nullable=false)
    private String url;

    private BigDecimal avgRating;

    @OneToOne(cascade=CascadeType.ALL)
    @MapsId
    @JoinColumn(name="number")
    private LegoSet legoSet;

    public Profile(String number, String url, BigDecimal avgRating) {
        this.number = number;
        this.url = url;
        this.avgRating = avgRating;
    }

}
