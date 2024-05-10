package Ennov.IT.test.repository;


import Ennov.IT.test.entites.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
