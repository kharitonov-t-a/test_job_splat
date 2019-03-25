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

    @GetMapping("list")
    public List<User> list() {
        return userService.findAllWithoutPassword();
    }

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

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("delete/{id}")
    public boolean disable(@PathVariable Integer id, @RequestParam(value = "newActivityState") boolean newActivityState){
        return userService.switchActivity(id, newActivityState);
    }

    @GetMapping
    public UserDetails banners(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

}
