package banner.service.implementations;

import banner.dao.interfaces.LocaleDao;
import banner.model.Locale;
import banner.service.GenericServiceImpl;
import banner.service.interfaces.LocaleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocaleServiceImpl extends GenericServiceImpl<Locale, Integer, LocaleDao> implements LocaleService {

}
