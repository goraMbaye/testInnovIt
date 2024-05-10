package Ennov.IT.test;

import Ennov.IT.test.entites.Ticket;
import Ennov.IT.test.entites._User;
import Ennov.IT.test.enumm.TicketStatus;
import Ennov.IT.test.repository.TicketRepository;
import Ennov.IT.test.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(TicketRepository ticketRepository, UserRepository userRepository){
		return args -> {
			_User user1 = _User.builder()
					.username("gora mbaye")
					.email("gora.mbaye.dev@gmail.com")
					.build();
			userRepository.save(user1);

			_User user2 = _User.builder()
					.username("jean gomis")
					.email("jeangomis@gmail.com")
					.build();
			userRepository.save(user2);

			_User user3 = _User.builder()
					.username("tapha Mbaye")
					.email("tapha.mbaye@gmail.com")
					.build();
			userRepository.save(user3);

			userRepository.findAll().forEach(user -> {
				for (int i = 0; i < 3; i++) {
					Ticket ticket = Ticket.builder()
							.title("titre")
							.description("description")
							.status(TicketStatus.ENCOUR)
							.user(user)
							.build();
					ticketRepository.save(ticket);
				}
			});
		};
	}

}
