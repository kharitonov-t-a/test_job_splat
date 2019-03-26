package banner.dao.interfaces;

import banner.dao.GenericDao;
import banner.model.Audit;

import java.util.List;

public interface AuditDao  extends GenericDao<Audit, Integer> {

    /**
     * Find audit note by user id
     * @param id user id
     * @return audit list
     */
    List<Audit> findByUser(Integer id);

    /**
     * Find audit note by banner id
     * @param id banner id
     * @return audit list
     */
    List<Audit> findByBanner(Integer id);

    /**
     * Clean audit notes if banner and user was deleted
     */
    void cleanAudit();

    /**
     * Find audit note by user name
     * @param name user name
     * @return audit list
     */
    List<Audit> findByUserName(String name);
}
