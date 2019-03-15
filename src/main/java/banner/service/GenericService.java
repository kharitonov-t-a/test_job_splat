package banner.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService<Model, PK extends Serializable> {

    Model findById(PK id);

    Model create(Model model);

    Model update(Model model);

    void delete(PK id);

    boolean switchActivity(PK id, boolean newActivityState);

    List<Model> findAll();
}