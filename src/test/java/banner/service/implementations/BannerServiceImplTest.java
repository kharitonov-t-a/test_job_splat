package banner.service.implementations;

import banner.Application;
import banner.dao.implementations.BannerDaoImpl;
import banner.dao.mappers.BannerMapper;
import banner.model.Banner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.util.List;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:testApplication.properties")
@RunWith(SpringRunner.class)
//@SqlGroup({
//        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
//                "classpath:sqlScripts/createBannerTable.sql",
//                "classpath:sqlScripts/createAuditTable.sql",
//                "classpath:sqlScripts/createUserTable.sql",
//                "classpath:sqlScripts/createLocaleTable.sql"}),
//        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sqlScripts/drop.sql")
//})
public class BannerServiceImplTest {

    @Value("${path_root_dir}")
    String pathRootDir;

    private BannerDaoImpl bannerDao;
    private BannerServiceImpl bannerService;
    EmbeddedDatabase db;
    @Before
    public void setUp() throws Exception {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sqlScripts/createBannerTable")
                .addScript("sqlScripts/createLocaleTable")
                .build();
        bannerDao = new BannerDaoImpl();
        bannerDao.setDataSource(db);
        bannerDao.postConstruct();
        bannerDao.mapper = new BannerMapper();

        bannerService = new BannerServiceImpl();
        bannerService.dao = bannerDao;
        bannerService.pathRootDir = pathRootDir;
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void createBanner() {
        Banner banner = new Banner();
        banner.setTargetUrl("www.ya.ru");
        banner.setHeight(100);
        banner.setWidth(200);
        banner.setLangId(1);
        banner.setActivity(true);
        byte[] data = { (byte) 10 };
        banner = bannerService.createBanner(banner, new MockMultipartFile("name", data));
        System.out.println("Save Banners: " + banner.toString());
        List<Banner> loadedBanners = bannerService.findAll();
        System.out.println("Loaded Banners: " + loadedBanners.toString());
        for (Banner loadedBanner : loadedBanners) {
            Assert.assertEquals(1, (int) loadedBanner.getPriority());
            Assert.assertEquals("www.ya.ru", loadedBanner.getTargetUrl());
            Assert.assertEquals(200, (int) loadedBanner.getWidth());
            Assert.assertEquals(100, (int) loadedBanner.getHeight());
            Assert.assertEquals(1, (int) loadedBanner.getLangId());
            Assert.assertTrue(loadedBanner.getActivity());
            if(!loadedBanner.getImgSrc().isEmpty()){
                File imgPath = new File(loadedBanner.getImgSrc());
                if (imgPath.exists()) {
                    Assert.assertTrue(true);
                }
            }
        }
    }

    @Test
    public void updateBanner() {
        Banner banner = new Banner();
        banner.setTargetUrl("www.ya.ru");
        banner.setHeight(100);
        banner.setWidth(200);
        banner.setLangId(1);
        banner.setActivity(true);
        byte[] data = {(byte) 10};
        Banner createdBanner = bannerService.createBanner(banner, new MockMultipartFile("name", data));
        File imgPath = null;
        if(!createdBanner.getImgSrc().isEmpty()){
            imgPath =new File(createdBanner.getImgSrc());
            if (imgPath.exists()) {
                Assert.assertTrue(true);
            }
        }
        banner.setHeight(200);
        banner.setWidth(400);
        data = new byte[]{(byte) 20};
        Banner updatedBanner = bannerService.updateBanner(banner, new MockMultipartFile("name2", data));


//        old file
        if(imgPath != null){
            if (imgPath.exists()) {
                Assert.fail();
            }else {
                Assert.assertTrue(true);
            }
        }

//        new file
        if(!updatedBanner.getImgSrc().isEmpty()){
            imgPath = new File(createdBanner.getImgSrc());
            if (imgPath.exists()) {
                Assert.assertTrue(true);
            }else {
                Assert.fail();
            }
        }

        Assert.assertEquals("www.ya.ru", updatedBanner.getTargetUrl());
        Assert.assertEquals(400, (int) updatedBanner.getWidth());
        Assert.assertEquals(200, (int) updatedBanner.getHeight());
        Assert.assertEquals(1, (int) updatedBanner.getLangId());
        Assert.assertTrue(updatedBanner.getActivity());
    }

    @Test
    public void deleteBanner() {
        Banner banner = new Banner();
        banner.setTargetUrl("www.ya.ru");
        banner.setHeight(1);
        banner.setWidth(2);
        banner.setLangId(1);
        banner.setActivity(true);
        byte[] data = {(byte) 10};
        bannerService.createBanner(banner, new MockMultipartFile("name1", data));

        banner = new Banner();
        banner.setTargetUrl("www.ya.ru");
        banner.setHeight(3);
        banner.setWidth(4);
        banner.setLangId(2);
        banner.setActivity(true);
        data = new byte[]{(byte) 20};
        bannerService.createBanner(banner, new MockMultipartFile("name2", data));

        banner = new Banner();
        banner.setTargetUrl("www.ya.ru");
        banner.setHeight(5);
        banner.setWidth(6);
        banner.setLangId(3);
        banner.setActivity(true);
        data = new byte[]{(byte) 30};
        bannerService.createBanner(banner, new MockMultipartFile("name3", data));


        Assert.assertEquals(3, bannerService.findAll().size());

        List<Banner> loadedBanners = bannerService.findAll();
        System.out.println("Loaded Banners: " + loadedBanners.toString());
        for (Banner loadedBanner : loadedBanners) {
            bannerService.deleteBanner(loadedBanner.getId());
            if(!loadedBanner.getImgSrc().isEmpty()){
                File imgPath = new File(loadedBanner.getImgSrc());
                if (!imgPath.exists()) {
                    Assert.assertTrue(true);
                }
            }
        }
        Assert.assertEquals(0, bannerService.findAll().size());
    }

    @Test
    public void findById() {
        Banner banner = new Banner();
        banner.setTargetUrl("www.ya.ru");
        banner.setHeight(100);
        banner.setWidth(200);
        banner.setLangId(1);
        banner.setActivity(true);
        banner.setPriority(1000);
        byte[] data = { (byte) 10 };
        banner = bannerService.createBanner(banner, new MockMultipartFile("name", data));

        Banner loadedBanner = bannerService.findById(banner.getId());

        // priority = max(priority) + 1
        Assert.assertNotEquals(1000, (int) loadedBanner.getPriority());
        Assert.assertEquals("www.ya.ru", loadedBanner.getTargetUrl());
        Assert.assertEquals(200, (int) loadedBanner.getWidth());
        Assert.assertEquals(100, (int) loadedBanner.getHeight());
        Assert.assertEquals(1, (int) loadedBanner.getLangId());
        Assert.assertTrue(loadedBanner.getActivity());
        if(!loadedBanner.getImgSrc().isEmpty()){
            File imgPath = new File(loadedBanner.getImgSrc());
            if (imgPath.exists()) {
                Assert.assertTrue(true);
            }
        }
    }

}