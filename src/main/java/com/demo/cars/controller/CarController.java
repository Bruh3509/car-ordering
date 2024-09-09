package com.demo.cars.controller;

import com.demo.cars.dto.CarDto;
import com.demo.cars.mapper.CarMapper;
import com.demo.cars.model.ErrorResponse;
import com.demo.cars.model.car.CarRequest;
import com.demo.cars.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
@Tag(name = "car-controller", description = "managing car logic")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                        mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                        mediaType = "application/json"))
})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarController {
    CarService carService;
    CarMapper carMapper;

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "get car info", description = "get car info by its id",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CarDto.class),
                            mediaType = "application/json")))
    public ResponseEntity<CarDto> getCarInfo(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "get all cars", description = "lists all available cars",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CarDto.class)),
                            mediaType = "application/json")))
    public ResponseEntity<List<CarDto>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = "application/json")
    @Operation(summary = "add car", description = "register a new car",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = CarDto.class),
                            mediaType = "application/json")))
    public ResponseEntity<Void> regNewCar(
            @RequestBody
            @Parameter(name = "post request")
            CarRequest request
    ) {
        carService.regCar(request);
        return new ResponseEntity<>(HttpStatus.CREATED);


    @PatchMapping(value = "/{id}/update", consumes = "application/json", produces = "application/json")
    @Operation(summary = "update info", description = "updates car info by id",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CarDto.class),
                            mediaType = "application/json")))
    public ResponseEntity<CarDto> updateCarInfo(
            @PathVariable
            Long id,
            @RequestBody
            @Parameter(name = "update request")
            CarRequest request
    ) {
        var response = carService.updateCar(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "delete", description = "delete car",
            responses = @ApiResponse(responseCode = "204", description = "No Content"))
    public ResponseEntity<Void> deleteCar(
            @PathVariable Long id
    ) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
