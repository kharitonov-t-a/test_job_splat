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
     * Get all banners
     * @return list banner instances
     */
    @GetMapping("list")
    public List<Banner> list() {
        return bannerService.findAll();
    }

    /**
     * Get banner by id
     * @param id
     * @return banner instance
     */
    @GetMapping("preview/{id}")
    public Banner findById(@PathVariable Integer id) {
        return bannerService.findById(id);
    }

    /**
     * Create new banner
     * @param banner model banner
     * @param bindingResult validate result
     * @param image for create new banner (MultipartFile)
     * @return created banner instance
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
     * Update exists banner
     * @param id banner id
     * @param banner new banner fields
     * @param bindingResult validate result
     * @param image new image banner (not required)
     * @return updated banner instance
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
     * Update priorities of banner list
     * @param bannerList list banner instances
     * @return empty ResponseEntity
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PutMapping
    public ResponseEntity<?> updateSorting(@RequestBody List<Banner> bannerList){
        HttpHeaders responseHeader = new HttpHeaders();
        bannerService.updateSorting(bannerList);
        return new ResponseEntity(responseHeader, HttpStatus.OK);
    }

    /**
     * Delete exists banner
     * @param id banner id
     * @return empty ResponseEntity
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        bannerService.deleteBanner(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Change activity exists banner
     * @param id banner id
     * @param newActivityState false or true
     * @return true if banner disabled successfully
     */
    @RolesAllowed(value={"ROLE_ADMIN", "ROLE_MANAGER"})
    @PutMapping("delete/{id}")
    public boolean switchActivity(@PathVariable Integer id, @RequestParam(value = "newActivityState") boolean newActivityState){
        return bannerService.switchActivity(id, newActivityState);
    }

}
