package banner.controller;

import banner.model.User;
import banner.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserRestController {

    @Autowired
    UserService userService;

    @GetMapping("list")
    public List<User> list() {
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        User resultUser = userService.create(user);
        if(resultUser == null)
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        else
            return new ResponseEntity<>(resultUser, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User user){
        user.setId(id);
        User resultUser = userService.update(user);
        if(resultUser == null)
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        else
            return new ResponseEntity<>(resultUser, HttpStatus.OK);
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
}
