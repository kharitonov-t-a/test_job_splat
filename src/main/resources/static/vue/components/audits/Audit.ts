export default class Audit{


    public id : number | null = null;
    public idBanner : number | null = null;
    public idUser : number | null = null;
    public crud : Crud = null;
    public description : string | null = null;
    public date : Date = null;

    // public copyUser(userRowFrom : User){
    //     this.id = userRowFrom.id;
    //     this.username = userRowFrom.username;
    //     this.password = userRowFrom.password;
    //     this.activity = userRowFrom.activity;
    // }
}

enum Crud {
    CREATE = 1,
    UPDATE = 2,
    SWITCH_ACTIVITY = 3,
    DELETE = 4,
    SORT = 5,

}