package com.haiduk.springcourse.projectThree.utill;

import com.haiduk.springcourse.projectThree.entities.Measurement;
import com.haiduk.springcourse.projectThree.entities.Sensor;
import com.haiduk.springcourse.projectThree.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;
@Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> oClass) {
        return Measurement.class.equals(oClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Measurement measurement = (Measurement) target;
        if(sensorsService.findOneByName((measurement).getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor","","This sensor does not exist");
        }

    }
}
