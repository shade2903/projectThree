package com.haiduk.springcourse.projectThree.controllers;

import com.haiduk.springcourse.projectThree.convertors.MeasurementConverter;
import com.haiduk.springcourse.projectThree.dto.MeasurementDTO;
import com.haiduk.springcourse.projectThree.entities.Measurement;
import com.haiduk.springcourse.projectThree.exception.MeasurementNotCreatedException;
import com.haiduk.springcourse.projectThree.services.MeasurementService;
import com.haiduk.springcourse.projectThree.services.SensorsService;
import com.haiduk.springcourse.projectThree.utill.CustomErrorResponse;
import com.haiduk.springcourse.projectThree.utill.MeasurementValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementConverter measurementConverter;
    private final SensorsService sensorsService;
    private final MeasurementValidator measurementValidator;


    public MeasurementController(MeasurementService measurementService, MeasurementConverter measurementConverter, SensorsService sensorsService, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.measurementConverter = measurementConverter;
        this.sensorsService = sensorsService;
        this.measurementValidator = measurementValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {

        Measurement measurement = measurementConverter.converterToMeasurement(measurementDTO);
        measurementValidator.validate(measurement,bindingResult);

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
        measurementService.save(measurement);
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

    @GetMapping
    public List<MeasurementDTO> getMeasurements(){
        return measurementConverter.converterToMeasurementDTO(measurementService.findByAllMeasurement());
    }
    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        List<MeasurementDTO> measurements = measurementConverter.converterToMeasurementDTO(
                measurementService.findByAllMeasurement());
        return measurements.stream().filter(MeasurementDTO::getRaining).count();

    }


}
