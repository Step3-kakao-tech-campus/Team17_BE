package com.kakaoseventeen.dogwalking.notification.repository;

import com.kakaoseventeen.dogwalking.dog.Dog;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotificationJpaRepository extends JpaRepository<Notification, Integer> {


    /**
     *  dogId를 통해서 Notification 엔티티를 가져오는 쿼리
     *  Dog와 Notification은 1:1 관계이다.
     */
    @Query("select n from Notification n join fetch n.dog where n.dog.id = :dogId")
    Optional<Notification> findByNotificationWithDogId(int dogId);

}
