package com.kakaoseventeen.dogwalking.application.repository;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("select a " +
            "from Application a " +
            "where a.appMemberId.id =:userId")
    List<Application> findApplicationByMemberId(Long userId);

}
