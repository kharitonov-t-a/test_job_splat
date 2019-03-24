export default abstract class Model{


    public id : number | null = null;
    public activity : boolean = true;

    abstract clean() : void
    abstract copyItem<T extends Model>(itemAttr : T) : void

    public static CLASS_NAME = 'Model';
    className:string;
    public static generalityOf<T extends Model>(item : T) : boolean {
        return item.className === this.CLASS_NAME;
    }
}