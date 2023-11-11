package com.kakaoseventeen.dogwalking.notification.service;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.notification.*;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogRepository;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.dto.request.WriteNotificationReqDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadDogResDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadNotificationResDTO;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * NotificationService(공고글) 서비스
 *
 * @author 곽민주
 * @version 1.0
 */
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final DogRepository dogRepository;
    private final NotificationRepository notificationRepository;

    /**
     * 공고글 강아지 불러오기 메서드
     */
    public LoadDogResDTO loadDog(Member sessionMember) throws DogListNotExistException {
        List<Dog> dogList = dogRepository.findDogsByMember(sessionMember.getId());
        //등록된 강아지가 없을 때
        if(dogList.isEmpty()){
            throw new DogListNotExistException(MessageCode.DOG_LIST_NOT_EXIST);
        }
        return new LoadDogResDTO(dogList);
    }

    /**
     * 공고글 상세보기 메서드
     */
    public LoadNotificationResDTO loadNotification(Long id, Member sessionMember) throws RuntimeException{

        Notification notification = notificationRepository.findById(id).orElseThrow(
                ()-> new NotificationException(MessageCode.NOTIFICATION_NOT_EXIST)
        );

        Dog dog = dogRepository.findById(notification.getDog().getId()).orElseThrow(
                ()-> new DogNotExistException(MessageCode.DOG_NOT_EXIST)
        );

        Boolean isMine = null;
        isMine = sessionMember.getId() == dog.getMember().getId();
        return new LoadNotificationResDTO(notification, dog, isMine);
    }


    /**
     * 공고글 작성하기 메서드
     */
	@Transactional
    public void writeNotification(WriteNotificationReqDTO writeNotificationReqDTO, Member sessionMember) throws RuntimeException {

		if(writeNotificationReqDTO.getStart().isAfter(writeNotificationReqDTO.getEnd())){
            throw new NotificationTimeException(MessageCode.NOTIFICATION_TIME_ERROR);
        }

        List<Dog> dogList = dogRepository.findDogsByMember(sessionMember.getId());
        //dogList에 존재하는 id가 wrtieNotificationDTO와 일치하는지 확인
        Dog dogEntity = dogList.stream().filter(dog -> dog.getId() == writeNotificationReqDTO.getDogId()).findFirst().orElseThrow(
                ()-> new DogNotExistException(MessageCode.DOG_NOT_EXIST)
        );

        if(sessionMember.getCoin().compareTo(writeNotificationReqDTO.getCoin())<0)
            throw new CoinNotEnoughException(MessageCode.MUNG_COIN_NOT_ENOUGH);

        Notification notification = writeNotificationReqDTO.toEntity(dogEntity);
        notificationRepository.save(notification);
    }

}
