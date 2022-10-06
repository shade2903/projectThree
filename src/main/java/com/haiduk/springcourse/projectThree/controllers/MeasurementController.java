package com.haiduk.springcourse.projectThree.controllers;

import com.haiduk.springcourse.projectThree.convertors.MeasurementConverter;
import com.haiduk.springcourse.projectThree.dto.MeasurementDTO;
import com.haiduk.springcourse.projectThree.exception.MeasurementNotCreatedException;
import com.haiduk.springcourse.projectThree.exception.SensorNotFoundException;
import com.haiduk.springcourse.projectThree.services.MeasurementService;
import com.haiduk.springcourse.projectThree.services.SensorsService;
import com.haiduk.springcourse.projectThree.utill.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("measurement")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementConverter measurementConverter;
    private final SensorsService sensorsService;


    public MeasurementController(MeasurementService measurementService, MeasurementConverter measurementConverter, SensorsService sensorsService) {
        this.measurementService = measurementService;
        this.measurementConverter = measurementConverter;
        this.sensorsService = sensorsService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        if(sensorsService.findOneByName(measurementDTO.getSensor().getName()).isEmpty()){
            throw new SensorNotFoundException();
        }

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotCreatedException(errorMsg.toString());
        }
        measurementService.save(measurementConverter.converterToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(MeasurementNotCreatedException e) {
        CustomErrorResponse response = new CustomErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
