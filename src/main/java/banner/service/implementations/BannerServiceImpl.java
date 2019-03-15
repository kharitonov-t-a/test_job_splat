package banner.service.implementations;

import banner.annotation.*;
import banner.dao.interfaces.BannerDao;
import banner.model.Banner;
import banner.service.GenericServiceImpl;
import banner.service.interfaces.BannerService;
import org.apache.commons.codec.digest.DigestUtils;
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
public class BannerServiceImpl extends GenericServiceImpl<Banner, Integer, BannerDao> implements BannerService {

    @Value("${upload_path}")
    private String pathImage;

    @AuditCreate
    @Override
    public Banner createBanner(Banner banner, MultipartFile image) {
        if (image!=null && !image.isEmpty()) {
            saveNewBannerImage(banner, image);
            Integer priority = dao.getMaxPriority();
            if(priority == null)
                priority = 0;
            banner.setPriority(priority + 1);
            return dao.create(banner);
        }
        return null;
    }

    @AuditUpdate
    @Override
    public Banner updateBanner(Banner banner, MultipartFile image) {
        if (image!=null && !image.isEmpty()) {
            deleteOldBannerImage(banner.getId());
            saveNewBannerImage(banner, image);
        }
        return dao.update(banner);
    }

    //BATCH!!!!!!!!!!!!!
    @AuditSort
    @Override
    public List<Banner> updateSorting(List<Banner> bannerList) {
        List<Banner> bannerBDList = dao.findAll();
        bannerList = bannerList.stream().filter(banner ->
                bannerBDList.stream().allMatch(bannerBD ->
                        !(banner.getId() == bannerBD.getId() && banner.getPriority() == bannerBD.getPriority()))).collect(Collectors.toList());
        bannerList.forEach(banner -> dao.updatePriority(banner));
        return bannerList;
    }

    @AuditDelete
    @Override
    public boolean deleteBanner(Integer id) {
        deleteOldBannerImage(id);
        dao.delete(id);
        return true;
    }

    private boolean deleteOldBannerImage(Integer id) {
        String bannerImage = dao.getBannerImage(id);
        File dirFile = new File(bannerImage);
        return dirFile.delete();
    }

    @AuditSwitchActivity
    @Override
    public boolean switchActivity(Integer id, boolean newActivityState) {
        return dao.switchActivity(id, newActivityState);
    }

    @Override
    public List<Banner> findAll() {
        return dao.findAll();
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
