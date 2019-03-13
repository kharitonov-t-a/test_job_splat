package banner.service;

import banner.dao.GenericDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class GenericServiceImpl<Model, PK extends Serializable, Dao> implements GenericService<Model, PK>{

    @Autowired
    protected Dao dao;

    @Override
    public Model findById(PK id) {
        return (Model) ((GenericDaoImpl)dao).findById(id);
    }

    @Override
    public Model create(Model model) {
        return (Model) ((GenericDaoImpl)dao).create(model);
    }

    @Override
    public Model update(Model model) {
        return (Model) ((GenericDaoImpl)dao).update(model);
    }

    @Override
    public void delete(PK id) {
        ((GenericDaoImpl)dao).delete(id);
    }

    @Override
    public boolean disable(PK id) {
        return ((GenericDaoImpl)dao).disable(id);
    }

    @Override
    public List<Model> findAll() {
        return ((GenericDaoImpl)dao).findAll();
    }
}
