package com.kakaoseventeen.dogwalking.notification.service;

import com.kakaoseventeen.dogwalking.dog.Dog;
import com.kakaoseventeen.dogwalking.dog.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.dto.request.UpdateNotificationDTO;
import com.kakaoseventeen.dogwalking.notification.dto.request.WriteNotificationDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadDogResponseDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadNotificationResponseDTO;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

	@Transactional
    public void writeNotification(WriteNotificationDTO writeNotificationDTO, Member sessionMember) throws RuntimeException {

        List<Dog> dogList = dogJpaRepository.findDogsByMemberId(sessionMember.getId());
        //dogList에 존재하는 id가 wrtieNotificationDTO와 일치하는지 확인
        Dog dogOP = dogList.stream().filter(dog -> dog.getId() == writeNotificationDTO.getDogId()).findFirst().orElseThrow(
                ()-> new RuntimeException("등록된 강아지가 아닙니다.")
        );

        if(sessionMember.getCoin().compareTo(writeNotificationDTO.getCoin())<0)
            throw new RuntimeException("보유한 멍코인이 부족합니다.");

        Notification notification = writeNotificationDTO.toEntity(dogOP);
        notificationJpaRepository.save(notification);
    }

    @Transactional
    public void editNotification(Long id, UpdateNotificationDTO updateNotificationDTO, Member sessionMember) throws RuntimeException {
        Notification notification = notificationJpaRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("해당 공고글이 존재하지 않습니다.")
        );

        List<Dog> dogList = dogJpaRepository.findDogsByMemberId(sessionMember.getId());
        //dogList에 존재하는 id가 wrtieNotificationDTO와 일치하는지 확인
        Dog dogOP = dogList.stream().filter(dog -> dog.getId() == updateNotificationDTO.getDogId()).findFirst().orElseThrow(
                ()-> new RuntimeException("등록된 강아지가 아닙니다.")
        );

        if(notification.getDog().getMember().getId() != sessionMember.getId()){
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        if(sessionMember.getCoin().compareTo(updateNotificationDTO.getCoin())<0)
            throw new RuntimeException("보유한 멍코인이 부족합니다.");

        notification.update(updateNotificationDTO, dogOP);
    }
}
