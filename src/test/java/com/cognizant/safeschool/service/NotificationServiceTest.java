package com.cognizant.safeschool.service;

import com.cognizant.safeschool.entity.Notification;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.NotificationProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.NotificationRepository;
import com.cognizant.safeschool.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    public void getMyNotifications_ValidTest() {
        Long userId = 1L;
        NotificationProjection np = new NotificationProjection(
                10L, userId, 5L, "Test Alert", "Incident", "unread", LocalDateTime.now()
        );

        when(notificationRepository.findAllByUserId(userId)).thenReturn(Arrays.asList(np));

        SuccessResponseProjection<List<NotificationProjection>> response = notificationService.getNotifications(userId);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        assertEquals("unread", response.getData().get(0).getStatus());
    }

    @Test
    public void markAsRead_ValidTest() {
        Long userId = 1L;
        Long notifId = 10L;

        User user = new User();
        user.setUserId(userId);

        Notification notification = new Notification();
        notification.setNotificationId(notifId);
        notification.setUser(user);
        notification.setStatus("read");

        when(notificationRepository.findById(notifId)).thenReturn(Optional.of(notification));

        SuccessResponseProjection<String> response = notificationService.markAsRead(userId, notifId);

        assertTrue(response.isSuccess());
        assertEquals("read", notification.getStatus());
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    public void markAsRead_UnauthorizedUser_ThrowsException() {
        Long ownerId = 2L;
        Long maliciousUserId = 1L;
        Long notifId = 10L;

        User owner = new User();
        owner.setUserId(ownerId);

        Notification notification = new Notification();
        notification.setUser(owner);

        when(notificationRepository.findById(notifId)).thenReturn(Optional.of(notification));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificationService.markAsRead(maliciousUserId, notifId);
        });

        assertEquals("Unauthorized access to notification", exception.getMessage());
        verify(notificationRepository, never()).save(any());
    }

    @Test
    public void broadcastAlert_ValidTest() {
        User u1 = new User();
        User u2 = new User();
        when(userRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        SuccessResponseProjection<String> response = notificationService.broadcastAlert("Danger!", "Incident");

        assertTrue(response.isSuccess());
        verify(notificationRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void sendGroupAlert_ValidTest() {
        String role = "OFFICER";
        User officer = new User();
        officer.setRole(role);
        when(userRepository.findByRole(role)).thenReturn(Arrays.asList(officer));

        SuccessResponseProjection<String> response = notificationService.sendGroupAlert(role, "Meeting", "User");

        assertTrue(response.isSuccess());
        verify(notificationRepository, times(1)).saveAll(anyList());
    }
}
