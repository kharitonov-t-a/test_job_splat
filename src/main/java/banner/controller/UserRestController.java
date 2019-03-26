package banner.controller;

import banner.model.User;
import banner.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("userValidator") // spring validator
    private Validator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    /**
     * Get all users
     * @return list user
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @GetMapping("list")
    public List<User> list() {
        return userService.findAllWithoutPassword();
    }

    /**
     * Create new user
     * @param user model user
     * @param bindingResult validate result
     * @return user instance
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders responseHeader = new HttpHeaders();
        User resultUser = userService.create(user);
        return new ResponseEntity<>(resultUser, responseHeader, HttpStatus.CREATED);
    }

    /**
     * Update exists user
     * @param id user id
     * @param user new user fields
     * @param bindingResult validate result
     * @return updated user instance
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody User user,
                                       BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders responseHeader = new HttpHeaders();
        user.setId(id);
        User resultUser = userService.update(user);
        return new ResponseEntity<>(resultUser, responseHeader, HttpStatus.OK);
    }

    /**
     * Delete exists user
     * @param id user id
     * @return empty ResponseEntity
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Change activity exists user
     * @param id user id
     * @param newActivityState new state false or true
     * @return true if locale changed activity successfully
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PutMapping("delete/{id}")
    public boolean switchActivity(@PathVariable Integer id, @RequestParam(value = "newActivityState") boolean newActivityState){
        return userService.switchActivity(id, newActivityState);
    }

    /**
     * Get information about current logged in user
     * @param userDetails information about user (name, role, etc.)
     * @return information about user (name, role, etc.)
     */
    @GetMapping
    public UserDetails getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }
}
