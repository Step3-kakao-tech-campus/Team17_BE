package com.kakaoseventeen.dogwalking.notification.service;

import com.kakaoseventeen.dogwalking.dog.Dog;
import com.kakaoseventeen.dogwalking.dog.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.dto.LoadDogResponseDTO;
import com.kakaoseventeen.dogwalking.notification.dto.LoadNotificationResponseDTO;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
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
    private final NotificationJpaRepository notificationJpaRepository;

    public LoadDogResponseDTO loadDog(Member sessionMember){
        //memberId로 dog 리스트 가져오기
        List<Dog> dogList = dogJpaRepository.findDogsByMemberId(sessionMember.getId());
        //등록된 강아지가 없을 때 에러
        if(dogList.isEmpty()){
            //TODO : 에러처리
        }
        return new LoadDogResponseDTO(dogList);
    }

    public LoadNotificationResponseDTO loadNotification(int id, Member sessionMember){
        //notificationId로 notification 객체 가져오기 TODO : 에러처리
        Notification notification = notificationJpaRepository.findById(id).orElseThrow();

        //본인이 적은 공고글이 아닐 경우 에러
        if(notification.getDog().getMember().getId() != sessionMember.getId()){
            //TODO : 에러처리
        }

        //notification 테이블에 저장된 dogId로 dog 정보 가져오기 TODO : 에러처리
        Dog dog = dogJpaRepository.findById(notification.getDog().getId()).orElseThrow();
        return new LoadNotificationResponseDTO(notification, dog);
    }
}
