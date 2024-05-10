package Ennov.IT.test.controller;


import Ennov.IT.test.entites.Ticket;
import Ennov.IT.test.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @GetMapping("/tickets")

    public ResponseEntity<List<Ticket>> getAllTicket() {
            return ResponseEntity.ok(ticketService.getAllTicket());
    }


    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(this.ticketService.getTicketById(id));
    }



    @PostMapping("/tickets")
    public ResponseEntity<String> creerTicket(@RequestBody Ticket ticket) {
        try {
            this.ticketService.createTicket(ticket);
            return ResponseEntity.ok("Ticket créé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la création du ticket");
        }
    }


    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> modifierTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        return new ResponseEntity<> (ticketService.modifierTicket(id, ticket),HttpStatus.OK);


    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        try {
            this.ticketService.deleteTicket(id);
            return ResponseEntity.ok("Ticket supprimé avec succès");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket non trouvé pour l'ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la suppression du ticket");
        }
    }

}
