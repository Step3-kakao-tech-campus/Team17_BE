package com.kakaoseventeen.dogwalking.dog.service;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.dto.DogReqDTO;
import com.kakaoseventeen.dogwalking.dog.dto.DogRespDTO;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Dog(반려견) 서비스
 *
 * @author 승건 이
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class DogService {

    private final DogJpaRepository dogJpaRepository;

    private final MemberJpaRepository memberJpaRepository;


    /**
     * 강아지 프로필 등록 메서드
     */
    @Transactional
    public DogRespDTO.save saveDog(DogReqDTO dogReqDTO, Long userId) throws RuntimeException {
        Optional<Member> member = memberJpaRepository.findById(userId);

        if (member.isEmpty()) {
            throw new RuntimeException("올바르지 않은 유저 Id입니다.");
        }

        Dog dog = Dog.of(dogReqDTO, member.get());

        return new DogRespDTO.save(dogJpaRepository.save(dog));
    }

    @Transactional(readOnly = true)
    public DogRespDTO.findById findByDogId(int dogId) throws RuntimeException {
        Optional<Dog> dog = dogJpaRepository.findById(dogId);

        if (dog.isPresent()) {
            return new DogRespDTO.findById(dog.get());
        }
        else {
            throw new RuntimeException("올바르지 않은 유저 Id입니다.");
        }
    }
}
