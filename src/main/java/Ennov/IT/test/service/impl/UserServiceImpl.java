package Ennov.IT.test.service.impl;

import Ennov.IT.test.entites._User;
import Ennov.IT.test.exeption.UserNotFoundException;
import Ennov.IT.test.repository.UserRepository;
import Ennov.IT.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<_User> getAllUsers() {
        return userRepository.findAll();
    }

    public _User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("utilisateur non trouvé")
        );
    }

    public void createUser(_User user) {
        this.userRepository.save(user);
    }

    public _User modifyUser(Long id, _User user) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("utilisateur non trouvé");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

}
