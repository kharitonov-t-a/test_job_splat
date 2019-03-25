package banner.dao.interfaces;

import banner.dao.GenericDao;
import banner.model.Banner;

public interface BannerDao extends GenericDao<Banner, Integer> {

    /**
     * @return
     */
    Integer getMaxPriority();

    /**
     * @param id
     * @return
     */
    String getBannerImage(Integer id);

    /**
     * @param banner
     */
    void updatePriority(Banner banner);

    /**
     * @param localeId
     * @param newActivityState
     */
    void switchActivityByLocale(Integer localeId, boolean newActivityState);

    /**
     *
     */
    void cleanBanners();
}
