package Ennov.IT.test.service.impl;

import Ennov.IT.test.entites.Ticket;
import Ennov.IT.test.exeption.TicketNoTFoundException;
import Ennov.IT.test.exeption.TicketServiceException;
import Ennov.IT.test.repository.TicketRepository;
import Ennov.IT.test.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    public List<Ticket> getAllTicket() {
        try {
            return this.ticketRepository.findAll();
        } catch (Exception ex) {
            throw new TicketServiceException("Une erreur s'est produite lors de la récupération des tickets", ex);
        }
    }

    public Ticket getTicketById(Long id) {
        return this.ticketRepository.findById(id).orElseThrow(
                () -> new TicketNoTFoundException("Ticket non trouver")
        );
    }

    public void createTicket(Ticket ticket) {
        this.ticketRepository.save(ticket);
    }

    public Ticket modifierTicket(Long id, Ticket ticket) {
        if (!ticketRepository.existsById(id)) {
            throw new TicketNoTFoundException("icket non trouver");
        }
        ticket.setId(id);
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        this.ticketRepository.deleteById(id);
    }

}
