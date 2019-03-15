export default class Banner{

    public id : number | null = null;
    public imgSrc : string | null = null;
    public imgFile : any | null = null;
    public width : number | null = null;
    public height : number| null = null;
    public targetUrl : string | null = null;
    public langId : number | null = null;
    public priority : number | null = null;
    public activity : boolean = true;

    public copyBanner(bannerRowFrom : Banner){
        this.id = bannerRowFrom.id;
        this.imgSrc = bannerRowFrom.imgSrc;
        this.width = bannerRowFrom.width;
        this.height = bannerRowFrom.height;
        this.targetUrl = bannerRowFrom.targetUrl;
        this.langId = bannerRowFrom.langId;
        this.priority = bannerRowFrom.priority;
        this.activity = bannerRowFrom.activity;
    }

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



}