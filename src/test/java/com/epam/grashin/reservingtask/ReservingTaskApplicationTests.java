package com.epam.grashin.reservingtask;

import com.epam.grashin.reservingtask.controller.TicketController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ReservingTaskApplicationTests {

    @Autowired
    TicketController ticketController;

    private ExecutorService executor
            = Executors.newFixedThreadPool(4);

    class testTicket implements Callable<ResponseEntity> {
        @Override
        public ResponseEntity<?> call() throws Exception {
            return ticketController.bookTicket(1L, 1L);
        }
    }

    @Test
    public void shouldExecuteSimultaneousBooking() {

        CompletionService<ResponseEntity> completionService =
                new ExecutorCompletionService<>(executor);
        int users = 100;

        for (int i = 0; i < users; i++) {
            completionService.submit(new testTicket());
        }

        List<HttpStatus> statuses = new ArrayList<>();
        boolean errors = false;

        while (statuses.size() < users && !errors) {
            try {
                Future<ResponseEntity> resultFuture = completionService.take(); //blocks if none available
                ResponseEntity result = resultFuture.get();
                statuses.add(result.getStatusCode());
            } catch (Exception e) {
                errors = true;
                log.error(e.getMessage());
            }
        }

        Assert.assertEquals(100, statuses.size());
        Assert.assertEquals(90, statuses.stream().filter(httpStatus -> httpStatus.value() == 406).count());
        Assert.assertEquals(10, statuses.stream().filter(httpStatus -> httpStatus.value() == 200).count());
    }

}
