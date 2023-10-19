package com.kakaoseventeen.dogwalking.notification.repository;

import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {

    @Query("select n " +
            "from Notification n " +
            "join fetch n.dog " +
            "where n.walk.master.id = :userId or n.walk.walker.id =:userId")
    List<Notification> findNotificationByMemberId(Long userId);

}
