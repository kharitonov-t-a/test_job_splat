package banner.service.interfaces;

import banner.model.Audit;
import banner.service.GenericService;

import java.util.List;

public interface AuditService extends GenericService<Audit, Integer> {

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
     * @param name
     * @return
     */
    List<Audit> findByUserName(String name);
}
