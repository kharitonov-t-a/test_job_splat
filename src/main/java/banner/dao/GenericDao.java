package banner.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    /** Get all item list
     * @return item list
     */
    List<T> findAll();

    /** Get item by id
     * @param id item id
     * @return item
     */
    T findById(PK id);

    /** Save newInstance in DB
     * @param newInstance item
     * @return item
     */
    T create(T newInstance);

    /** Update item in DB
     * @param transientObject item
     * @return item
     */
    T update(T transientObject);

    /** Change item activity
     * @param id item id
     * @param activity new activity value
     * @return true - all successfully
     */
    boolean switchActivity(PK id, boolean activity);

    /** Delete item by id
     * @param id item id
     */
    void delete(PK id);



}