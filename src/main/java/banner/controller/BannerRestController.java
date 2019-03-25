package banner.controller;

import banner.model.Banner;
import banner.service.interfaces.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("banner")
public class BannerRestController {

    @Autowired
    BannerService bannerService;

    @Autowired
    @Qualifier("bannerValidator") // spring validator
    private Validator bannerValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(bannerValidator);
    }

    /**
     * @return
     */
    @GetMapping("list")
    public List<Banner> list() {
        return bannerService.findAll();
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("preview/{id}")
    public Banner findById(@PathVariable Integer id) {
        return bannerService.findById(id);
    }

    /**
     * @param banner
     * @param bindingResult
     * @param image
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PostMapping
    public ResponseEntity<?> create(@Valid @ModelAttribute Banner banner,
                                    BindingResult bindingResult, @RequestParam(value = "image", required = false) MultipartFile image){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders responseHeader = new HttpHeaders();
        Banner resultBanner = bannerService.createBanner(banner, image);
        return new ResponseEntity<>(resultBanner, responseHeader, HttpStatus.CREATED);
    }

    /**
     * @param id
     * @param banner
     * @param bindingResult
     * @param image
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @ModelAttribute Banner banner,
                                    BindingResult bindingResult, @RequestParam(value = "image", required = false) MultipartFile image){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders responseHeader = new HttpHeaders();
        banner.setId(id);
        Banner resultBanner = bannerService.updateBanner(banner, image);
        return new ResponseEntity<>(resultBanner, responseHeader, HttpStatus.OK);
    }

    /**
     * @param bannerList
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PutMapping
    public ResponseEntity<?> updateSorting(@RequestBody List<Banner> bannerList){
        HttpHeaders responseHeader = new HttpHeaders();
        bannerService.updateSorting(bannerList);
        return new ResponseEntity(responseHeader, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        bannerService.deleteBanner(id);
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
        return bannerService.switchActivity(id, newActivityState);
    }

}
