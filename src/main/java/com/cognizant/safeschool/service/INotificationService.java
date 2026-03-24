package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.NotificationDto;
import com.cognizant.safeschool.projection.NotificationProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;

import java.util.List;

public interface INotificationService {
    SuccessResponseProjection<List<NotificationProjection>> getNotifications(Long userId);
    SuccessResponseProjection<String> markAsRead(Long userId, Long notificationId);
    SuccessResponseProjection<String> broadcastAlert(NotificationDto notificationDto);
    SuccessResponseProjection<String> sendGroupAlert(String role, NotificationDto notificationDto);
}
