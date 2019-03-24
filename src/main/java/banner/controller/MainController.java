package banner.controller;

import banner.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.HandlerMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;


@Controller
public class MainController {

    @Value("${spring_profile_active}")
    private String profile;
    @Value("${contextPath:}")
    private String contextPath;

    @GetMapping("/image/**")
    public ResponseEntity<byte[]> index(HttpServletRequest request) throws IOException {
        String restOfTheUrl = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        File imgPath = new File(restOfTheUrl.replaceFirst("/image", ""));
        byte[] image = Files.readAllBytes(imgPath.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping({"/error", "/", "/**/{path:[^\\.]+}"})
    public String banners(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        HashMap<Object, Object> data = new HashMap<>();
        data.put("profile", userDetails);
        model.addAttribute("contextPath", contextPath);
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}