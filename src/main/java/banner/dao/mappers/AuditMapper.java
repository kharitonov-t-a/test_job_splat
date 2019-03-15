package banner.dao.mappers;

import banner.dao.GenericMapper;
import banner.model.Audit;
import banner.model.enums.Crud;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Component
public class AuditMapper implements GenericMapper<Audit> {
    @Override
    public Audit mapRow(ResultSet rs, int rowNum) throws SQLException {
        Audit audit = new Audit();
        audit.setId(rs.getInt("id"));
        audit.setIdBanner(rs.getInt("idBanner"));
        audit.setIdUser(rs.getInt("idUser"));
        audit.setCrud(Crud.values()[rs.getInt("crud") - 1]);
        audit.setDescription(rs.getString("description"));
        audit.setDate(rs.getDate("date"));
        return audit;
    }
}