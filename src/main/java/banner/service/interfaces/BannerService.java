package banner.service.interfaces;

import banner.model.Banner;
import banner.service.GenericService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService extends GenericService<Banner, Integer> {

    Banner createBanner(Banner banner, MultipartFile image);

    Banner updateBanner(Banner banner, MultipartFile image);

    boolean updateSorting(List<Banner> bannerList);

}
