package com.haiduk.springcourse.projectThree.utill;

import com.haiduk.springcourse.projectThree.entities.Sensor;
import com.haiduk.springcourse.projectThree.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class SensorValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> oClass) {
        return Sensor.class.equals(oClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;
        if(sensorsService.findOneByName(sensor.getName()).isPresent()){
            errors.rejectValue("name","","This name is already taken");
        }

    }
}
