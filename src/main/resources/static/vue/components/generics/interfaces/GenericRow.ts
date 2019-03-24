import {Prop} from "vue-property-decorator";

export default interface GenericRow<T> {

    editItem() : void

    deleteItem() : void

    activateItem() : void

    showHistory() : void

}
