package com.kakaoseventeen.dogwalking.notification.service;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.notification.*;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.dto.request.UpdateNotificationReqDTO;
import com.kakaoseventeen.dogwalking.notification.dto.request.WriteNotificationReqDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadDogRespDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadNotificationRespDTO;
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

    /**
     * 공고글 강아지 불러오기 메서드
     */
    public LoadDogRespDTO loadDog(Member sessionMember) throws DogListNotExistException {
        List<Dog> dogList = dogJpaRepository.findDogsByMember(sessionMember.getId());
        //등록된 강아지가 없을 때
        if(dogList.isEmpty()){
            throw new DogListNotExistException(MessageCode.DOG_LIST_NOT_EXIST);
        }
        return new LoadDogRespDTO(dogList);
    }

    /**
     * 공고글 상세보기 메서드
     */
    public LoadNotificationRespDTO loadNotification(Long id, Member sessionMember) throws RuntimeException{

        Notification notification = notificationJpaRepository.findById(id).orElseThrow(
                ()-> new NotificationException(MessageCode.NOTIFICATION_NOT_EXIST)
        );

        Dog dog = dogJpaRepository.findById(notification.getDog().getId()).orElseThrow(
                ()-> new DogNotExistException(MessageCode.DOG_NOT_EXIST)
        );

        Boolean isMine = null;
        isMine = sessionMember.getId() == dog.getMember().getId();
        return new LoadNotificationRespDTO(notification, dog, isMine);
    }


    /**
     * 공고글 작성하기 메서드
     */
	@Transactional
    public void writeNotification(WriteNotificationReqDTO writeNotificationReqDTO, Member sessionMember) throws RuntimeException {

		if(writeNotificationReqDTO.getStart().isAfter(writeNotificationReqDTO.getEnd())){
            throw new NotificationTimeException(MessageCode.NOTIFICATION_TIME_ERROR);
        }

        List<Dog> dogList = dogJpaRepository.findDogsByMember(sessionMember.getId());
        //dogList에 존재하는 id가 wrtieNotificationDTO와 일치하는지 확인
        Dog dogEntity = dogList.stream().filter(dog -> dog.getId() == writeNotificationReqDTO.getDogId()).findFirst().orElseThrow(
                ()-> new DogNotExistException(MessageCode.DOG_NOT_EXIST)
        );

        if(sessionMember.getCoin().compareTo(writeNotificationReqDTO.getCoin())<0)
            throw new CoinNotEnoughException(MessageCode.MUNG_COIN_NOT_ENOUGH);

        Notification notification = writeNotificationReqDTO.toEntity(dogEntity);
        notificationJpaRepository.save(notification);
    }

    /**
     * 공고글 수정하기 메서드
     */
    @Transactional
    public void editNotification(Long id, UpdateNotificationReqDTO updateNotificationReqDTO, Member sessionMember) throws RuntimeException {
        Notification notification = notificationJpaRepository.findById(id).orElseThrow(
                ()-> new NotificationException(MessageCode.NOTIFICATION_NOT_EXIST)
        );

        List<Dog> dogList = dogJpaRepository.findDogsByMember(sessionMember.getId());
        //dogList에 존재하는 id가 wrtieNotificationDTO와 일치하는지 확인
        Dog dogOP = dogList.stream().filter(dog -> dog.getId() == updateNotificationReqDTO.getDogId()).findFirst().orElseThrow(
                ()-> new DogNotExistException(MessageCode.DOG_NOT_EXIST)
        );

        if(notification.getDog().getMember().getId() != sessionMember.getId()){
            throw new NotificationUpdateException(MessageCode.NOTIFICATION_FORBIDDEN);
        }

        if(sessionMember.getCoin().compareTo(updateNotificationReqDTO.getCoin())<0)
            throw new CoinNotEnoughException(MessageCode.MUNG_COIN_NOT_ENOUGH);

        notification.update(updateNotificationReqDTO, dogOP);
    }

}
