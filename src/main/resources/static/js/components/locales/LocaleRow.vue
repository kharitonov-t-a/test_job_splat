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
        <div style="display:table-cell" v-if="!locale.activity">
            <span>
                <input type="button" value="Activate" @click="activateLocale"/>
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

        editLocale() {
            this.$emit('editLocale')
        }

        deleteLocale() {
            this.$emit('deleteLocale')
        }

        activateLocale() {
            this.$emit('activateLocale')
        }
    }
</script>

<style scoped>

</style>