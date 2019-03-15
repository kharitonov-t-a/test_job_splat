package banner.controller;

import banner.model.Locale;
import banner.service.interfaces.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("locale")
public class LocaleRestController {

    @Autowired
    LocaleService localeService;

    @GetMapping("list")
    public List<Locale> list() {
        return localeService.findAll();
    }

    @PostMapping
    public ResponseEntity<Locale> create(@RequestBody Locale locale){
        Locale resultLocale = localeService.create(locale);
        if(resultLocale == null)
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        else
            return new ResponseEntity<>(resultLocale, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Locale> update(@PathVariable Integer id, @RequestBody Locale locale){
        locale.setId(id);
        Locale resultLocale = localeService.update(locale);
        if(resultLocale == null)
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        else
            return new ResponseEntity<>(resultLocale, HttpStatus.OK);
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
