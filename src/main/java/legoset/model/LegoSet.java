package legoset.model;

import java.time.Year;

import javax.persistence.*;

import lombok.*;

import jpa.YearConverter;

@NoArgsConstructor
@Data
@Entity
public class LegoSet {

    @Id
    private String number;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    @Convert(converter=YearConverter.class)
    private Year year;

    @Column(nullable=false)
    private int pieces;

    @OneToOne(mappedBy="legoSet", cascade=CascadeType.ALL)
    @ToString.Exclude
    private Profile profile;

    public LegoSet(String number, String name, Year year, int pieces) {
        this.number = number;
        this.name = name;
        this.year = year;
        this.pieces = pieces;
    }

}
