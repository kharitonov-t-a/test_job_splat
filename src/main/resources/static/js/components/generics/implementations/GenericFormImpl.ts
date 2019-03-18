import {Vue, Prop, Watch, Component} from "vue-property-decorator";
import Model from "../../Model";
import GenericForm from "../interfaces/GenericForm";

@Component({})
export default class GenericFormImpl<T extends Model> extends Vue implements GenericForm<T> {

    @Prop() itemAttr!: T;
    item: T;
    @Prop() readonly itemAttrChange: boolean;
    @Prop() readonly errorsForm: Array<any>;
    errorsFormMap: Map<string, string> = new Map();

    saveItem(): void {
        if(this.checkItemBeforeSave())
           this.$emit('saveItem', this.item);
        else
            alert("Fill the fields!");
    }

    @Watch('itemAttrChange')
    getItemAttr() : void{
        if(this.itemAttr !== null){
            this.item.copyItem(this.itemAttr);
        }else{
            this.cleanForm();
        }
    }

    @Watch('errorsForm')
    setErrorsForm() : void{
        this.errorsFormMap = new Map<string, string>();
        this.errorsForm.forEach(value => {
            this.errorsFormMap.set(value.field, value.code);//value.defaultMessage
        });
    }

    cleanForm(): void {
        this.item.clean();
    }

    checkItemBeforeSave(): boolean {return true};


}

