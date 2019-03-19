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
    @Prop() readonly totalItemListChange: Boolean;

    errorsForm : Array<any> = [];


    public editItem(item: T) {
        this.showAuditTab = false;
        this.cleanForm();
        this.itemAttr = item;
    }

    public deleteItem(item: T): void {

        this.showAuditTab = false;

        this.cleanForm();

        if (item.activity === false) {
            this.$resource(this.pathURL+'{/id}').remove({id: item.id}).then(result => {
                    if (result.ok) {
                        this.totalItemList.splice(this.totalItemList.indexOf(item), 1);

                        if(Locale.generalityOf(item))
                            this.$emit("reloadBanners");

                        if(Banner.generalityOf(item))
                            this.filterItem();
                    }
                },
                reason => alert("Error!"));
        } else {
            this.switchActivity(item, false);
        }
    }

    public activateItem(item : T){
        this.showAuditTab = false;

        this.switchActivity(item, true);
    }

    switchActivity(item : T, newActivityState : boolean){
        let formData = new FormData();
        formData.append('newActivityState', newActivityState.toString());
        this.$resource(this.pathURL+'/delete{/id}').update({id: item.id}, formData).then(result =>
                result.json().then((data : Object) => {
                    item.activity= newActivityState;

                    if(Locale.generalityOf(item))
                        this.$emit("reloadBanners");

                    if(Banner.generalityOf(item))
                        this.filterItem();
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

    public saveItem(item : T, id? : number, itemFormData? : FormData){
        this.showAuditTab = false;

        let requestBody;
        if(itemFormData != null)
            requestBody = itemFormData;
        else
            requestBody = item;

        if (id !== null) {
            this.$resource(this.pathURL + '{/id}').update({id: id}, requestBody).then(result =>
                    result.json().then((data : T) => {
                        const index = GenericListImpl.getIndex(this.totalItemList, data.id);
                        this.totalItemList.splice(index, 1, data);
                        this.cleanForm();

                        if(Locale.generalityOf(item))
                            this.localeMap.set(data.id, (<Locale>(<unknown>data)).name);

                        if(Banner.generalityOf(item))
                            this.filterItem();
                    }),
            reason => {
                this.errorsForm = reason.body.errors;
            });
        } else {
            this.$resource(this.pathURL + '{/id}').save({}, requestBody).then(result =>
                    result.json().then((data : T) => {
                        this.totalItemList.push(data);
                        this.cleanForm();

                        if(Locale.generalityOf(item))
                            this.localeMap.set(data.id, (<Locale>(<unknown>data)).name);

                        if(Banner.generalityOf(item))
                            this.filterItem();
                    }),
            reason => {
                this.errorsForm = reason.body.errors;
            })
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
