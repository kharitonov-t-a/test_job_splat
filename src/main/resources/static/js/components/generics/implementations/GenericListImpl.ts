import {Vue, Prop, Component} from "vue-property-decorator";
import Model from "../../Model";
import Banner from "../../banners/Banner";
import Locale from "../../locales/Locale";
import GenericList from "../interfaces/GenericList";
import User from "../../users/User";
import Audit from "../../audits/Audit";

@Component({})
export default class GenericListImpl<T extends Model> extends Vue implements GenericList<T> {


    @Prop() totalItemList : Array<T>;
    @Prop() localeMap!: Map<number, string>;
    itemList : Array<T> = [];
    itemAttr: T = null;
    itemAttrChange: Boolean = true;

    showAuditTab : boolean = false;
    auditList : Array<Audit> = [];

    @Prop() pathURL : String;


    public editItem(item: T) {
        if(item instanceof Banner || item instanceof User)
            this.showAuditTab = false;
        this.cleanForm();
        this.itemAttr = item;
    }

    public deleteItem(item: T): void {

        if(item instanceof Banner || item instanceof User)
            this.showAuditTab = false;

        this.cleanForm();

        if (item.activity === false) {
            this.$resource(this.pathURL+'{/id}').remove({id: item.id}).then(result => {
                    if (result.ok) {
                        this.totalItemList.splice(this.totalItemList.indexOf(item), 1);

                        if(item instanceof Locale)
                            this.$emit("reloadBanners");

                        if(item instanceof Banner)
                            this.filterItem();
                    }
                },
                reason => alert("Error!"));
        } else {
            this.switchActivity(item, false);
        }
    }

    public activateItem(item : T){
        if(item instanceof Banner || item instanceof User)
            this.showAuditTab = false;

        this.switchActivity(item, true);
    }

    switchActivity(item : T, newActivityState : boolean){
        let formData = new FormData();
        formData.append('newActivityState', newActivityState.toString());
        this.$resource(this.pathURL+'/delete{/id}').update({id: item.id}, formData).then(result =>
                result.json().then((data : Object) => {
                    item.activity= newActivityState;

                    if(item instanceof Locale)
                        this.$emit("reloadBanners");
                }),
            reason => alert("Error!")
        );
    }

    cleanForm(){
        this.itemAttr = null;
        this.itemAttrChange = !this.itemAttrChange;
    }

    static getIndex<T extends Model>(list : Array<T>, id : Number) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].id === id) {
                return i;
            }
        }
        return -1;
    }

    public saveItem(item : T | FormData, id? : number){
        if(item instanceof Banner || item instanceof User)
            this.showAuditTab = false;



        if (id !== null) {
            this.$resource(this.pathURL + '{/id}').update({id: id}, item).then(result =>
                    result.json().then((data : T) => {
                        const index = GenericListImpl.getIndex(this.totalItemList, data.id);
                        this.totalItemList.splice(index, 1, data);
                        this.cleanForm();

                        if(item instanceof Locale)
                            this.localeMap.set(data.id, (<Locale>(<unknown>data)).name);

                        if(item instanceof Banner)
                            this.filterItem();
                    }),
                reason => alert("Error!")
            );
        } else {
            this.$resource(this.pathURL + '{/id}').save({}, item).then(result =>
                    result.json().then((data : T) => {
                        this.totalItemList.push(data);
                        this.cleanForm();

                        if(item instanceof Locale)
                            this.localeMap.set(data.id, (<Locale>(<unknown>data)).name);

                        if(item instanceof Banner)
                            this.filterItem();
                    }),
                reason => alert("Save error! There is no image!"))
        }
    }

    filterItem() : void {}

    showHistory(item : T){
        this.auditList = new Array<Audit>();
        this.$resource('/audit/list' + this.pathURL + '{/id}').get({id: item.id}).then(result =>
            result.json().then((data : Audit[]) => {
                data.forEach((audit: Audit) => this.auditList.push(audit))
                this.showAuditTab = true;
            })
        );
    }
}
