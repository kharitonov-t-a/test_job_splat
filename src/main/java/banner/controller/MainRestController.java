package banner.controller;

import banner.model.Banner;
import banner.service.interfaces.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("banner")
public class MainRestController {

    @Autowired
    BannerService bannerService;

    @GetMapping
    public List<Banner> list() {
        return bannerService.findAll();
    }

    @PostMapping
    public Banner create(@RequestBody Banner banner){
        return bannerService.create(banner);
    }

    @PutMapping("{id}")
    public Banner update(@PathVariable Integer id, @RequestBody Banner banner){
        banner.setId(id);
        return bannerService.update(banner);
    }

    @PutMapping
    public boolean updateSorting(@RequestBody List<Banner> bannerList){
        return bannerService.updateSorting(bannerList);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id){
        bannerService.delete(id);
    }

}
