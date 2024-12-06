package com.boukyApps.myguep.repository;

import com.boukyApps.myguep.model.Entretien;
import com.boukyApps.myguep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends JpaRepository<Entretien, Long> {
}
