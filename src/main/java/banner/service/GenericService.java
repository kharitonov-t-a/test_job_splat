package banner.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService<Model, PK extends Serializable> {

    /**
     * @param id
     * @return
     */
    Model findById(PK id);

    /**
     * @param model
     * @return
     */
    Model create(Model model);

    /**
     * @param model
     * @return
     */
    Model update(Model model);

    /**
     * @param id
     */
    void delete(PK id);

    /**
     * @param id
     * @param newActivityState
     * @return
     */
    boolean switchActivity(PK id, boolean newActivityState);

    /**
     * @return
     */
    List<Model> findAll();
}