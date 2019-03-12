package banner.service.interfaces;

import banner.dao.interfaces.BannerDao;
import banner.model.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService extends GenericService<Banner, Integer> {

    Banner createBanner(Banner banner, MultipartFile image);

    Banner updateBanner(Banner banner, MultipartFile image);

    boolean updateSorting(List<Banner> bannerList);

    boolean activityDelete(Integer id);
}
