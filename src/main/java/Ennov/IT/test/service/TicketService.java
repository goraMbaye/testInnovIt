package Ennov.IT.test.service;


import Ennov.IT.test.entites.Ticket;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {

    List<Ticket> getAllTicket();

    Ticket getTicketById(Long id);

    void createTicket(Ticket ticket);

    Ticket modifierTicket(Long id, Ticket ticket);

    void deleteTicket(Long id);

}
