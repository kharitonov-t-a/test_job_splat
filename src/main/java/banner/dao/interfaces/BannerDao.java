package banner.dao.interfaces;

import banner.dao.GenericDao;
import banner.model.Banner;

public interface BannerDao extends GenericDao<Banner, Integer> {

    Integer getMaxPriority();

    String getBannerImage(Integer id);

    void updatePriority(Banner banner);

    void switchActivityByLocale(Integer localeId, boolean newActivityState);

    void cleanBanners();
}
