package banner.controller;

import banner.model.Locale;
import banner.service.interfaces.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("list")
    public List<Locale> list() {
        return localeService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Locale locale){
        HttpHeaders responseHeader = new HttpHeaders();
        Locale resultLocale = localeService.create(locale);
        return new ResponseEntity<>(resultLocale, responseHeader, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Locale locale){
        HttpHeaders responseHeader = new HttpHeaders();
        locale.setId(id);
        Locale resultLocale = localeService.update(locale);
        return new ResponseEntity<>(resultLocale, responseHeader, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        localeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("delete/{id}")
    public boolean disable(@PathVariable Integer id, @RequestParam(value = "newActivityState") boolean newActivityState){
        return localeService.switchActivity(id, newActivityState);
    }
}
