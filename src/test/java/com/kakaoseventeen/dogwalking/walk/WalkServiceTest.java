package com.kakaoseventeen.dogwalking.walk;

import com.kakaoseventeen.dogwalking._core.utils.GetEntity;
import com.kakaoseventeen.dogwalking.match.repository.MatchRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.dto.WalkResDTO;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.walk.service.WalkService;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Walk(산책 경로) 서비스 테스트
 *
 * @author 승건 이
 * @version 1.0
 * 참고 레퍼런스 : https://galid1.tistory.com/772
 */
@ExtendWith(MockitoExtension.class)
public class WalkServiceTest {
    /**
     * @InjectMocks -> Mockito에서 사용되며, Mock된 가짜 객체(WalkRepository)를 진짜 객체(WalkService)에 주입할 수 있게한다.
     */
    @InjectMocks
    private WalkService walkService;

    /**
     * @Mock -> 메소드가 return 하는 타입의 어떤 예측한 값이 나온다고 가정 해서 전체적인 흐름을 테스트 할 때 사용
     */
    @Mock
    private WalkRepository walkRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MatchRepository matchRepository;



    @Test
    void test_startWalk() throws Exception{
        // given
        Member walker = GetEntity.getWalker2();
        Member master = GetEntity.getMaster2();

        Notification notification = GetEntity.getNotification();

        Walk walk = Walk.of(walker, master, notification);

        // mocking
        given(matchRepository.findWalkFromMatchById(any()))
                .willReturn(Optional.ofNullable(walk));

        // when
        WalkResDTO.StartWalk walkOP = walkService.startWalk(1L);

        // then
        Assertions.assertNotNull(walkOP.getStartTime());
        System.out.println(walkOP.getStartTime());
    }

}
