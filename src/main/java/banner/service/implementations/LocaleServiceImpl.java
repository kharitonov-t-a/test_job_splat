package banner.service.implementations;

import banner.dao.interfaces.BannerDao;
import banner.dao.interfaces.LocaleDao;
import banner.model.Locale;
import banner.service.GenericServiceImpl;
import banner.service.interfaces.BannerService;
import banner.service.interfaces.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocaleServiceImpl extends GenericServiceImpl<Locale, Integer, LocaleDao> implements LocaleService {

    @Autowired
    BannerDao bannerDao;

    /**
     * delete banner if locale deleted
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(Integer id) {
        dao.delete(id);
        bannerDao.cleanBanners();
    }

    /**
     * switch also activity in banners if activity change to false
     * @param id
     * @param newActivityState
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean switchActivity(Integer id, boolean newActivityState) {
        if(!newActivityState)
            bannerDao.switchActivityByLocale(id, false);
        return dao.switchActivity(id, newActivityState);
    }
}
