import Model from "../Model";

export default class User extends Model{


    // public id : number | null = null;
    public username : string | null = null;
    public password : string | null = null;
    // public activity : boolean = true;

    clean() : void {
        this.id = null;
        this.username = null;
        this.password = null;
        this.activity = true;
    }

    copyItem<T extends Model>(localeRowFrom: T): void {
        this.id = localeRowFrom.id;
        this.username = (<User><unknown>localeRowFrom).username;
        this.password = (<User><unknown>localeRowFrom).password;
        this.activity = localeRowFrom.activity;
    }
}