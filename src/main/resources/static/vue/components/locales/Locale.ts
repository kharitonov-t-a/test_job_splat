import Model from "../Model";

export default class Locale extends Model{

    // public id : number | null = null;
    public name : string | null = null;
    // public activity : boolean = true;

    clean(): void {
        this.id = null;
        this.name = null;
        this.activity = true;
    }

    copyItem<T extends Model>(localeRowFrom: T): void {
        this.id = localeRowFrom.id;
        this.name = (<Locale><unknown>localeRowFrom).name
        this.activity = localeRowFrom.activity;
    }

    public static CLASS_NAME = 'Locale';
    public className : string = Locale.CLASS_NAME;

    // public static generalityOf<T extends Model>(item: T) : boolean {
    //     if (item.className === this.CLASS_NAME)
    //         return true;
    //     return super.generalityOf(item);
    // }
}