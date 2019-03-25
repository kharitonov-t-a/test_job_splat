package banner.dao.interfaces;

import banner.dao.GenericDao;
import banner.model.Audit;

import java.util.List;

public interface AuditDao  extends GenericDao<Audit, Integer> {

    /**
     * @param id
     * @return
     */
    List<Audit> findByUser(Integer id);

    /**
     * @param id
     * @return
     */
    List<Audit> findByBanner(Integer id);

    /**
     *
     */
    void cleanAudit();

    /**
     * @param name
     * @return
     */
    List<Audit> findByUserName(String name);
}
