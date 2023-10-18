package com.kakaoseventeen.dogwalking.application.repository;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
