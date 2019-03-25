package banner.service.implementations;

import banner.dao.implementations.BannerDaoImpl;
import banner.dao.implementations.LocaleDaoImpl;
import banner.dao.mappers.BannerMapper;
import banner.dao.mappers.LocaleMapper;
import banner.model.Banner;
import banner.model.Locale;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class LocaleServiceImplTest {

    private LocaleDaoImpl localeDao;
    private LocaleServiceImpl localeService;
    EmbeddedDatabase db;
    @Before
    public void setUp() throws Exception {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sqlScripts/createLocaleTable")
                .addScript("sqlScripts/createBannerTable")
                .build();
        localeDao = new LocaleDaoImpl();
        localeDao.setDataSource(db);
        localeDao.postConstruct();
        localeDao.mapper = new LocaleMapper();
        BannerDaoImpl bannerDao = new BannerDaoImpl();
        bannerDao.setDataSource(db);
        bannerDao.postConstruct();
        bannerDao.mapper = new BannerMapper();

        localeService = new LocaleServiceImpl();
        localeService.dao = localeDao;
        localeService.bannerDao = bannerDao;
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }


    /**
     *
     */
    @Test
    public void create() {
        Locale locale = new Locale();
        locale.setName("ru");
        locale.setActivity(true);

        Locale createLocale = localeService.create(locale);
        System.out.println("Save Locale: " + locale.toString());
        Locale loadedLocale = localeService.findById(createLocale.getId());
        System.out.println("Loaded Locale: " + loadedLocale.toString());
        Assert.assertNotNull(loadedLocale.getId());
        Assert.assertEquals("ru", loadedLocale.getName());
        Assert.assertTrue(loadedLocale.getActivity());
    }

    /**
     *
     */
    @Test
    public void update() {
        Locale locale = new Locale();
        locale.setName("ru");

        Locale createLocale = localeService.create(locale);
        System.out.println("Save Locale: " + locale.toString());

        String nameCreatedLocale = createLocale.getName();
        Assert.assertEquals("ru", nameCreatedLocale);

        Locale loadedLocale = localeService.findById(createLocale.getId());
        System.out.println("Loaded Locales: " + loadedLocale.toString());
        loadedLocale.setName("en");
        nameCreatedLocale = localeService.update(loadedLocale).getName();

        Assert.assertEquals("en", nameCreatedLocale);

    }

    /**
     *
     */
    @Test
    public void delete() {
        List<Locale> loadedLocales = localeService.findAll();
        Assert.assertTrue(loadedLocales.size() == 3);

        Locale removableLocale = loadedLocales.get(loadedLocales.size() - 1);

        Banner banner = new Banner();
        banner.setTargetUrl("www.ya.ru");
        banner.setHeight(100);
        banner.setWidth(200);
        banner.setImgSrc("exampleImgSrc");
        banner.setLangId(removableLocale.getId());
        banner.setPriority(1);
        banner.setActivity(true);

        localeService.bannerDao.create(banner);

        localeService.delete(removableLocale.getId());

        loadedLocales = localeService.findAll();
        Assert.assertEquals(2, loadedLocales.size());

        loadedLocales.forEach(locale -> Assert.assertNotEquals(locale.getId(), removableLocale.getId()));

        Assert.assertEquals(0, localeService.bannerDao.findAll().size());
    }
}