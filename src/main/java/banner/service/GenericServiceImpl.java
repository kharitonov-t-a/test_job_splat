package banner.service;

import banner.dao.GenericDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public class GenericServiceImpl<Model, PK extends Serializable, Dao> implements GenericService<Model, PK>{

    @Autowired
    public Dao dao;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public Model findById(PK id) {
        return (Model) ((GenericDaoImpl)dao).findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Model create(Model model) {
        return (Model) ((GenericDaoImpl)dao).create(model);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Model update(Model model) {
        return (Model) ((GenericDaoImpl)dao).update(model);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(PK id) {
        ((GenericDaoImpl)dao).delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean switchActivity(PK id, boolean newActivityState) {
        return ((GenericDaoImpl)dao).switchActivity(id, newActivityState);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Model> findAll() {
        return ((GenericDaoImpl)dao).findAll();
    }
}
