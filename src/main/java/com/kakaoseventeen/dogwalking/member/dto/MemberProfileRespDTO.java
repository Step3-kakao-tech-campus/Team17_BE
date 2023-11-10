package com.kakaoseventeen.dogwalking.member.dto;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.review.domain.Review;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.domain.WalkStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter @Builder
public class MemberProfileRespDTO {

    private Long id;

    private String nickname;

    private String profileImage;

    private String profileContent;

    private int dogBowl;

    private BigDecimal coin;

    private List<NotificationDTO> notifications;

    private List<DogDTO> dogs;

    private List<ApplicationDTO> applications;

    private List<ReviewDTO> reviews;


    public static MemberProfileRespDTO of(Member member, List<Notification> notifications, List<Dog> dogs, List<Application> applications, List<Review> reviews){
        MemberProfileRespDTO dto = MemberProfileRespDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .profileContent(member.getProfileContent())
                .dogBowl(member.getDogBowl())
                .coin(member.getCoin())
                .build();

        if (!notifications.isEmpty()) {
            dto.setNotifications(notifications.stream()
                    .map(notification -> {
                        Optional<Walk> walk = Optional.ofNullable(notification.getWalk());
                        if(walk.isPresent()) {
                            return new NotificationDTO(notification, walk.get().getWalkStatus().toString());
                        }
                        return new NotificationDTO(notification, "");
                    })
                    .collect(Collectors.toList()));
        }

        if (!dogs.isEmpty()) {
            dto.setDogs(dogs.stream().map(DogDTO::new).collect(Collectors.toList()));
        }

        if (!applications.isEmpty()) {
            dto.setApplications(applications.stream().map(ApplicationDTO::new).collect(Collectors.toList()));
        }

        if (!reviews.isEmpty()) {
            dto.setReviews(reviews.stream().map(ReviewDTO::new).collect(Collectors.toList()));
        }

        return dto;
    }

    @Getter @Setter
    public static class DogDTO {

        private Long id;

        private String image;

        public DogDTO(Dog dog){
            this.id = dog.getId();
            this.image = dog.getImage();
        }
    }

    @Getter @Setter
    public static class NotificationDTO {

        private Long id;

        private String title;

        private LocalDateTime start;

        private LocalDateTime end;

        private InDogDTO dog;

        private String walkStatus;

        public NotificationDTO(Notification notification, String walkStatus){
            this.id = notification.getId();
            this.title = notification.getTitle();
            this.start = notification.getStartTime();
            this.end = notification.getEndTime();
            this.walkStatus = walkStatus;
            this.dog = new InDogDTO(notification.getDog());
        }
    }

    @Getter @Setter
    public static class InDogDTO {

        private String breed;

        private int age;

        private String image;

        public InDogDTO(Dog dog){
            this.breed = dog.getBreed();
            this.age = dog.getAge();
            this.image = dog.getImage();
        }
    }

    @Getter @Setter
    public static class ApplicationDTO {

        private Long id;

        private String title;

        private String aboutMe;

        private String certification;

        private String experience;

        public ApplicationDTO(Application application){
            this.id = application.getApplicationId();
            this.title = application.getTitle();
            this.aboutMe = application.getAboutMe();
            this.certification = application.getCertification();
            this.experience = application.getExperience();
        }
    }

    @Getter @Setter
    public static class ReviewDTO {

        private Long id;

        private String reviewContent;

        private String writerImage;

        private LocalDateTime reviewTime;

        public ReviewDTO(Review review){
            this.id = review.getReviewId();
            this.reviewContent = review.getReviewContent();
            this.writerImage = review.getReceiverId().getProfileImage();
            this.reviewTime = review.getCreatedAt();
        }
    }
}
