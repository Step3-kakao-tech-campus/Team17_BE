package com.kakaoseventeen.dogwalking.notification.repository;

import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {

}
