<template>
    <div class="form">

        <div class="field">
            <label for="nameField">Name locale</label>
            <input id="nameField" type="text" placeholder="name" v-model="item.name"/>
            <div class="errorForm">{{errorsFormMap.get("name")}}</div>
        </div>
        <div class="field fieldNotInput">
            <input type="button" value="Save" @click="saveItem"/>
            <input type="button" value="Cancel" @click="cleanForm"/>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import Locale from "components/locales/Locale.ts";
    import GenericFormImpl from "../generics/implementations/GenericFormImpl";

    @Component({
        name: 'LocaleForm'
    })
    export default class LocaleForm extends GenericFormImpl<Locale> {

        constructor(){
            super();
            this.item = new Locale();
        }

        checkItemBeforeSave(): boolean {
            return this.item.name != null && this.item.name.length != 0;
        }

    }
</script>

<style scoped>

</style>