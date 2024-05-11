package Ennov.IT.test.service.impl;

import Ennov.IT.test.entites.Ticket;
import Ennov.IT.test.exeption.TicketNoTFoundException;
import Ennov.IT.test.repository.TicketRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

    @RunWith(MockitoJUnitRunner.class)
    public class TicketServiceImplTest {
        @InjectMocks
        private TicketServiceImpl ticketService;
        @Mock
        private TicketRepository ticketRepository;

        @Test
        public void testGetAllTickets_returnsEmptyList() {
            when(ticketRepository.findAll()).thenReturn(Collections.emptyList());

            List<Ticket> allTickets = ticketService.getAllTicket();

            assertThat(allTickets).isEmpty();
        }
        @Test
        public void testGetAllTickets_returnsTicketList() {
            Ticket ticket = Ticket.builder()
                    .id(1L)
                    .title("Titre du ticket")
                    .description("Description du ticket")
                    .build();
            when(ticketRepository.findAll()).thenReturn(Collections.singletonList(ticket));

            List<Ticket> allTickets = ticketService.getAllTicket();

            assertThat(allTickets).isNotEmpty()
                    .hasSize(1)
                    .containsExactly(ticket);
        }

        @Test
        public void testGetTicketById_returnsTicket() {
            Ticket ticket = Ticket.builder()
                    .id(1L)
                    .title("Titre du ticket")
                    .description("Description du ticket")
                    .build();
            when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

            Ticket result = ticketService.getTicketById(ticket.getId());

            assertThat(result).isEqualTo(ticket);
        }
        @Test
        public void testGetTicketById_throwsNotFoundException() {
            when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> ticketService.getTicketById(1L))
                    .isInstanceOf(TicketNoTFoundException.class)
                    .hasMessage("Ticket non trouver");
        }
        @Test
        public void testCreateTicket() {
            Ticket ticket = Ticket.builder()
                    .title("Titre du ticket")
                    .description("Description du ticket")
                    .build();
            ticketService.createTicket(ticket);
            verify(ticketRepository).save(ticket);
        }
        @Test
        public void testModifyTicketWithSuccess() {
            Long ticketId = 1L;
            Ticket ticket = Ticket.builder()
                    .id(ticketId)
                    .title("Titre du ticket")
                    .description("Description du ticket")
                    .build();
            Ticket updatedTicket = Ticket.builder()
                    .id(ticketId)
                    .title("Titre du ticket modifié")
                    .description("Description du ticket modifiée")
                    .build();
            when(ticketRepository.existsById(ticketId)).thenReturn(true);
            when(ticketRepository.save(ticket)).thenReturn(updatedTicket);

            Ticket result = ticketService.modifierTicket(ticketId, ticket);

            verify(ticketRepository).save(ticket);
            assertThat(result).isEqualTo(updatedTicket);
        }
        @Test
        public void testDeleteTicketWithSuccess() {
            Long ticketId = 1L;
            ticketService.deleteTicket(ticketId);
            verify(ticketRepository).deleteById(ticketId);
        }
        @Test
        public void testDeleteTicketFailure() {
            Long ticketId = 1L;
            doThrow(EmptyResultDataAccessException.class).when(ticketRepository).deleteById(ticketId);

            assertThatThrownBy(() -> ticketService.deleteTicket(ticketId))
                    .isInstanceOf(EmptyResultDataAccessException.class);

            verify(ticketRepository).deleteById(ticketId);
        }


    }


