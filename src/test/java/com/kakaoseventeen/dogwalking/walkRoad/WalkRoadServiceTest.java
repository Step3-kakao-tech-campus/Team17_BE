package com.kakaoseventeen.dogwalking.walkRoad;

import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.walk.service.WalkService;
import com.kakaoseventeen.dogwalking.walkRoad.domain.WalkRoad;
import com.kakaoseventeen.dogwalking.walkRoad.dto.WalkRoadReqDTO;
import com.kakaoseventeen.dogwalking.walkRoad.dto.WalkRoadRespDTO;
import com.kakaoseventeen.dogwalking.walkRoad.repository.WalkRoadRepository;
import com.kakaoseventeen.dogwalking.walkRoad.service.WalkRoadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class WalkRoadServiceTest {

    @InjectMocks
    WalkRoadService walkRoadService;

    @Mock
    WalkRoadRepository walkRoadRepository;

    @Mock
    WalkRepository walkRepository;


    /**
     * saveWalk() 테스트
     */
    @Test
    void test_save_walkRoad() throws Exception {
        // given
        Member walker = getMember();
        Member master = getMember();
        ChatRoom chatRoom = new ChatRoom();

        Walk walk = Walk.of(walker, master, chatRoom);

        WalkRoad walkRoad = WalkRoad.of(2.3213123, 3.3213123, walk);

        // mocking
        given(walkRepository.findById(any()))
                .willReturn(Optional.ofNullable(walk));
        given(walkRoadRepository.save(any()))
                .willReturn(walkRoad);

        // when
        WalkRoadRespDTO.SaveWalkResp walkRoadOP = walkRoadService.saveWalkRoad(getReqDTO(walkRoad), walk.getId());

        // then
        Assertions.assertEquals(walkRoadOP.getLat(), 2.3213123);
        Assertions.assertEquals(walkRoadOP.getLng(), 3.3213123);
    }

    /**
     * findAllByWalkId 테스트
     */
    @Test
    void test_find_all_by_walk_id() throws Exception {
        // given
        Member walker = getMember();
        Member master = getMember();
        ChatRoom chatRoom = new ChatRoom();

        Walk walk = Walk.of(walker, master, chatRoom);

        WalkRoad walkRoad1 = WalkRoad.of(2.3213123, 3.3213123, walk);
        WalkRoad walkRoad2 = WalkRoad.of(2.3213123, 3.3213123, walk);
        WalkRoad walkRoad3 = WalkRoad.of(2.3213123, 3.3213123, walk);
        WalkRoad walkRoad4 = WalkRoad.of(2.3213123, 3.3213123, walk);
        List<WalkRoad> walkRoads = new ArrayList<>();
        walkRoads.addAll(List.of(new WalkRoad[]{walkRoad1, walkRoad2, walkRoad3, walkRoad4}));


        // mocking
        given(walkRepository.findById(any()))
                .willReturn(Optional.ofNullable(walk));
        given(walkRoadRepository.findWalkRoadsByWalk(any()))
                .willReturn(walkRoads);

        // when
        WalkRoadRespDTO.FindByWalkId walkRoadOP = walkRoadService.findAllByWalkId(walk.getId());

        // then
        Assertions.assertEquals(walkRoadOP.getWalkRoadLatLngDTOS().size(), 4);
    }

    private Member getMember(){
        return Member.builder()
                .email("yardyard@likelion.org")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("견주유저1")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }

    private WalkRoadReqDTO getReqDTO(WalkRoad walkRoad){
        WalkRoadReqDTO walkRoadReqDTO = new WalkRoadReqDTO();
        walkRoadReqDTO.setLat(walkRoad.getLat());
        walkRoadReqDTO.setLng(walkRoad.getLng());
        return walkRoadReqDTO;
    }
}
