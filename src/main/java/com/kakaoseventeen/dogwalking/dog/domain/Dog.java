package com.kakaoseventeen.dogwalking.dog.domain;

import com.kakaoseventeen.dogwalking.dog.dto.DogReqDTO;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name="dog_tb")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 45, nullable = false)
    private String name;
    @Column(length = 45, nullable = false)
    private String sex;
    @Column(length = 45, nullable = false)
    private String breed;
    @Column(length = 45, nullable = false)
    private String size;
    @Column(length = 256)
    private String specificity;
    private int age;
    @Column(length = 256)
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public static Dog of(DogReqDTO dogReqDTO, Member member){
        return Dog.builder()
                .age(dogReqDTO.getAge())
                .breed(dogReqDTO.getBreed())
                .image(dogReqDTO.getImage())
                .name(dogReqDTO.getName())
                .sex(dogReqDTO.getSex())
                .size(dogReqDTO.getSize())
                .specificity(dogReqDTO.getSpecificity())
                .member(member)
                .build();
    }
}
