package com.vaccine.service.warehouseVaccine;

import com.vaccine.model.WarehouseVaccine;
import com.vaccine.repository.IWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class WarehouseVaccineService implements IWarehouseVaccineService{
    @Autowired
    private IWarehouseRepository warehouseRepository;



    @Override
    public List<WarehouseVaccine> findAll() {
        return warehouseRepository.findAll();
    }

    @Override
    public Optional<WarehouseVaccine> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public WarehouseVaccine save(WarehouseVaccine warehouseVaccine) {
        return warehouseRepository.save(warehouseVaccine);
    }

    @Override
    public void remove(Long id) {
        warehouseRepository.deleteById(id);
    }

    @Override
    public List<WarehouseVaccine> getUserListIsDone(String DATE) {
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
        return null;
    }

    @Override
    public Long amountVaccine() {
        return warehouseRepository.amountVaccine();
    }
}
