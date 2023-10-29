package com.kakaoseventeen.dogwalking.notification.repository;

import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {

    @Query("select n " +
            "from Notification n " +
            "join fetch n.dog " +
            "where n.walk.master.id = :userId or n.walk.walker.id =:userId")
    List<Notification> findNotificationByMemberId(Long userId);

    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasNone(@Param("latitude") Double lat, @Param("longitude") Double lng, Pageable pageable);

    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.breed IN :breed " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasBreed(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("breed") List<String> breed, Pageable pageable);

    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.size IN :size " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSize(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, Pageable pageable);

    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.size IN :size OR d.breed IN :breed " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeAndBreed(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, @Param("breed") List<String> breed, Pageable pageable);


    /************************************************************************************/

    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "GROUP BY n.chatroom_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasNoneKey(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.breed IN :breed " +
            "GROUP BY n.chatroom_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasBreedKey(@Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE d.size IN :size " +
            "GROUP BY n.chatroom_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeKey(@Param("size") List<String> size, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE (d.size IN :size OR d.breed IN :breed) " +
            "GROUP BY n.chatroom_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeAndBreedKey(@Param("size") List<String> size, @Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    // @Query(value = "SELECT N.ID, N.lat, N.lng, D.name, D.sex, D.age,  FROM Notification AS N LEFT JOIN Dog as D ON N.notification_id = D.id",nativeQuery = true)

    /*
    @Query(value = "SELECT n.id, n.lat, n.lng, d.image, d.age, d.sex " +
            "ST_Distance_Sphere(POINT(:longitude, :latitude),POINT(n.lat, n.lng)) AS dis " +
            "FROM Notification AS n", nativeQuery = true)
    List<Notification> findAllHasNoneKey(@Param("latitude")Double lat, @Param("longitude")Double lng, Long key, Pageable pageable);
     */
	/**********************************************************************************************************/

    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasNoneSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, Pageable pageable);


    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "AND d.breed IN :breed " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasBreedSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("breed") List<String> breed, Pageable pageable);


    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "AND d.size IN :size " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, Pageable pageable);


    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "AND (d.size IN :size OR d.breed IN :breed) " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeAndBreedSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, @Param("breed") List<String> breed, Pageable pageable);


    /********************************************************************************/

    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE n.title LIKE CONCAT('%', :tit, '%') " +
            "GROUP BY n.chatroom_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasNoneKeySearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE (n.title LIKE CONCAT('%', :tit, '%')) AND (n.breed IN :breed) " +
            "GROUP BY n.chatroom_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasBreedKeySearch(@Param("tit") String tit, @Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE (n.title LIKE CONCAT('%', :tit, '%')) AND (n.size IN :size) " +
            "GROUP BY n.chatroom_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeKeySearch(@Param("tit") String tit, @Param("size") List<String> size, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    @Query(value = "SELECT n.*, 6371.0 *acos(cos(radians(34.24))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat))) as distance " +
            "FROM Notification_tb n " +
            "JOIN dog_tb d on n.dog_id = d.id " +
            "WHERE (n.title LIKE CONCAT('%', :tit, '%')) AND (n.size IN :size) + OR (n.breed IN :breed) " +
            "GROUP BY n.chatroom_id " +
            "HAVING distance > :key " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Notification> findAllHasSizeAndBreedKeySearch(@Param("tit") String tit, @Param("size") List<String> size, @Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

}
