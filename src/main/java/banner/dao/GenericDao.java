package banner.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    /** Извлечь весь список объектов
     * @return
     */
    List<T> findAll();

    /** Извлечь объект, используя указанный id в качестве первичного ключа
     * @param id
     * @return
     */
    T findById(PK id);

    /** Сохранить объект newInstance в базе данных
     * @param newInstance
     * @return
     */
    T create(T newInstance);

    /** Сохранить изменения, сделанные в объекте
     * @param transientObject
     * @return
     */
    T update(T transientObject);

    /** Изменение активности объекта
     * @param id
     * @param activity
     * @return
     */
    boolean switchActivity(PK id, boolean activity);

    /** Удалить объект из базы данных
     * @param id
     */
    void delete(PK id);



}