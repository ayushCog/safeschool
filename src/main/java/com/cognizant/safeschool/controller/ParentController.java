package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.ParentResgistrationDto;
import com.cognizant.safeschool.projection.ParentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.service.IParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parent")
public class ParentController {
    @Autowired
    private IParentService iParentService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponseProjection<ParentProjection>> registerParent(@RequestBody ParentResgistrationDto parentResgistrationDto) {
        return ResponseEntity.status(HttpStatus.OK).body(iParentService.addParent(parentResgistrationDto));
    }
}
