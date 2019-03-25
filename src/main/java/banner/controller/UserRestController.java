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
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @GetMapping("list")
    public List<User> list() {
        return userService.findAllWithoutPassword();
    }

    /**
     * @param user
     * @param bindingResult
     * @return
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
     * @param id
     * @param user
     * @param bindingResult
     * @return
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
     * @param id
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @param id
     * @param newActivityState
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PutMapping("delete/{id}")
    public boolean disable(@PathVariable Integer id, @RequestParam(value = "newActivityState") boolean newActivityState){
        return userService.switchActivity(id, newActivityState);
    }

    /**
     * @param userDetails
     * @return
     */
    @GetMapping
    public UserDetails getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }
}
