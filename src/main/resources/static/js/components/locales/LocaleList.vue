<template>
    <div>

        <locale-form :itemAttr="itemAttr"
                     :itemAttrChange="itemAttrChange"
                     :errorsForm="errorsForm"
                     v-on:saveItem="saveItem($event, $event.id)"/>

        <div class="drop list" style="display:table">

            <div style="display:table-row">
                <div style="display:table-cell">id</div>
                <div style="display:table-cell">name</div>
                <div style="display:table-cell">activity</div>
                <div style="display:table-cell"></div>
                <div style="display:table-cell"></div>
            </div>

            <div class="table-row-group" style="display:table-row-group">
                <locale-row v-for="item in totalItemList"
                            :key="item.id"
                            :item="item"
                            v-on:activateItem="activateItem(item)"
                            v-on:editItem="editItem(item)"
                            v-on:deleteItem="deleteItem(item)">
                </locale-row>
            </div>

        </div>

    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import LocaleForm from 'components/locales/LocaleForm.vue';
    import LocaleRow from 'components/locales/LocaleRow.vue';
    import Locale from "components/locales/Locale.ts";
    import GenericListImpl from "../generics/implementations/GenericListImpl";

    @Component({
        name: 'LocaleList',
        components:{
            LocaleRow,
            LocaleForm
        }
    })
    export default class LocaleList extends GenericListImpl<Locale>  {

        filterItem(): void {}

    }

</script>

<style scoped>

</style>