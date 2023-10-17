package com.kakaoseventeen.dogwalking.dog;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="dog_tb")
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
    @Column(length = 256)
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Dog(Long id, String name, String sex, String breed, String size, String image, Member member){
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.breed = breed;
        this.size = size;
        this.image = image;
        this.member = member;
    }
}
