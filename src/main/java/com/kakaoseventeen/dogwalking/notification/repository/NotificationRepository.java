package com.kakaoseventeen.dogwalking.notification.repository;

import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Notification(공고글) 레파지토리
 *
 * @author 곽민주
 * @version 1.0
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * 공고글을 작성한 멤버의 아이디로 공고글을 찾는 쿼리
     */
    @Query("select n " +
            "from Notification n " +
            "join fetch n.dog d " +
            "join fetch d.member m " +
            "where m.id = :userId")
    List<Notification> findNotificationByMemberId(Long userId);

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이로 가까운 순으로 공고글 리스트를 구하는 쿼리
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasNone(@Param("latitude") Double lat, @Param("longitude") Double lng, Pageable pageable);

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이로 가까운 순으로 공고글 리스트를 구하는 쿼리 + 견종 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.breed IN :breed " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasBreed(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("breed") List<String> breed, Pageable pageable);

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이로 가까운 순으로 공고글 리스트를 구하는 쿼리 + 강아지 사이즈 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.size IN :size " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSize(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, Pageable pageable);

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이로 가까운 순으로 공고글 리스트를 구하는 쿼리 + 강아지 사이즈, 견종 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.size IN :size OR d.breed IN :breed " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeAndBreed(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, @Param("breed") List<String> breed, Pageable pageable);


    /************************************************************************************/

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이가 키값보다 클 경우 공고글 리스트를 구하는 쿼리
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "GROUP BY n.notification_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasNoneKey(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이가 키값보다 클 경우 공고글 리스트를 구하는 쿼리 + 견종 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.breed IN :breed " +
            "GROUP BY n.notification_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasBreedKey(@Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이가 키값보다 클 경우 공고글 리스트를 구하는 쿼리 + 사이즈 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.size IN :size " +
            "GROUP BY n.notification_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeKey(@Param("size") List<String> size, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이가 키값보다 클 경우 공고글 리스트를 구하는 쿼리 + 사이즈 필터링, 견종 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE (d.size IN :size OR d.breed IN :breed) " +
            "GROUP BY n.notification_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeAndBreedKey(@Param("size") List<String> size, @Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


	/**********************************************************************************************************/

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이로 가까운 순으로 공고글 리스트를 구하는 쿼리, 검색어로 검색
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasNoneSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, Pageable pageable);

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이로 가까운 순으로 공고글 리스트를 구하는 쿼리, 검색어로 검색
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "AND d.breed IN :breed " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasBreedSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("breed") List<String> breed, Pageable pageable);

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이로 가까운 순으로 공고글 리스트를 구하는 쿼리, 검색어로 검색, 강아지 사이즈 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "AND d.size IN :size " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, Pageable pageable);


    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이로 가까운 순으로 공고글 리스트를 구하는 쿼리, 검색어로 검색, 강아지 사이즈, 견종 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "AND (d.size IN :size OR d.breed IN :breed) " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeAndBreedSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, @Param("breed") List<String> breed, Pageable pageable);


    /********************************************************************************/

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이가 키값보다 클 경우 공고글 리스트를 구하는 쿼리, 검색어로 검색
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "GROUP BY n.notification_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasNoneKeySearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이가 키값보다 클 경우 공고글 리스트를 구하는 쿼리, 검색어로 검색, 견종 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE (n.title LIKE CONCAT('%', :tit, '%')) AND (n.breed IN :breed) " +
            "GROUP BY n.notification_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasBreedKeySearch(@Param("tit") String tit, @Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이가 키값보다 클 경우 공고글 리스트를 구하는 쿼리, 검색어로 검색, 강아지 사이즈 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE (n.title LIKE CONCAT('%', :tit, '%')) AND (n.size IN :size) " +
            "GROUP BY n.notification_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeKeySearch(@Param("tit") String tit, @Param("size") List<String> size, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    /**
     * 공고글의 위도와 경도를 기준으로 현재 위치와의 거리차이가 키값보다 클 경우 공고글 리스트를 구하는 쿼리, 검색어로 검색, 강아지 사이즈, 견종 필터링
     */
    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM notification n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE (n.title LIKE CONCAT('%', :tit, '%')) AND (n.size IN :size) OR (n.breed IN :breed) " +
            "GROUP BY n.notification_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeAndBreedKeySearch(@Param("tit") String tit, @Param("size") List<String> size, @Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    /**
     * NotificationId를 통해서 해당 Notification 엔티티에 연관된 MemberId를 조회한다.
     *
     * @author 박영규
     */
    @Query(value = "SELECT noti FROM Notification noti JOIN fetch noti.dog JOIN fetch noti.dog.member WHERE noti.id = :notificationId")
    Notification mfindMember(Long notificationId);

}
