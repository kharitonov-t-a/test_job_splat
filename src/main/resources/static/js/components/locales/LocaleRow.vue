<template>
    <div :class="'table-row-' + (index%2+1) + ' table-row'" style="display:table-row">
        <div style="display:table-cell">{{ item.id }}</div>
        <div style="display:table-cell">{{ item.name }}</div>
        <div style="display:table-cell">{{ item.activity }}</div>
        <div style="display:table-cell">
            <span>
               <input type="button" value="Edit" @click="editItem"/>
            </span>
        </div>
        <div style="display:table-cell">
            <span>
                <input type="button" value="Delete" @click="deleteItem"/>
            </span>
        </div>
        <div style="display:table-cell" v-if="!item.activity">
            <span>
                <input type="button" value="Activate" @click="activateItem"/>
            </span>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import Locale from "components/locales/Locale.ts";
    import GenericRowImpl from "../generics/implementations/GenericRowImpl";

    @Component({
        name: 'LocaleRow'
    })
    export default class LocaleRow extends GenericRowImpl<Locale> {

    }
</script>

<!--<style lang="less" scoped src="static/css/row"></style>-->
<style lang="less" scoped>

</style>