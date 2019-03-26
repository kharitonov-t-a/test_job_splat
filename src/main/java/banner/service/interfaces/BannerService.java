package banner.service.interfaces;

import banner.model.Banner;
import banner.service.GenericService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService extends GenericService<Banner, Integer> {

    /**
     * Create new banner
     * @param banner model banner without id
     * @param image will create file on hard drive
     * @return model banner with id and imgSrc
     */
    Banner createBanner(Banner banner, MultipartFile image);

    /**
     * Update banner
     * @param banner model banner with id
     * @param image will create file on hard drive
     * @return model banner
     */
    Banner updateBanner(Banner banner, MultipartFile image);

    /**
     * Update priority of banners
     * @param bannerList banner list with new priority value
     * @return bannerList
     */
    List<Banner> updateSorting(List<Banner> bannerList);

    /**
     * Delete exists banner
     * @param id banner id
     * @return true, if all successfully
     */
    boolean deleteBanner(Integer id);

}
