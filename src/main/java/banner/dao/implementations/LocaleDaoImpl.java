package banner.dao.implementations;

import banner.dao.GenericDaoImpl;
import banner.dao.GenericMapper;
import banner.dao.interfaces.LocaleDao;
import banner.model.Banner;
import banner.model.Locale;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class LocaleDaoImpl extends GenericDaoImpl<Locale, Integer, GenericMapper<Locale>> implements LocaleDao {

    @Override protected String getSELECT_ALL_SQL() { return "SELECT * FROM Locale ORDER BY id"; }

    @Override protected String getSELECT_BY_ID_SQL() { return "SELECT * FROM Locale WHERE id = ?"; }

    @Override protected String getUPDATE_ACTIVITY_SQL() { return "UPDATE Locale SET activity = ? WHERE id = ?"; }

    @Override protected String getTOTAL_DELETE_SQL() { return "DELETE FROM Locale WHERE id = ?"; }

    private final String INSERT_SQL =
            "INSERT INTO Locale(name, activity) VALUES(?,?)";

    private final String UPDATE_SQL =
            "UPDATE Locale SET name = ?, activity = ? WHERE id = ?";


    @Override
    public Locale create(Locale locale) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, locale.getName());
            ps.setInt(2, locale.getActivity()?1:0);
            return ps;
        }, holder);

        int newId = holder.getKey().intValue();
        locale.setId(newId);
        return locale;
    }

    @Override
    public Locale update(Locale locale) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, Statement.NO_GENERATED_KEYS);
            ps.setString(1, locale.getName());
            ps.setInt(2, locale.getActivity()?1:0);
            ps.setInt(3, locale.getId());
            return ps;
        });
        return locale;
    }
}
