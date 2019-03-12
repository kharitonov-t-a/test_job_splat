package banner.dao.interfaces;

import banner.model.Banner;

public interface BannerDao extends GenericDao<Banner, Integer> {

    Integer getMaxPriority();

    void updatePriority(Banner banner);

    String getBannerImage(Integer id);

    boolean disableActivity(Integer id);
}
