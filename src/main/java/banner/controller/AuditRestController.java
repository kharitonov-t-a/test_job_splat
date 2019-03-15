package banner.controller;

import banner.model.Audit;
import banner.model.User;
import banner.service.interfaces.AuditService;
import banner.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("audit")
public class AuditRestController {

    @Autowired
    AuditService auditService;

    @GetMapping("list")
    public List<Audit> listAll() {
        return auditService.findAll();
    }

    @GetMapping("list/user/{id}")
    public List<Audit> listByUser(@PathVariable Integer id) {
        return auditService.findByUser(id);
    }

    @GetMapping("list/banner/{id}")
    public List<Audit> listByBanner(@PathVariable Integer id) {
        return auditService.findByBanner(id);
    }



}
