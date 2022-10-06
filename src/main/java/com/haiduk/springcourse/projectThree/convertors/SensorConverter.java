package com.haiduk.springcourse.projectThree.convertors;

import com.haiduk.springcourse.projectThree.dto.SensorDTO;
import com.haiduk.springcourse.projectThree.entities.Sensor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SensorConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public SensorConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
     public Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO,Sensor.class);
     }
     public SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
     }

     public List<SensorDTO> convertToSensorDTO(List<Sensor> sensors){
        return sensors.stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList());
     }


}
