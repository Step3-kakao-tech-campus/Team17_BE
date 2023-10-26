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

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "ORDER BY (6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasNone(@Param("latitude") Double lat, @Param("longitude") Double lng, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE d.breed IN :breed " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasBreed(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("breed") List<String> breed, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE d.size IN :size " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasSize(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE (d.size IN :size OR d.breed IN :breed) " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasSizeAndBreed(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, @Param("breed") List<String> breed, Pageable pageable);


    /************************************************************************************/

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE (6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) > :key " +
            "ORDER BY (6371.0 *acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasNoneKey(@Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE d.breed IN :breed " +
            "AND (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) > :key " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasBreedKey(@Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE d.size IN :size " +
            "AND (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) > :key " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasSizeKey(@Param("size") List<String> size, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE (d.size IN :size OR d.breed IN :breed) " +
            "AND (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) > :key " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasSizeAndBreedKey(@Param("size") List<String> size, @Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    // @Query(value = "SELECT N.ID, N.lat, N.lng, D.name, D.sex, D.age,  FROM Notification AS N LEFT JOIN Dog as D ON N.notification_id = D.id",nativeQuery = true)

    /*
    @Query(value = "SELECT n.id, n.lat, n.lng, d.image, d.age, d.sex " +
            "ST_Distance_Sphere(POINT(:longitude, :latitude),POINT(n.lat, n.lng)) AS dis " +
            "FROM Notification AS n", nativeQuery = true)
    List<Notification> findAllHasNoneKey(@Param("latitude")Double lat, @Param("longitude")Double lng, Long key, Pageable pageable);
     */

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE (n.title LIKE %:tit%) " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasNoneSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE d.breed IN :breed " +
            "AND (n.title LIKE %:tit%) " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasBreedSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("breed") List<String> breed, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE d.size IN :size " +
            "AND (n.title LIKE %:tit%) " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasSizeSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE (d.size IN :size OR d.breed IN :breed) " +
            "AND (n.title LIKE %:tit%) " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasSizeAndBreedSearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("size") List<String> size, @Param("breed") List<String> breed, Pageable pageable);


    /********************************************************************************/

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) > :key " +
            "AND (n.title LIKE %:tit%) " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasNoneKeySearch(@Param("tit") String tit, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE d.breed IN :breed " +
            "AND (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) > :key " +
            "AND (n.title LIKE %:tit%) " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasBreedKeySearch(@Param("tit") String tit, @Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE d.size IN :size " +
            "AND (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) > :key " +
            "AND (n.title LIKE %:tit%) " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasSizeKeySearch(@Param("tit") String tit, @Param("size") List<String> size, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);

    @Query("SELECT n " +
            "FROM Notification n " +
            "JOIN FETCH n.dog d " +
            "WHERE (d.size IN :size OR d.breed IN :breed) " +
            "AND (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) > :key " +
            "AND (n.title LIKE %:tit%) " +
            "ORDER BY (6371.0*acos(cos(radians(:latitude))*cos(radians(n.lat))*cos(radians(n.lng)-radians(:longitude))+sin(radians(:latitude))*sin(radians(n.lat)))) ASC")
    List<Notification> findAllHasSizeAndBreedKeySearch(@Param("tit") String tit, @Param("size") List<String> size, @Param("breed") List<String> breed, @Param("latitude") Double lat, @Param("longitude") Double lng, @Param("key") Double key, Pageable pageable);


}
