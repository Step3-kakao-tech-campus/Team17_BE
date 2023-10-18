package com.kakaoseventeen.dogwalking.walk;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.GetEntity;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.dto.WalkRespDTO;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.walk.service.WalkService;
import com.kakaoseventeen.dogwalking.chat.model.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private MatchingRepository matchingRepository;

    @BeforeEach
    void set_up() {

    }


    /**
     * saveWalk() 테스트
     */
    @Test
    void test_save_walk() throws Exception {
        // given
        Member walker = GetEntity.getWalker1();
        Member master = GetEntity.getMaster1();

        CustomUserDetails customUserDetails = new CustomUserDetails(master);

        Match match = GetEntity.getMatch();
        Notification notification = GetEntity.getNotification();

        Walk walk = Walk.of(walker, master, notification);

        // mocking
        given(walkRepository.save(any()))
                .willReturn(walk);
        given(walkRepository.findById(any()))
                .willReturn(Optional.ofNullable(walk));
        given(memberJpaRepository.findById(any()))
                .willReturn(Optional.ofNullable(walker));
        given(matchingRepository.findMatchById(any()))
                .willReturn(Optional.ofNullable(match));

        // when
        walkService.saveWalk(customUserDetails, walk.getId(), walker.getId());

        // then
        Walk walkOP = walkRepository.findById(1L).get();

        Assertions.assertEquals(walkOP.getWalkStatus().toString(), "READY");
    }

    /**
     * startWalk() 테스트
     * 궁금한 점 : Service를 단독적으로 테스팅 했을 때 의존하고 있는 Repository가 Mock 객체이므로, 단순히 Service 계층이 Repository에 쿼리를 날리는 역할 그 이상을 하지 않을 때 테스팅 하는 의미가 있는지
     *           왜냐하면 아래 코드를 보면 알 수 있듯이 목 객체인 Repositoru 자체의 반환값을 지정을 해두면 Service에서는 무엇을 테스팅 하는지가 궁금
     * 내가 내린 결론 : Service 코드를 독립한다는 것에 포커싱을 두자. -> Repository가 정상적으로 돌아가는 것에는 해당 테스트는 관심이 없다. 단순히 Service의 코드가 잘 수행되는지가 궁금한 것이다.
     *               그렇기에 Repository의 반환값을 고정을 해두고, 해당 값을 통해 Service가 어떻게 비즈니스 로직을 거치는지가 해당 테스트의 최종 궁금점이다.
     */
    @Test
    void test_startWalk() throws Exception{
        // given
        Member walker = GetEntity.getWalker2();
        Member master = GetEntity.getMaster2();

        Notification notification = GetEntity.getNotification();

        Walk walk = Walk.of(walker, master, notification);

        // mocking
        given(matchingRepository.findWalkFromMatchById(any()))
                .willReturn(Optional.ofNullable(walk));

        // when
        WalkRespDTO.StartWalk walkOP = walkService.startWalk(1L);

        // then
        Assertions.assertNotNull(walkOP.getStartTime());
        System.out.println(walkOP.getStartTime());
    }

}
