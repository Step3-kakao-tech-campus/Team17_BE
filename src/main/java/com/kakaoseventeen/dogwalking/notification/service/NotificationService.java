package com.kakaoseventeen.dogwalking.notification.service;

import com.kakaoseventeen.dogwalking.dog.Dog;
import com.kakaoseventeen.dogwalking.dog.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.dto.LoadDogResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NotificationService {
    private final DogJpaRepository dogJpaRepository;

    public LoadDogResponseDTO loadDog(Member sessionMember){
        //memberId로 dog 리스트 가져오기
        List<Dog> dogList = dogJpaRepository.findDogsByMemberId(sessionMember.getId());
        //등록된 강아지가 없을 때 에러
        if(dogList.isEmpty()){
            //TODO : 에러처리
        }
        return new LoadDogResponseDTO(dogList);
    }
}
