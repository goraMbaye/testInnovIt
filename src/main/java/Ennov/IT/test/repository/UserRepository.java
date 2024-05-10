package Ennov.IT.test.repository;



import Ennov.IT.test.entites._User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<_User,Long> {
}
