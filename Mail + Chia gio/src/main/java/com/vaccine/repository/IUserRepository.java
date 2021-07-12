package com.vaccine.repository;


import com.vaccine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    @Query("SELECT e FROM User e WHERE e.status = 2 or e.status = 1 order by e.id")
    List<User> getUserListIsDone();

    @Query(" select count(e.TimeVaccine) from User e where (e.status = 1 or  e.status = 2) and e.TimeVaccine =  (select e.TimeVaccine from e where e.id = (select max(id) from e)) and e.DateVaccine =  (select e.DateVaccine from e where e.id = (select max(id) from e))")
    Integer  countMaxTimeInDay();

    @Query(" select count(e.DateVaccine) from User e where (e.status = 1 or  e.status = 2) and  e.DateVaccine =  (select e.DateVaccine from e where e.id = (select max(id) from e))")
    int  countMaxDayToNext();

    @Query("select e.DateVaccine from User e where (e.status = 1 or  e.status = 2) and e.id = (select max(id) from e)")
    String getMaxDayFromData();

    @Query("select e.TimeVaccine from User e where (e.status = 1 or  e.status = 2) and e.id = (select max(id) from e)")
    String getMaxTimeFromData();

    @Query("select e from User  e where (e.status=1 or e.status=2) and e.DateVaccine=?1")
    List<User> getUserOneDay(String date);



}
