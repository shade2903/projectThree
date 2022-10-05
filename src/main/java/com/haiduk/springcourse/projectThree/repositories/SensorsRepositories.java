package com.haiduk.springcourse.projectThree.repositories;

import com.haiduk.springcourse.projectThree.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorsRepositories extends JpaRepository<Sensor,Integer> {
    Optional<Sensor> findSensorByName(String name);
}
