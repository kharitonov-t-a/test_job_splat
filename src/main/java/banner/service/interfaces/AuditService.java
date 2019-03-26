package banner.service.interfaces;

import banner.model.Audit;
import banner.service.GenericService;

import java.util.List;

public interface AuditService extends GenericService<Audit, Integer> {

    /**
     * Find audit items by user id
     * @param id user id
     * @return audit list
     */
    List<Audit> findByUser(Integer id);

    /**
     * Find audit items by banner id
     * @param id banner id
     * @return audit list
     */
    List<Audit> findByBanner(Integer id);

    /**
     * Find audit items by user name
     * @param name user name
     * @return audit list
     */
    List<Audit> findByUserName(String name);
}
