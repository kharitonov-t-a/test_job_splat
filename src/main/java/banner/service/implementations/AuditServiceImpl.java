package banner.service.implementations;

import banner.dao.interfaces.AuditDao;
import banner.model.Audit;
import banner.service.GenericServiceImpl;
import banner.service.interfaces.AuditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditServiceImpl extends GenericServiceImpl<Audit, Integer, AuditDao> implements AuditService {

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Audit> findByUser(Integer id) {
        return dao.findByUser(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Audit> findByBanner(Integer id) {
        return dao.findByBanner(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Audit> findByUserName(String name) {
        return dao.findByUserName(name);
    }
}
