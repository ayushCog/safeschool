package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.projection.NotificationProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    INotificationService notificationServiceImpl;

    @GetMapping("/allnotifications/{userId}")
    public ResponseEntity<SuccessResponseProjection<List<NotificationProjection>>> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationServiceImpl.getNotifications(userId));
    }

    @PatchMapping("/mark-read/{userId}/{notificationId}")
    public ResponseEntity<SuccessResponseProjection<String>> markAsRead(
            @PathVariable Long userId,
            @PathVariable Long notificationId) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationServiceImpl.markAsRead(userId, notificationId));
    }

    @PostMapping("/broadcast")
    public ResponseEntity<SuccessResponseProjection<String>> broadcastAlert(
            @RequestParam String message,
            @RequestParam String category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationServiceImpl.broadcastAlert(message, category));
    }

    @PostMapping("/role/{role}")
    public ResponseEntity<SuccessResponseProjection<String>> sendGroupAlert(
            @PathVariable String role,
            @RequestParam String message,
            @RequestParam String category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationServiceImpl.sendGroupAlert(role, message, category));
    }
}
