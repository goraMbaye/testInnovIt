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
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void getAllUsers_retourneListeVide() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<_User> allUsers = userService.getAllUsers();

        Assertions.assertThat(allUsers).isEmpty();
    }

    @Test
    public void getAllUsers_retourneListUsre() {
        _User user = _User.builder()
                .id(7L)
                .username("gora")
                .email("gora.mbaye@gmail.com")
                .build();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));


        List<_User> allUsers = userService.getAllUsers();

        Assertions.assertThat(allUsers).isNotEmpty()
                .hasSize(1)
                .extracting(_User::getId, _User::getEmail, _User::getUsername)
                .containsExactly(Tuple.tuple(7L, "gora.mbaye@gmail.com", "gora"));

    }

    @Test
    public void finUserById_retouneException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("utilisateur non trouvé");
    }

    @Test
    public void findUserById() {
        _User user = _User.builder()
                .id(7L)
                .username("gora")
                .email("gora.mbaye@gmail.com")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        _User result = userService.getUserById(user.getId());

        Assertions.assertThat(result).isEqualTo(user);
    }

    @Test
    public void soudSaveUserWithSucess() {
        _User user = _User.builder()
                .id(9L)
                .username("gora")
                .email("gora.mbaye@gmail.com")
                .build();
        when(userRepository.save(user)).thenReturn(user);

        userService.createUser(user);

        verify(userRepository).save(user);
    }

    @Test
    public void testSaveUserWithInvalidData() {
        _User user = _User.builder()
                .id(9L)
                .username("gora")
                .email("gora.mbaye@gmail.com")
                .build();
        when(userRepository.save(user)).thenReturn(null);

        userService.createUser(user);

        verify(userRepository).save(user);
    }

    @Test
    public void testModifyUserWithSuccess() {
        Long userId = 9L;
        _User user = _User.builder()
                .id(userId)
                .username("gora")
                .email("gora.mbaye@gmail.com")
                .build();
        _User updatedUser = _User.builder()
                .id(userId)
                .username("gora_updated")
                .email("gora.mbaye.updated@gmail.com")
                .build();
        when(userRepository.existsById(userId)).thenReturn(true);

        when(userRepository.save(user)).thenReturn(updatedUser);

        _User result = userService.modifyUser(userId, user);

        verify(userRepository).save(user);

        assertThat(result).isNotNull()
                .isEqualTo(updatedUser);
    }

    @Test
    public void testModifyUserFailureUserNotFound() {
        // ID de l'utilisateur non existant
        Long userId = 9L;

        // Création de l'utilisateur de test
        _User user = _User.builder()
                .id(userId)
                .username("gora")
                .email("gora.mbaye@gmail.com")
                .build();


        when(userRepository.existsById(userId)).thenReturn(false);

        // Appel de la méthode à tester
        try {
            userService.modifyUser(userId, user);
            // Si la méthode ne lance pas d'exception, le test échoue
            fail("Une exception aurait dû être lancée");
        } catch (Exception ex) {

            assertThat(ex).isInstanceOf(UserNotFoundException.class);
        }

        // Vérifier que la méthode save n'a pas été appelée
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testDeleteUserWithSuccess() {
        Long userId = 9L;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }


    @Test
    public void testDeleteUserFailure() {
        Long userId = 9L;
        doThrow(EmptyResultDataAccessException.class).when(userRepository).deleteById(userId);

        try {
            userService.deleteUser(userId);
            fail("Une exception aurait dû être lancée");
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(EmptyResultDataAccessException.class);
        }
        verify(userRepository).deleteById(userId);
    }


}

