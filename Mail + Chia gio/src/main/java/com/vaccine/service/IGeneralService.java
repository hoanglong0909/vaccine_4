package com.vaccine.service;



import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {

    List<T> findAll();

    Optional<T> findById(Long id);

    T save(T t);

    void remove(Long id);

    List<T> getUserListIsDone(String DATE);

    Integer  countMaxTimeInDay();

    String getMaxDayFromData();

    String getMaxTimeFromData();

    int  countMaxDayToNext();

    Long countCheckStatus1();

    Long amountVaccine();
}