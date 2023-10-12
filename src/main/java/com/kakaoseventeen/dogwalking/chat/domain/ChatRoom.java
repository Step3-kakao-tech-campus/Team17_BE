package com.kakaoseventeen.dogwalking.chat.domain;

import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHATROOM_ID")
    private long id;

    @OneToOne(mappedBy = "chatRoom")
    private Walk walk;
}
