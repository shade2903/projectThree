package com.haiduk.springcourse.projectThree.repositories;

import com.haiduk.springcourse.projectThree.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepositories extends JpaRepository<Measurement, Integer> {

}
