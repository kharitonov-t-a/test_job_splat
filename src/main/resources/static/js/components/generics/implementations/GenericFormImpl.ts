import {Vue, Prop, Watch, Component} from "vue-property-decorator";
import Model from "../../Model";
import GenericForm from "../interfaces/GenericForm";

@Component({})
export default class GenericFormImpl<T extends Model> extends Vue implements GenericForm<T> {

    @Prop() itemAttr!: T;
    item: T;
    @Prop() readonly itemAttrChange: boolean;

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

    cleanForm(): void {
        this.item.clean();
    }

    checkItemBeforeSave(): boolean {return true};


}

