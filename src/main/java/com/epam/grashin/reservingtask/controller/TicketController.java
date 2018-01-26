package com.epam.grashin.reservingtask.controller;

import com.epam.grashin.reservingtask.entity.Train;
import com.epam.grashin.reservingtask.entity.User;
import com.epam.grashin.reservingtask.repository.TicketRepository;
import com.epam.grashin.reservingtask.repository.TrainRepository;
import com.epam.grashin.reservingtask.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/book")
@Api(value="booking", description="Ticket booking for user and train")
public class TicketController {

    private static final String SUCCESS_MESSAGE="Ticket successfully booked for user %s and train to %s on %s";
    private static final String USER_NOT_FOUND="User not found";
    private static final String TRAIN_NOT_FOUND="Train not found";
    private static final String NO_TICKETS_LEFT="No tickets left for this train";
    private static final String TRAIN_HAS_GONE="This train was before current date";

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @ApiOperation(value = "Book a ticket for a train")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully booked a ticket for user"),
            @ApiResponse(code = 400, message = "Incorrect id of user or train"),
            @ApiResponse(code = 406, message = "Impossible to book ticket for this train because no tickets left"),
            @ApiResponse(code = 412, message = "Date of train is before current date")
    }
    )@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> bookTicket(@RequestParam Long userId, @RequestParam Long trainId) {
        User user = userRepository.findUserById(userId);
        if(user==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_NOT_FOUND);
        }
        Train train;
        synchronized (this){
            train = trainRepository.findTrainById(trainId);
            if(train==null ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TRAIN_NOT_FOUND);
            } else if(train.getTickets()<=0){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(NO_TICKETS_LEFT);
            } else if(!train.getDate().toLocalDate().isAfter(LocalDate.now())){
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(TRAIN_HAS_GONE);
            }
            else{
                trainRepository.updateTicketsAmountForTrain(train.getTickets()-1, trainId);
            }
        }
        ticketRepository.insert(userId,trainId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format(SUCCESS_MESSAGE, user.getName(), train.getDestination(), train.getDate().toLocalDate().toString()));
    }

}
