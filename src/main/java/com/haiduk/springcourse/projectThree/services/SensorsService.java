package com.haiduk.springcourse.projectThree.services;

import com.haiduk.springcourse.projectThree.entities.Sensor;
import com.haiduk.springcourse.projectThree.repositories.SensorsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
  private final   SensorsRepositories sensorsRepositories;

  @Autowired
    public SensorsService(SensorsRepositories sensorsRepositories) {
        this.sensorsRepositories = sensorsRepositories;
    }

    @Transactional
    public void save(Sensor sensor){
      sensorsRepositories.save(sensor);
    }

    public Sensor findOneById(int id){
      Optional<Sensor> foundSensor = sensorsRepositories.findById(id);
      return foundSensor.orElse(null);
    }
    public List<Sensor> findAllSensors(){
      return sensorsRepositories.findAll();

    }

    public Optional<Sensor> findOneByName(String name){
      return sensorsRepositories.findSensorByName(name);
    }

    @Transactional
    public void update(int id, Sensor updatedSensor){
      updatedSensor.setId(id);
      sensorsRepositories.save(updatedSensor);
    }

    @Transactional
    public void deleteById(int id){
      sensorsRepositories.deleteById(id);
    }
}
