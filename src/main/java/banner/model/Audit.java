package banner.model;

import banner.model.enums.Crud;

import java.util.Date;

public class Audit {

    private int id;
    private int idBanner;
    private int idUser;
    private Crud crud;
    private String description;
    private Date date;

}
