package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.NotificationException;
import com.cognizant.safeschool.dto.NotificationDto;
import com.cognizant.safeschool.entity.Notification;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.NotificationProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.NotificationRepository;
import com.cognizant.safeschool.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements INotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public SuccessResponseProjection<List<NotificationProjection>> getNotifications(Long userId) {
        log.info("Service request: Retrieving notification list for User ID: {}", userId);

        List<NotificationProjection> notifications = notificationRepository.findAllByUserId(userId);

        log.info("Found {} notifications for User ID: {}", notifications.size(), userId);

        return new SuccessResponseProjection<>(true, "Notifications retrieved", notifications);
    }

    @Transactional
    public SuccessResponseProjection<String> markAsRead(Long userId, Long notificationId) {
        log.info("Service request: Attempting to update status for Notification ID: {}", notificationId);

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> {
                    log.error("Update failed: Notification ID: {} not found", notificationId);
                    return new NotificationException("Notification not found", HttpStatus.NOT_FOUND);
                });

        if (!notification.getUser().getUserId().equals(userId)) {
            log.error("Alert: User ID: {} attempted to modify Notification ID: {} belonging to another user", userId, notificationId);
            throw new NotificationException("Unauthorized access to notification", HttpStatus.UNAUTHORIZED);
        }

        notification.setStatus("READ");
        notificationRepository.save(notification);

        log.info("Successfully marked Notification ID: {} as READ for User ID: {}", notificationId, userId);

        return new SuccessResponseProjection<>(true, "Notification marked as read", "read");
    }

    @Transactional
    public SuccessResponseProjection<String> broadcastAlert(NotificationDto notificationDto) {
        log.info("Service request: Processing global broadcast. Category: {}", notificationDto.getCategory());

        List<User> allUsers = userRepository.findAll();
        log.info("Found {} total users to receive global broadcast", allUsers.size());

        List<Notification> alerts = allUsers.stream().map(user -> {
            Notification n = new Notification();
            n.setUser(user);
            n.setEntityId(notificationDto.getEntityId());
            n.setMessage(notificationDto.getMessage());
            n.setCategory(notificationDto.getCategory());
            n.setStatus("UNREAD");
            n.setCreatedDate(LocalDateTime.now());
            return n;
        }).collect(Collectors.toList());

        notificationRepository.saveAll(alerts);
        log.info("Global broadcast completed successfully. {} notifications generated", alerts.size());
        return new SuccessResponseProjection<>(true, "Alert broadcasted to all users", notificationDto.getMessage());
    }

    @Transactional
    public SuccessResponseProjection<String> sendGroupAlert(String role, NotificationDto notificationDto) {
        log.info("Service request: Processing role-based alert for Group: {}", role);

        List<User> groupUsers = userRepository.findByRole(role);

        List<Notification> alerts = groupUsers.stream().map(user -> {
            Notification n = new Notification();
            n.setUser(user);
            n.setEntityId(notificationDto.getEntityId());
            n.setMessage(notificationDto.getMessage());
            n.setCategory(notificationDto.getCategory());
            n.setStatus("UNREAD");
            n.setCreatedDate(LocalDateTime.now());
            return n;
        }).collect(Collectors.toList());

        notificationRepository.saveAll(alerts);

        log.info("Role-based alert completed. {} notifications sent to {}", alerts.size(), role);

        return new SuccessResponseProjection<>(true, "Role-based alert sent", notificationDto.getMessage());
    }
}
