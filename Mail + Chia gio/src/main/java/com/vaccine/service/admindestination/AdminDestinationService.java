package com.vaccine.service.admindestination;

import com.vaccine.model.AdminDestination;
import com.vaccine.repository.IAdminDestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


public class AdminDestinationService implements IAdminDestinationService{

    @Autowired
    IAdminDestinationRepository adminDestinationRepository;

    @Override
    public List<AdminDestination> findAll() {
        return adminDestinationRepository.findAll();
    }

    @Override
    public Optional<AdminDestination> findById(Long id) {
        return adminDestinationRepository.findById(id);
    }

    @Override
    public AdminDestination save(AdminDestination adminDestination) {
        return adminDestinationRepository.save(adminDestination);
    }

    @Override
    public void remove(Long id) {
        adminDestinationRepository.deleteById(id);
    }

    @Override
    public List<AdminDestination> getUserListIsDone(String DATE) {
        return null;
    }

    @Override
    public Integer countMaxTimeInDay() {
        return null;
    }

    @Override
    public String getMaxDayFromData() {
        return null;
    }

    @Override
    public String getMaxTimeFromData() {
        return null;
    }

    @Override
    public int countMaxDayToNext() {
        return 0;
    }


    @Override
    public Long countCheckStatus1() {
        return adminDestinationRepository.countCheckStatus1();
    }

    @Override
    public Long amountVaccine() {
        return null;
    }
}
