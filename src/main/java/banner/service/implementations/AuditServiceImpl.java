package banner.service.implementations;

import banner.dao.interfaces.AuditDao;
import banner.model.Audit;
import banner.service.GenericServiceImpl;
import banner.service.interfaces.AuditService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditServiceImpl extends GenericServiceImpl<Audit, Integer, AuditDao> implements AuditService {

    @Override
    public List<Audit> findByUser(Integer id) {
        return dao.findByUser(id);
    }

    @Override
    public List<Audit> findByBanner(Integer id) {
        return dao.findByBanner(id);
    }
}
