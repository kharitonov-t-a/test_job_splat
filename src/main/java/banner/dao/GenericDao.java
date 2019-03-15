package banner.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    /** Извлечь весь список объектов */
    List<T> findAll();

    /** Извлечь объект, используя указанный id в качестве первичного ключа */
    T findById(PK id);

    /** Сохранить объект newInstance в базе данных */
    T create(T newInstance);

    /** Сохранить изменения, сделанные в объекте */
    T update(T transientObject);

    /** Изменение активности объекта */
    boolean switchActivity(PK id, boolean activity);

    /** Удалить объект из базы данных */
    void delete(PK id);



}