package banner.service.interfaces;

import banner.dao.interfaces.BannerDao;
import banner.model.Banner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface BannerService extends GenericService<Banner, Integer> {
    boolean updateSorting(List<Banner> bannerList);
}
