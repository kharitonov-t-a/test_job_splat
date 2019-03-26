package banner.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService<Model, PK extends Serializable> {

    /**
     * Get item by id
     * @param id item id
     * @return exists item
     */
    Model findById(PK id);

    /**
     * Create new item
     * @param model model item without id
     * @return created item
     */
    Model create(Model model);

    /**
     * Update exists item
     * @param model model item with filled id
     * @return updated item
     */
    Model update(Model model);

    /**
     * Delete item
     * @param id item id
     */
    void delete(PK id);

    /**
     * Change activity item id
     * @param id item id
     * @param newActivityState new item state (true or false)
     * @return true if item changed activity successfully
     */
    boolean switchActivity(PK id, boolean newActivityState);

    /**
     * Find all items
     * @return item list
     */
    List<Model> findAll();
}