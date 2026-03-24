package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.Incident;
import com.cognizant.safeschool.projection.IncidentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    @Query("select new com.cognizant.safeschool.projection.IncidentProjection(i.incidentId, i.reporter.userId, i.type, i.location, i.date, i.status) from Incident i")
    List<IncidentProjection> getAllIncidents();

    @Query("select new com.cognizant.safeschool.projection.IncidentProjection(i.incidentId, i.reporter.userId, i.type, i.location, i.date, i.status) from Incident i where i.reporter.userId= :userId")
    List<IncidentProjection> getUserIncidents(@Param("userId") Long userId);
}
