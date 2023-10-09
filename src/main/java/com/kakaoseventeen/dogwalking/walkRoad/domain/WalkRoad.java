package com.kakaoseventeen.dogwalking.walkRoad.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class WalkRoad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

}
