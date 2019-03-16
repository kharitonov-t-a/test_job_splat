export default abstract class Model{

    public id : number | null = null;
    public activity : boolean = true;

    abstract clean() : void
    abstract copyItem<T extends Model>(itemAttr : T) : void
}