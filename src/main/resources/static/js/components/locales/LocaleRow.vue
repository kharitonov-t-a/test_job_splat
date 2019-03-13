<template>
    <div class="table-row" style="display:table-row">
        <div style="display:table-cell">{{ locale.id }}</div>
        <div style="display:table-cell">{{ locale.name }}</div>
        <div style="display:table-cell">{{ locale.activity }}</div>
        <div style="display:table-cell">
            <span>
               <input type="button" value="Edit" @click="editLocale"/>
            </span>
        </div>
        <div style="display:table-cell">
            <span>
                <input type="button" value="Delete" @click="deleteLocale"/>
            </span>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import Locale from "components/locales/Locale.ts";

    @Component({
        name: 'LocaleRow'
    })
    export default class LocaleRow extends Vue {

        @Prop() readonly locale!: Locale;
        // @Prop() readonly localeList!: Array<Locale>;

        editLocale() {
            this.$emit('editLocale')
        }

        deleteLocale() {
            this.$emit('deleteLocale')
        }
    }
</script>

<style scoped>

</style>