package com.kakaoseventeen.dogwalking.notification.service;

import com.kakaoseventeen.dogwalking.dog.Dog;
import com.kakaoseventeen.dogwalking.dog.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadDogResponseDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadNotificationResponseDTO;
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
        List<Dog> dogList = dogJpaRepository.findDogsByMemberId(sessionMember.getId());
        //등록된 강아지가 없을 때
        if(dogList.isEmpty()){
            new RuntimeException("등록된 강아지가 없습니다.");
        }
        return new LoadDogResponseDTO(dogList);
    }

    public LoadNotificationResponseDTO loadNotification(Long id, Member sessionMember){
        Notification notification = notificationJpaRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("해당 공고글이 존재하지 않습니다.")
        );

        Dog dog = dogJpaRepository.findById(notification.getDog().getId()).orElseThrow(
                ()-> new RuntimeException("해당 강아지가 존재하지 않습니다.")
        );
        return new LoadNotificationResponseDTO(notification, dog);
    }
}
