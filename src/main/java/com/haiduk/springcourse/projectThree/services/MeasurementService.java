package com.haiduk.springcourse.projectThree.services;

import com.haiduk.springcourse.projectThree.entities.Measurement;
import com.haiduk.springcourse.projectThree.repositories.MeasurementsRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementsRepositories measurementsRepositories;
    private final SensorsService sensorsService;

    public MeasurementService(MeasurementsRepositories measurementsRepositories, SensorsService sensorsService) {
        this.measurementsRepositories = measurementsRepositories;
        this.sensorsService = sensorsService;
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setSensor(sensorsService.findOneByName(measurement.getSensor().getName()).get());
        measurement.setMeasurementTime(LocalDateTime.now());
        measurementsRepositories.save(measurement);
    }

    public List<Measurement> findByAllMeasurement(){
       return measurementsRepositories.findAll();
    }
}
