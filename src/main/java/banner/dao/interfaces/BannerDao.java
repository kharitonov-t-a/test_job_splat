package banner.dao.interfaces;

import banner.dao.GenericDao;
import banner.model.Banner;

public interface BannerDao extends GenericDao<Banner, Integer> {

    Integer getMaxPriority();

    void updatePriority(Banner banner);

    String getBannerImage(Integer id);

}
