package com.vaccine.repository;

import com.vaccine.model.AdminDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAdminDestinationRepository extends JpaRepository<AdminDestination, Long> {
    @Query("SELECT count (u) from User u where u.checkStatus=1")
    Long countCheckStatus1();
}
