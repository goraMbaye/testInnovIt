package Ennov.IT.test.entites;

import Ennov.IT.test.enumm.TicketStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor @AllArgsConstructor @ToString  @Getter
@Setter @Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @ManyToOne
    private _User user;
}
