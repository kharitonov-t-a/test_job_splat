package banner.service;

import banner.dao.interfaces.BannerDao;
import banner.model.Banner;
import banner.service.interfaces.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerDao bannerDao;

    @Override
    public Banner create(Banner banner) {
        return bannerDao.create(banner);
    }

    @Override
    public Banner update(Banner banner) {
        return bannerDao.update(banner);
    }

    @Override
    public void delete(Integer id) {
        bannerDao.delete(id);
    }

    @Override
    public List<Banner> findAll() {
        return bannerDao.findAll();
    }
}
