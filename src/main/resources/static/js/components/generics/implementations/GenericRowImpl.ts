import Vue from "vue";
import GenericRow from "../interfaces/GenericRow";
import {Component, Prop} from "vue-property-decorator";
import Model from "../../Model";

@Component({})
export default class GenericRowImpl<T extends Model> extends Vue implements GenericRow<T> {

    @Prop() readonly item!: T;

    activateItem(): void {
        this.$emit('activateItem')
    }

    deleteItem(): void {
        this.$emit('deleteItem')
    }

    editItem(): void {
        this.$emit('editItem')
    }

    showHistory(): void {
        this.$emit('showHistory')
    }

}
