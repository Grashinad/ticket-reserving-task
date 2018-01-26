package com.epam.grashin.reservingtask.controller;

import com.epam.grashin.reservingtask.entity.Train;
import com.epam.grashin.reservingtask.repository.TrainRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/train")
@Api(value="trainInfo", description="Train management: searching and adding")
public class TrainController {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    TrainRepository trainRepository;

    @RequestMapping(path="/searchDestination", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Train> getTrainsByDestination(@RequestParam String destination) {
        return trainRepository.findTrainByDestination(destination);
    }

    @RequestMapping(path="/searchDate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> getTrainsByDate(
            @ApiParam(name = "date", value = "String representation of date in format 'yyyy-MM-dd'", required = true) @RequestParam String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString, dateTimeFormatter);
            return ResponseEntity.status(HttpStatus.OK).body(trainRepository.findTrainByDate(date));
        } catch (DateTimeParseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<?> createTrain(
            @ApiParam(name = "destination", value = "String representation of destination city", required = true) @RequestParam String destination,
            @ApiParam(name = "date", value = "String representation of date in format 'yyyy-MM-dd'", required = true) @RequestParam String dateString,
            @ApiParam(name = "capacity", value = "Capacity (tickets amount) of this train", required = true) @RequestParam Integer capacity) {
        try {
            LocalDate date = LocalDate.parse(dateString, dateTimeFormatter);
            return ResponseEntity.status(HttpStatus.OK).body(trainRepository.insert(destination, date, capacity));
        } catch (DateTimeParseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
