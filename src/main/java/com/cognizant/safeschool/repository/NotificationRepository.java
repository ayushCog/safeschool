package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.Notification;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.NotificationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT new com.cognizant.safeschool.projection.NotificationProjection(n.notificationId, n.user.userId, n.entityId, n.message, n.category, n.status, n.createdDate) FROM Notification n WHERE n.user.userId = :userId and n.status='unread' ORDER BY n.createdDate DESC")
    List<NotificationProjection> findAllByUserId(@Param("userId") Long userId);
}
