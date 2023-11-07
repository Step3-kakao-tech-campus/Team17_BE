package com.kakaoseventeen.dogwalking.notification.service;


import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.CursorRequest;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.dto.response.HomeRespDTO;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HomeService {
    private final NotificationJpaRepository notificationJpaRepository;

    public HomeRespDTO home(CursorRequest cursorRequest, Double lat, Double lng, List<String> big, List<String> breed, String search, CustomUserDetails customUserDetails) {
        String image;
        if(customUserDetails == null){
            image = null;
        }else{
            image = customUserDetails.getMember().getProfileImage();
        }

        List<Notification> notifications = findPosts(cursorRequest, lat, lng, big, breed, search);

        Double lastKey = getLastKey(notifications,lat,lng);

        return HomeRespDTO.of(cursorRequest.next(lastKey, 20), notifications, image);
    }

    private List<Notification> findPosts(CursorRequest cursorRequest, Double lat, Double lng, List<String> big, List<String> breed, String search) {

        int pageSize = cursorRequest.hasSize() ? cursorRequest.getSize() : 20;

        Pageable pageable = PageRequest.of(0, pageSize);

        if(search != ""){
            if (!cursorRequest.hasKey()) {

                if (big.isEmpty() && breed.isEmpty())
                    return notificationJpaRepository.findAllHasNoneSearch(search, lat, lng, pageable);
                if (big.isEmpty())
                    return notificationJpaRepository.findAllHasBreedSearch(search, lat, lng, breed, pageable);
                if (breed.isEmpty())
                    return notificationJpaRepository.findAllHasSizeSearch(search, lat, lng, big, pageable);

                return notificationJpaRepository.findAllHasSizeAndBreedSearch(search, lat, lng, big, breed, pageable);
            }
            if (big.isEmpty() && breed.isEmpty())
                return notificationJpaRepository.findAllHasNoneKeySearch(search, lat, lng,cursorRequest.getKey(), pageable);
            if (big.isEmpty())
                return notificationJpaRepository.findAllHasBreedKeySearch(search, breed, lat, lng,cursorRequest.getKey(), pageable);
            if (breed.isEmpty())
                return notificationJpaRepository.findAllHasSizeKeySearch(search, big, lat, lng, cursorRequest.getKey(), pageable);

            return notificationJpaRepository.findAllHasSizeAndBreedKeySearch(search, big, breed, lat, lng, cursorRequest.getKey(), pageable);
        }
        else{
            if (!cursorRequest.hasKey()) {

                if (big.isEmpty() && breed.isEmpty())
                    return notificationJpaRepository.findAllHasNone(lat, lng, pageable);
                if (big.isEmpty())
                    return notificationJpaRepository.findAllHasBreed(lat, lng, breed, pageable);
                if (breed.isEmpty())
                    return notificationJpaRepository.findAllHasSize(lat, lng, big, pageable);

                return notificationJpaRepository.findAllHasSizeAndBreed(lat, lng, big, breed, pageable);
            }
            if (big.isEmpty() && breed.isEmpty())
                return notificationJpaRepository.findAllHasNoneKey(lat, lng,cursorRequest.getKey(), pageable);
            if (big.isEmpty())
                return notificationJpaRepository.findAllHasBreedKey(breed, lat, lng,cursorRequest.getKey(), pageable);
            if (breed.isEmpty())
                return notificationJpaRepository.findAllHasSizeKey(big, lat, lng, cursorRequest.getKey(), pageable);

            return notificationJpaRepository.findAllHasSizeAndBreedKey(big, breed, lat, lng, cursorRequest.getKey(), pageable);
        }
    }

    private static Double getLastKey(List<Notification> notifications, Double curLat, Double curLng) {
        if (notifications.isEmpty()) {
            return CursorRequest.NONE_KEY;
        }

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