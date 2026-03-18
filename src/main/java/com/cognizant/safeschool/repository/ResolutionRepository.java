package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.Resolution;
import com.cognizant.safeschool.projection.ResolutionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResolutionRepository extends JpaRepository<Resolution, Long> {
    @Query("select new com.cognizant.safeschool.projection.ResolutionProjection(r.resolutionId, r.incident.incidentId, r.officer.userId, r.actions, r.date, r.status) from Resolution r where r.incident.incidentId= : incidentId")
    public ResolutionProjection findResolutionByIncidentId(@Param("incidentId") Long incidentId);

    @Query("select new com.cognizant.safeschool.projection.ResolutionProjection(r.resolutionId, r.incident.incidentId, r.officer.userId, r.actions, r.date, r.status) from Resolution r where r.officer.userId= :userId")
    List<ResolutionProjection> getUserResolution(@Param("userId") Long userId);
}
