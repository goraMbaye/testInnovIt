package Ennov.IT.test.controller;



import Ennov.IT.test.entites._User;
import Ennov.IT.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<Object> getAllUser() {
        try {
            List<_User> users = this.userService.getAllUsers();
            if (users != null && !users.isEmpty()) {
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun utilisateur trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur inattendue s'est produite lors de la récupération des utilisateurs");
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<_User> getUserById(@PathVariable Long id) {
                return ResponseEntity.ok( userService.getUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody _User user) {
        try {
            this.userService.createUser(user);
            return ResponseEntity.ok("Utilisateur créé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la création de l'utilisateur");
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<_User> modifyUser(@PathVariable Long id, @RequestBody _User user) {
        return  new ResponseEntity<>(userService.modifyUser(id, user),HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            this.userService.deleteUser(id);
            return ResponseEntity.ok("Utilisateur supprimé avec succès");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé pour l'ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la suppression de l'utilisateur");
        }
    }
}
