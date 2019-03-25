package banner.controller;

import banner.model.Locale;
import banner.service.interfaces.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("locale")
public class LocaleRestController {

    @Autowired
    LocaleService localeService;

    @Autowired
    @Qualifier("localeValidator") // spring validator
    private Validator localeValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(localeValidator);
    }

    /**
     * @return
     */
    @GetMapping("list")
    public List<?> list() {
        return localeService.findAll();
    }

    /**
     * @param locale
     * @param bindingResult
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Locale locale,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders responseHeader = new HttpHeaders();
        Locale resultLocale = localeService.create(locale);
        return new ResponseEntity<>(resultLocale, responseHeader, HttpStatus.OK);
    }

    /**
     * @param id
     * @param locale
     * @param bindingResult
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Locale locale,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders responseHeader = new HttpHeaders();
        locale.setId(id);
        Locale resultLocale = localeService.update(locale);
        return new ResponseEntity<>(resultLocale, responseHeader, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        localeService.delete(id);
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
        return localeService.switchActivity(id, newActivityState);
    }
}
