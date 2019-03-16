import Model from "../Model";

export default class Banner extends Model{

    // public id : number | null = null;
    public imgSrc : string | null = null;
    public imgFile : any | null = null;
    public width : number | null = null;
    public height : number| null = null;
    public targetUrl : string | null = null;
    public langId : number | null = null;
    public priority : number | null = null;
    // public activity : boolean = true;

    public clean(){
        this.id = null;
        this.imgSrc = null;
        this.imgFile = null;
        this.width = null;
        this.height = null;
        this.targetUrl = null;
        this.langId = null;
        this.priority = null;
        this.activity = true;
    }

    copyItem<T extends Model>(localeRowFrom: T): void {
        this.id = localeRowFrom.id;
        this.imgSrc = (<Banner><unknown>localeRowFrom).imgSrc;
        this.width = (<Banner><unknown>localeRowFrom).width;
        this.height = (<Banner><unknown>localeRowFrom).height;
        this.targetUrl = (<Banner><unknown>localeRowFrom).targetUrl;
        this.langId = (<Banner><unknown>localeRowFrom).langId;
        this.priority = (<Banner><unknown>localeRowFrom).priority;
        this.activity = localeRowFrom.activity;
    }
}