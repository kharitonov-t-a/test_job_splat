<template>
    <div>
        <input type="text" placeholder="width" v-model="item.name"/>
        <input type="button" value="Save" @click="saveItem"/>
        <input type="button" value="Cancel" @click="cleanForm"/>
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
            return this.item.name != null;
        }

    }
</script>

<style scoped>

</style>