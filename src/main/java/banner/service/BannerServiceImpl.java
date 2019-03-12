package banner.service;

import banner.dao.interfaces.BannerDao;
import banner.model.Banner;
import banner.service.interfaces.BannerService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BannerServiceImpl implements BannerService {

    @Value("${upload_path}")
    private String pathImage;

    @Autowired
    BannerDao bannerDao;

    @Override
    public List<Banner> findAll() {
        return bannerDao.findAll();
    }

    @Override
    public Banner findById(Integer id) {
        return null;
    }

    @Override
    public Banner createBanner(Banner banner, MultipartFile image) {
        if (image!=null && !image.isEmpty()) {
            saveNewBannerImage(banner, image);
            banner.setPriority(bannerDao.getMaxPriority() + 1);
            return create(banner);
        }
        return null;
    }

    @Override
    public Banner create(Banner banner) {
        return bannerDao.create(banner);
    }


    @Override
    public Banner updateBanner(Banner banner, MultipartFile image) {
        if (image!=null && !image.isEmpty()) {
            deleteOldBannerImage(banner.getId());
            saveNewBannerImage(banner, image);
        }
        return update(banner);
    }

    @Override
    public Banner update(Banner banner) {
        return bannerDao.update(banner);
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

    @Override
    public void delete(Integer id) {
        bannerDao.delete(id);
        deleteOldBannerImage(id);
    }

    @Override
    public boolean activityDelete(Integer id) {
        return bannerDao.disableActivity(id);
    }

    private boolean deleteOldBannerImage(Integer id) {
        String bannerImage = bannerDao.getBannerImage(id);
        File dirFile = new File(bannerImage);
        return dirFile.delete();
    }

    private boolean saveNewBannerImage(Banner banner, MultipartFile image) {
        String rootPath = System.getProperty("catalina.home");

        String uuidFile = UUID.randomUUID().toString();
        String fileName = uuidFile + "_" + image.getOriginalFilename();
        File dirFile = new File(rootPath + File.separator + "images");
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }

        dirFile = getTreeDirFile(getTreeDirFile(dirFile));

        dirFile = new File(dirFile, fileName);
        try {
            image.transferTo(dirFile);
            banner.setImgSrc(dirFile.getPath());
            // clean metadata
            BufferedImage bufferedImage = ImageIO.read(dirFile);
            ImageIO.write(bufferedImage, getFileExtension(dirFile), dirFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private File getTreeDirFile(File dirFile) {
        int random;
        String dirTree;
        random = (int) (Math.random() * 15);
        dirTree = DigestUtils.md5Hex(String.valueOf(new Date().getTime())).substring(random, random + 2);
        dirFile = new File(dirFile, dirTree);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return dirFile;
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".")+1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }
}
