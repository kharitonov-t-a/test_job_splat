package banner.service.interfaces;

import banner.model.Banner;
import banner.service.GenericService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService extends GenericService<Banner, Integer> {

    /**
     * @param banner
     * @param image
     * @return
     */
    Banner createBanner(Banner banner, MultipartFile image);

    /**
     * @param banner
     * @param image
     * @return
     */
    Banner updateBanner(Banner banner, MultipartFile image);

    /**
     * @param bannerList
     * @return
     */
    List<Banner> updateSorting(List<Banner> bannerList);

    /**
     * @param id
     * @return
     */
    boolean deleteBanner(Integer id);

}
