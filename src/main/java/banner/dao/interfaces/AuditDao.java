package banner.dao.interfaces;

import banner.dao.GenericDao;
import banner.model.Audit;

import java.util.List;

public interface AuditDao  extends GenericDao<Audit, Integer> {

    List<Audit> findByUser(Integer id);

    List<Audit> findByBanner(Integer id);

    void cleanAudit();
}
