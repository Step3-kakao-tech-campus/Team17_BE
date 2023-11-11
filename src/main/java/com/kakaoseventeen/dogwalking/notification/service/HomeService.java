package com.kakaoseventeen.dogwalking.notification.service;


import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.CursorRequest;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.dto.response.HomeResDTO;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * HomeService(메인페이지) 서비스
 *
 * @author 곽민주
 * @version 1.0
 */
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HomeService {

    private final NotificationRepository notificationRepository;

    /**
     * 메인페이지에서 공고글을 불러오는 메서드
     */
    public HomeResDTO home(CursorRequest cursorRequest, Double lat, Double lng, List<String> big, List<String> breed, String search, CustomUserDetails customUserDetails) {
        String image;
        if(customUserDetails == null){
            image = null;
        }else{
            image = customUserDetails.getMember().getProfileImage();
        }

        List<Notification> notifications = findPosts(cursorRequest, lat, lng, big, breed, search);

        Double lastKey = getLastKey(notifications,lat,lng);

        return HomeResDTO.of(cursorRequest.next(lastKey, 20), notifications, image);
    }

    /**
     * 메인페이지에서 위도, 경도, 견종, 크기, 검색어를 통해 포스트를 가져오는 메서드
     */
    private List<Notification> findPosts(CursorRequest cursorRequest, Double lat, Double lng, List<String> big, List<String> breed, String search) {

        int pageSize = cursorRequest.hasSize() ? cursorRequest.getSize() : 20;

        Pageable pageable = PageRequest.of(0, pageSize);

        if(search != ""){
            if (!cursorRequest.hasKey()) 
                return searchNotificationsWithNoKey(lat, lng, big, breed, search, pageable);
            
            return searchNotificationsWithKey(cursorRequest, lat, lng, big, breed, search, pageable);
        }
        else{
            if (!cursorRequest.hasKey())
                return getNotificationsWithNoKey(lat, lng, big, breed, pageable);

            return getNotificationsWithKey(cursorRequest, lat, lng, big, breed, pageable);
        }
    }

    /**
     * 검색어는 존재하지 않고 키가 존재할 경우의 메서드
     */
    private List<Notification> getNotificationsWithKey(CursorRequest cursorRequest, Double lat, Double lng, List<String> big, List<String> breed, Pageable pageable) {
        if (big.isEmpty() && breed.isEmpty())
            return notificationRepository.findAllHasNoneKey(lat, lng, cursorRequest.getKey(), pageable);
        if (big.isEmpty())
            return notificationRepository.findAllHasBreedKey(breed, lat, lng, cursorRequest.getKey(), pageable);
        if (breed.isEmpty())
            return notificationRepository.findAllHasSizeKey(big, lat, lng, cursorRequest.getKey(), pageable);

        return notificationRepository.findAllHasSizeAndBreedKey(big, breed, lat, lng, cursorRequest.getKey(), pageable);
    }

    /**
     * 검색어는 존재하지 않고 키가 존재하지 않을 경우의 메서드
     */
    private List<Notification> getNotificationsWithNoKey(Double lat, Double lng, List<String> big, List<String> breed, Pageable pageable) {
        if (big.isEmpty() && breed.isEmpty())
            return notificationRepository.findAllHasNone(lat, lng, pageable);
        if (big.isEmpty())
            return notificationRepository.findAllHasBreed(lat, lng, breed, pageable);
        if (breed.isEmpty())
            return notificationRepository.findAllHasSize(lat, lng, big, pageable);

        return notificationRepository.findAllHasSizeAndBreed(lat, lng, big, breed, pageable);
    }

    /**
     * 검색어가 존재하고 키도 존재할 경우의 메서드
     */
    private List<Notification> searchNotificationsWithKey(CursorRequest cursorRequest, Double lat, Double lng, List<String> big, List<String> breed, String search, Pageable pageable) {
        if (big.isEmpty() && breed.isEmpty())
            return notificationRepository.findAllHasNoneKeySearch(search, lat, lng, cursorRequest.getKey(), pageable);
        if (big.isEmpty())
            return notificationRepository.findAllHasBreedKeySearch(search, breed, lat, lng, cursorRequest.getKey(), pageable);
        if (breed.isEmpty())
            return notificationRepository.findAllHasSizeKeySearch(search, big, lat, lng, cursorRequest.getKey(), pageable);

        return notificationRepository.findAllHasSizeAndBreedKeySearch(search, big, breed, lat, lng, cursorRequest.getKey(), pageable);
    }

    /**
     * 검색어가 존재하고 키는 존재하지 않을 경우의 메서드
     */
    private List<Notification> searchNotificationsWithNoKey(Double lat, Double lng, List<String> big, List<String> breed, String search, Pageable pageable) {
        if (big.isEmpty() && breed.isEmpty())
            return notificationRepository.findAllHasNoneSearch(search, lat, lng, pageable);
        if (big.isEmpty())
            return notificationRepository.findAllHasBreedSearch(search, lat, lng, breed, pageable);
        if (breed.isEmpty())
            return notificationRepository.findAllHasSizeSearch(search, lat, lng, big, pageable);

        return notificationRepository.findAllHasSizeAndBreedSearch(search, lat, lng, big, breed, pageable);
    }

    /**
     * 마지막 공고글의 키를 가져오는 메서드
     */
    private static Double getLastKey(List<Notification> notifications, Double curLat, Double curLng) {
        if (notifications.isEmpty()) {
            return CursorRequest.NONE_KEY;
        }
		else{
            Double lat = notifications.get(notifications.size() - 1).getLat();
            Double lng = notifications.get(notifications.size() - 1).getLng();

            final Double R = 6371.0;

            // 위도 및 경도를 라디안으로 변환
            Double latRad = Math.toRadians(lat);
            Double lngRad = Math.toRadians(lng);
            Double curLatRad = Math.toRadians(curLat);
            Double curLngRad = Math.toRadians(curLng);

            // 위도 및 경도의 차이 계산
            Double dLat = curLatRad - latRad;
            Double dLng = curLngRad - lngRad;

            // 하버사인 공식을 사용하여 거리 계산
            Double formula = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(latRad) * Math.cos(curLatRad) *
                            Math.sin(dLng / 2) * Math.sin(dLng / 2);

            Double result = 2 * Math.atan2(Math.sqrt(formula), Math.sqrt(1 - formula));

            return R * result;
        }


    }
}