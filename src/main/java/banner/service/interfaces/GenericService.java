package banner.service.interfaces;

import java.io.Serializable;
import java.util.List;

public interface GenericService<Model, PK extends Serializable> {

    Model findById(PK id);

    Model create(Model model);

    Model update(Model dto);

    void delete(PK id);

    List<Model> findAll();
}