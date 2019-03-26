package banner.dao.interfaces;

import banner.dao.GenericDao;
import banner.model.Banner;

public interface BannerDao extends GenericDao<Banner, Integer> {

    /**
     * Get max priority among banners
     * @return max priority in DB
     */
    Integer getMaxPriority();

    /**
     * Get imgSrc by banner id
     * @param id banner id
     * @return imgSrc
     */
    String getBannerImage(Integer id);

    /**
     * Set new priority value for banner
     * @param banner banner with new priority value
     */
    void updatePriority(Banner banner);

    /**
     * Change activity state in banners by locale id
     * @param localeId locale id
     * @param newActivityState new activity value
     */
    void switchActivityByLocale(Integer localeId, boolean newActivityState);

    /**
     * Delete banners from BD if locale was deleted
     */
    void cleanBanners();
}
