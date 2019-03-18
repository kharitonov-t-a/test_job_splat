package banner.dao.mappers;

import banner.dao.GenericMapper;
import banner.model.Banner;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BannerMapper implements GenericMapper<Banner> {
    @Override
    public Banner mapRow(ResultSet rs, int rowNum) throws SQLException {
        Banner banner = new Banner();
        banner.setId(rs.getInt("id"));
        banner.setImgSrc(rs.getString("imgSrc"));
        banner.setWidth(rs.getInt("width"));
        banner.setHeight(rs.getInt("height"));
        banner.setTargetUrl(rs.getString("targetUrl"));
        banner.setLangId(rs.getInt("langId"));
        banner.setPriority(rs.getInt("priority"));
        banner.setActivity(rs.getInt("activity") != 0);
        return banner;
    }
}
