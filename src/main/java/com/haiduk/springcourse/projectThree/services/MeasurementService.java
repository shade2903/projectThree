package com.haiduk.springcourse.projectThree.services;

import com.haiduk.springcourse.projectThree.entities.Measurement;
import com.haiduk.springcourse.projectThree.repositories.MeasurementsRepositories;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {
    private final MeasurementsRepositories measurementsRepositories;

    public MeasurementService(MeasurementsRepositories measurementsRepositories) {
        this.measurementsRepositories = measurementsRepositories;
    }
    public void save(Measurement measurement){
        measurementsRepositories.save(measurement);
    }
}
