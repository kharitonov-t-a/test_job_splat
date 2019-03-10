package banner.service;

import banner.dao.interfaces.BannerDao;
import banner.model.Banner;
import banner.service.interfaces.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public boolean updateSorting(List<Banner> bannerList) {
        List<Banner> bannerBDList = bannerDao.findAll();
        bannerList = bannerList.stream().filter(banner ->
                bannerBDList.stream().allMatch(bannerBD ->
                        !(banner.getId() == bannerBD.getId() && banner.getPriority() == bannerBD.getPriority()))).collect(Collectors.toList());
        bannerList.forEach(banner -> bannerDao.updatePriority(banner));
        return true;
    }
}
