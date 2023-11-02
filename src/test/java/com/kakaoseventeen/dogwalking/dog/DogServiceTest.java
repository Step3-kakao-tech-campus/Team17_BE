package com.kakaoseventeen.dogwalking.dog;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.dto.DogReqDTO;
import com.kakaoseventeen.dogwalking.dog.dto.DogRespDTO;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.dog.service.DogService;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Dog(애완견) 서비스 테스트
 *
 * @author 승건 이
 * @version 1.0
 * 참고 레퍼런스 : https://galid1.tistory.com/772
 */
@ExtendWith(MockitoExtension.class)
public class DogServiceTest {

    @InjectMocks
    private DogService dogService;

    @Mock
    private DogJpaRepository dogJpaRepository;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    /**
     * saveDog() 테스트
     */
    @Test
    void test_save_dog() throws Exception {
        // given
        Member walker = getMember();

        DogReqDTO dogReqDTO = DogReqDTO.builder()
                                .age(2)
                                .breed("요크")
                                .image("img")
                                .name("복슬")
                                .sex("MALE")
                                .size("대형")
                                .specificity("안물어요")
                                .build();

        Dog dog = Dog.of(dogReqDTO, walker);

        // mocking
        given(memberJpaRepository.findById(any()))
                .willReturn(Optional.ofNullable(walker));
        given(dogJpaRepository.save(any()))
                .willReturn(dog);

        // when
        //DogRespDTO.save dogOP = dogService.saveDog(dogReqDTO, 1L);

        // then
        //Assertions.assertEquals(dogOP.getSex(), "MALE");
    }

    /**
     * saveDog() 테스트
     */
    @Test
    void test_find_By_dog() throws Exception {
        // given
        Member walker = getMember();

        DogReqDTO dogReqDTO = DogReqDTO.builder()
                .age(2)
                .breed("요크")
                .image("img")
                .name("복슬")
                .sex("MALE")
                .size("대형")
                .specificity("안물어요")
                .build();

        Dog dog = Dog.of(dogReqDTO, walker);


        // mocking
        given(dogJpaRepository.findById(any()))
                .willReturn(Optional.ofNullable(dog));

        // when
        DogRespDTO.findById dogOP = dogService.findByDogId(1L);

        // then
        Assertions.assertEquals(dogOP.getSex(), "MALE");
    }

    private Member getMember(){
        return Member.builder()
                .email("yardyard@likelion.org")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("견주유저1")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }
}
