export default class User{

    public id : number | null = null;
    public username : string | null = null;
    public password : string | null = null;
    public activity : boolean = true;

    public copyUser(userRowFrom : User){
        this.id = userRowFrom.id;
        this.username = userRowFrom.username;
        this.password = userRowFrom.password;
        this.activity = userRowFrom.activity;
    }

}