package banner.dao.mappers;

import banner.dao.GenericMapper;
import banner.model.Locale;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LocaleMapper implements GenericMapper<Locale> {
    @Override
    public Locale mapRow(ResultSet rs, int rowNum) throws SQLException {
        Locale locale = new Locale();
        locale.setId(rs.getInt("id"));
        locale.setName(rs.getString("name"));
        locale.setActivity(rs.getInt("activity") != 0);
        return locale;
    }
}
