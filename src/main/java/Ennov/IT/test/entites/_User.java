package Ennov.IT.test.entites;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor @AllArgsConstructor @ToString  @Getter @Setter @Builder
public class _User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
   /* @OneToMany
    @JoinColumn(name = "user_id")
    private List<Ticket> tickets;*/
}
