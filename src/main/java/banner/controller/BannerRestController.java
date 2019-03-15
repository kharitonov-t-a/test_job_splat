package banner.controller;

import banner.model.Banner;
import banner.service.interfaces.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("banner")
public class BannerRestController {

    @Autowired
    BannerService bannerService;

    @GetMapping("list")
    public List<Banner> list() {
        return bannerService.findAll();
    }

    @PostMapping
    public ResponseEntity<Banner> create(@ModelAttribute Banner banner, @RequestParam(value = "image", required = false) MultipartFile image){
        Banner resultBanner = bannerService.createBanner(banner, image);
        if(resultBanner == null)
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        else
            return new ResponseEntity<>(resultBanner, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Banner> update(@PathVariable Integer id, @ModelAttribute Banner banner, @RequestParam(value = "image", required = false) MultipartFile image){
        banner.setId(id);
        Banner resultBanner = bannerService.updateBanner(banner, image);
        if(resultBanner == null)
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        else
            return new ResponseEntity<>(resultBanner,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateSorting(@RequestBody List<Banner> bannerList){
        if(bannerService.updateSorting(bannerList) == null)
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        bannerService.deleteBanner(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("delete/{id}")
    public boolean disable(@PathVariable Integer id, @RequestParam(value = "newActivityState") boolean newActivityState){
        return bannerService.switchActivity(id, newActivityState);
    }

}
