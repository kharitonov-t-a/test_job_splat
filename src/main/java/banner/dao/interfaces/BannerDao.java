package banner.dao.interfaces;

import banner.model.Banner;

import java.util.List;

public interface BannerDao extends GenericDao<Banner, Integer> {
    void updatePriority(Banner banner);
}
