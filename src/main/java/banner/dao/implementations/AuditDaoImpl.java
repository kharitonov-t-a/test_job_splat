package banner.dao.implementations;

import banner.dao.GenericDaoImpl;
import banner.dao.GenericMapper;
import banner.dao.interfaces.AuditDao;
import banner.model.Audit;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class AuditDaoImpl extends GenericDaoImpl<Audit, Integer, GenericMapper<Audit>> implements AuditDao {

    @Override protected String getSELECT_ALL_SQL() { return "SELECT * FROM Audit ORDER BY id"; }

    @Override protected String getSELECT_BY_ID_SQL() { return "SELECT * FROM Audit WHERE id = ?"; }

    @Override protected String getTOTAL_DELETE_SQL() { return "DELETE FROM Audit WHERE id = ?"; }

    @Override protected String getUPDATE_ACTIVITY_SQL() { return null; }

    private final String INSERT_SQL =
            "INSERT INTO Audit(idBanner, idUser, crud, description, date)" +
                    " values(?,?,?,?,?)";

    private final String UPDATE_SQL =
            "UPDATE Audit SET idBanner = ?, idUser = ?, crud = ?, description = ?, date = ? WHERE id = ?";

    private final String SELECT_BY_USER_SQL =
            "SELECT * FROM Audit WHERE idUser = ? ORDER BY date";

    private final String SELECT_BY_BANNER_SQL =
            "SELECT * FROM Audit WHERE idBanner = ? ORDER BY date";

    private final String CLEAN_AUDIT_SQL =
            "DELETE FROM Audit WHERE idBanner NOT IN (SELECT id FROM Banner) AND" +
                    " idUser NOT IN (SELECT id FROM User)";


    @Override
    public Audit create(Audit audit) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, audit.getIdBanner());
                ps.setInt(2, audit.getIdUser());
                ps.setInt(3, audit.getCrud().getAction());
                ps.setString(4, audit.getDescription());
                ps.setDate(5, new java.sql.Date(audit.getDate().getTime()));
                return ps;
            }
        }, holder);

        int newAuditId = holder.getKey().intValue();
        audit.setId(newAuditId);
        return audit;
    }

    @Override
    public Audit update(Audit audit) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, Statement.NO_GENERATED_KEYS);
            ps.setInt(1, audit.getIdBanner());
            ps.setInt(2, audit.getIdUser());
            ps.setInt(3, audit.getCrud().getAction());
            ps.setString(4, audit.getDescription());
            ps.setDate(5, new java.sql.Date(audit.getDate().getTime()));
            ps.setInt(6, audit.getId());
            return ps;
        });
        return audit;
    }

    @Override
    public boolean switchActivity(Integer id, boolean activity) {
        return false;
    }

    @Override
    public List<Audit> findByUser(Integer id) {
        return findBySome(SELECT_BY_USER_SQL, id);
    }

    @Override
    public List<Audit> findByBanner(Integer id) {
        return findBySome(SELECT_BY_BANNER_SQL, id);
    }

    @Override
    public void cleanAudit() {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CLEAN_AUDIT_SQL, Statement.NO_GENERATED_KEYS);
            return ps;
        });
    }

    private List<Audit> findBySome(String query, Integer id) {
        return jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
                ps.setInt(1, id);
                return ps;
            }
        }, mapper);
    }


}
