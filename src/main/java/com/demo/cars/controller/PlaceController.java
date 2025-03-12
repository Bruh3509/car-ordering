package com.demo.cars.controller;

import com.demo.cars.dto.PlaceDto;
import com.demo.cars.model.ErrorResponse;
import com.demo.cars.model.places.PlaceRequest;
import com.demo.cars.service.PlaceService;
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
@RequestMapping("/place")
@Tag(name = "place-controller", description = "managing place logic")
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
public class PlaceController {
    PlaceService service;

    @GetMapping(produces = "application/json")
    @Operation(summary = "get all places", description = "lists all places",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlaceDto.class)),
                            mediaType = "application/json")))
    public ResponseEntity<List<PlaceDto>> getAllPlaces() {
        return new ResponseEntity<>(service.getAllPlaces(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "get place", description = "retrieves place by id",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PlaceDto.class),
                            mediaType = "application/json")))
    public ResponseEntity<PlaceDto> getPlaceById(
            @PathVariable long id
    ) {
        return new ResponseEntity<>(service.getPlaceById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/place", consumes = "application/json", produces = "application/json")
    @Operation(summary = "add place", description = "creates new place",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = PlaceDto.class),
                            mediaType = "application/json")))
    public ResponseEntity<PlaceDto> addNewPlace(
            @RequestBody
            @Parameter(name = "new place post request")
            PlaceRequest request
    ) {
        return new ResponseEntity<>(service.addPlace(request), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "update place", description = "updates place info",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PlaceDto.class),
                            mediaType = "application/json")))
    public ResponseEntity<PlaceDto> updatePlaceInfo(
            @PathVariable
            long id,
            @RequestBody
            @Parameter(name = "update place info request", required = true)
            PlaceRequest request
    ) {
        return new ResponseEntity<>(service.updatePlace(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete place", description = "deletes place",
            responses = @ApiResponse(responseCode = "204", description = "No Content"))
    public ResponseEntity<Void> deletePlace(
            @PathVariable long id
    ) {
        service.deletePlace(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
