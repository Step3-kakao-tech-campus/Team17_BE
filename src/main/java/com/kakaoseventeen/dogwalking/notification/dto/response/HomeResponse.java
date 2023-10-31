package com.kakaoseventeen.dogwalking.notification.dto.response;

import com.kakaoseventeen.dogwalking._core.utils.CursorRequest;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import lombok.Getter;

import java.util.List;

@Getter
public class HomeResponse {

    private CursorRequest nextCursorRequest;
    private List<NotificationDTO> notifications;
    private String image;

    public HomeResponse(CursorRequest nextCursorRequest, List<NotificationDTO> notifications, String image) {
        this.nextCursorRequest = nextCursorRequest;
        this.notifications = notifications;
        this.image = image;
    }

    public static HomeResponse of(CursorRequest nextCursorRequest, List<Notification> notifications, String image) {
        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map(NotificationDTO::new)
                .toList();
        return new HomeResponse(nextCursorRequest, notificationDTOs, image);
    }

    @Getter
    public static class NotificationDTO {
        private Long notificationId;
        private String title;
        private Double lat;
        private Double lng;
        private DogInfo dogInfo;
        private int dogBowl;

        public NotificationDTO(Long notificationId, String title, Double lat, Double lng, DogInfo dogInfo, int dogBowl) {
            this.notificationId = notificationId;
            this.title = title;
            this.lat = lat;
            this.lng = lng;
            this.dogInfo = dogInfo;
            this.dogBowl = dogBowl;
        }

        public NotificationDTO(Notification post) {
            this(
                    post.getId(),
                    post.getTitle(),
                    post.getLat(),
                    post.getLng(),
                    new DogInfo(
                            post.getDog().getName(),
                            post.getDog().getAge(),
                            post.getDog().getSex(),
                            post.getDog().getBreed()
                    ),
                    post.getDog().getMember().getDogBowl()
            );
        }
    }

    @Getter
    public static class DogInfo {
        private String name;
        private int age;
        private String sex;
        private String breed;

        public DogInfo(String name, int age, String sex, String breed) {
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.breed = breed;
        }
    }


}