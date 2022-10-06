package com.haiduk.springcourse.projectThree.controllers;

import com.haiduk.springcourse.projectThree.convertors.SensorConverter;
import com.haiduk.springcourse.projectThree.dto.SensorDTO;
import com.haiduk.springcourse.projectThree.exception.SensorNotCreatedException;
import com.haiduk.springcourse.projectThree.exception.SensorNotFoundException;
import com.haiduk.springcourse.projectThree.services.SensorsService;
import com.haiduk.springcourse.projectThree.utill.CustomErrorResponse;
import com.haiduk.springcourse.projectThree.utill.SensorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final SensorConverter sensorConverter;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorConverter sensorConverter,
                             SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.sensorConverter = sensorConverter;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        sensorValidator.validate(sensorConverter.convertToSensor(sensorDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }
        sensorsService.save(sensorConverter.convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(SensorNotCreatedException e) {
        CustomErrorResponse response = new CustomErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/registration")
    public List<SensorDTO> getSensors() {
        return sensorConverter.convertToSensorDTO(sensorsService.findAllSensors());
    }

    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id) {
        return sensorConverter.convertToSensorDTO(sensorsService.findOneById(id));
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(SensorNotFoundException e) {
        CustomErrorResponse response = new CustomErrorResponse(
                "Sensor with this id wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

}
