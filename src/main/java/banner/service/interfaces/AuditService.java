package banner.service.interfaces;

import banner.model.Audit;
import banner.service.GenericService;

import java.util.List;

public interface AuditService extends GenericService<Audit, Integer> {

    List<Audit> findByUser(Integer id);

    List<Audit> findByBanner(Integer id);
}
