export default class Locale{

    // @ts-ignore
    // constructor(){
    // }
    constructor();
    constructor(id : number, name : string, activity : boolean){
        // this.id = id;
        // this.name = name;
        // this.activity = activity;
    }


    public id : number | null = null;
    public name : string | null = null;
    public activity : boolean = true;

    public copyLocale(localeRowFrom : Locale){
        this.id = localeRowFrom.id;
        this.name = localeRowFrom.name;
        this.activity = localeRowFrom.activity;
    }

}