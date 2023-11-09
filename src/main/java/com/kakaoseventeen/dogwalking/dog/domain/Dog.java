package com.kakaoseventeen.dogwalking.dog.domain;

import com.kakaoseventeen.dogwalking.dog.dto.DogReqDTO;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

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

    /**
     * Dog DTO, Member, dogProfile 주소를 이용해서 Dog 객체를 생성하는 정적 팩터리 메서드
     */
    public static Dog of(DogReqDTO dogReqDTO, Member member, String dogProfile){
        return Dog.builder()
                .age(Integer.parseInt(dogReqDTO.getAge()))
                .breed(dogReqDTO.getBreed())
                .image(dogProfile)
                .name(dogReqDTO.getName())
                .sex(dogReqDTO.getSex())
                .size(dogReqDTO.getSize())
                .specificity(dogReqDTO.getSpecificity())
                .member(member)
                .build();
    }

    /**
     * Dog를 수정하는 메서드
     */
    public void updateDog(DogReqDTO dogReqDTO, String imageUrl){
        if (!(Objects.equals(imageUrl, "null"))){
            this.image = imageUrl;
        }

        if (!dogReqDTO.getName().isEmpty()){
            this.name = dogReqDTO.getName();
        }

        if (!dogReqDTO.getBreed().isEmpty()){
            this.breed = dogReqDTO.getBreed();
        }

        if (!(Integer.parseInt(dogReqDTO.getAge()) == 0)){
            this.age = Integer.parseInt(dogReqDTO.getAge());
        }

        if (!dogReqDTO.getSize().isEmpty()){
            this.size = dogReqDTO.getSize();
        }

        if (!dogReqDTO.getSpecificity().isEmpty()){
            this.specificity = dogReqDTO.getSpecificity();
        }
    }
}
