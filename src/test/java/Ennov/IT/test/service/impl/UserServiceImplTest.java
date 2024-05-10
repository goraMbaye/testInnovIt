package Ennov.IT.test.service.impl;

import Ennov.IT.test.entites._User;
import Ennov.IT.test.exeption.UserNotFoundException;
import Ennov.IT.test.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
@InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Test
    public void getAllUsers_retourneListeVide(){
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<_User> allUsers = userService.getAllUsers();

        Assertions.assertThat(allUsers).isEmpty();
    }
    @Test
    public void getAllUsers_retourneListUsre(){
        _User user = _User.builder()
                .id(7L)
                .username("gora")
                .email("gora.mbaye@gmail.com")
                .build();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<_User> allUsers = userService.getAllUsers();

        Assertions.assertThat(allUsers).isNotEmpty()
                .hasSize(1)
                .extracting(_User::getId,_User::getEmail,_User::getUsername)
                .containsExactly(Tuple.tuple(7L,"gora.mbaye@gmail.com","gora"));

    }

    @Test
    public void finUserById_retouneException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()->userService.getUserById(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("utilisateur non trouvé");
    }

    @Test
    public void findUserById(){
        _User user = _User.builder()
                .id(7L)
                .username("gora")
                .email("gora.mbaye@gmail.com")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        _User result = userService.getUserById(user.getId());

        Assertions.assertThat(result).isEqualTo(user);
    }
}