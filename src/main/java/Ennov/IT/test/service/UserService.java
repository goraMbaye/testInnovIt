package Ennov.IT.test.service;


import Ennov.IT.test.entites._User;
import Ennov.IT.test.exeption.UserNotFoundException;

import java.util.List;


public interface UserService {
    List<_User> getAllUsers() throws UserNotFoundException;
    _User getUserById(Long id);
    void createUser(_User user);
    _User modifyUser(Long id, _User user);
    void deleteUser(Long id);
}
