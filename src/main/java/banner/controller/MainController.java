package banner.controller;

import banner.service.interfaces.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    BannerService bannerService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("eventName", "FIFA 2018");
        return "index2";
    }

    @GetMapping("/banners")
    public String banners(Model model) {
//        model.addAttribute("bannerList", bannerService.findAll());
        return "banners";
    }

    @GetMapping("/admin")
    public String admin() {
        return "index2";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}