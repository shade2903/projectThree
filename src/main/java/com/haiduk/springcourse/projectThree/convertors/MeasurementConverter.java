package com.haiduk.springcourse.projectThree.convertors;

import com.haiduk.springcourse.projectThree.dto.MeasurementDTO;
import com.haiduk.springcourse.projectThree.entities.Measurement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeasurementConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Measurement converterToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO,Measurement.class);
    }

    public MeasurementDTO converterToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    public List<MeasurementDTO> converterToListMeasurementDTO(List<Measurement> measurements){
        return measurements.stream().map(this::converterToMeasurementDTO)
                .collect(Collectors.toList());
    }
}
