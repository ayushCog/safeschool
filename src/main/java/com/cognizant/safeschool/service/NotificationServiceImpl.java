package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.NotificationException;
import com.cognizant.safeschool.entity.Notification;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.NotificationProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.NotificationRepository;
import com.cognizant.safeschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements INotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public SuccessResponseProjection<List<NotificationProjection>> getNotifications(Long userId) {
        List<NotificationProjection> notifications = notificationRepository.findAllByUserId(userId);
        return new SuccessResponseProjection<>(true, "Notifications retrieved", notifications);
    }

    @Transactional
    public SuccessResponseProjection<String> markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException("Notification not found", HttpStatus.NOT_FOUND));

        if (!notification.getUser().getUserId().equals(userId)) {
            throw new NotificationException("Unauthorized access to notification", HttpStatus.UNAUTHORIZED);
        }

        notification.setStatus("read");
        notificationRepository.save(notification);

        return new SuccessResponseProjection<>(true, "Notification marked as read", "read");
    }

    @Transactional
    public SuccessResponseProjection<String> broadcastAlert(String message, String category) {
        List<User> allUsers = userRepository.findAll();

        List<Notification> alerts = allUsers.stream().map(user -> {
            Notification n = new Notification();
            n.setUser(user);
            n.setMessage(message);
            n.setCategory(category);
            n.setStatus("unread");
            n.setCreatedDate(LocalDateTime.now());
            return n;
        }).collect(Collectors.toList());

        notificationRepository.saveAll(alerts);
        return new SuccessResponseProjection<>(true, "Alert broadcasted to all users", message);
    }

    @Transactional
    public SuccessResponseProjection<String> sendGroupAlert(String role, String message, String category) {
        List<User> groupUsers = userRepository.findByRole(role);

        List<Notification> alerts = groupUsers.stream().map(user -> {
            Notification n = new Notification();
            n.setUser(user);
            n.setMessage(message);
            n.setCategory(category);
            n.setStatus("UNREAD");
            n.setCreatedDate(LocalDateTime.now());
            return n;
        }).collect(Collectors.toList());

        notificationRepository.saveAll(alerts);
        return new SuccessResponseProjection<>(true, "Role-based alert sent", message);
    }
}
