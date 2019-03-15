<template>
    <div>
        <input type="text" placeholder="width" v-model="locale.name"/>
        <input type="button" value="Save" @click="saveLocale"/>
        <input type="button" value="Cancel" @click="clearForm"/>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import Locale from "components/locales/Locale.ts";

    @Component({
        name: 'LocaleForm'
    })
    export default class LocaleForm extends Vue {

        @Prop() localeAttr!: Locale;
        locale: Locale = new Locale();
        @Prop() readonly localeAttrChange : boolean;

        saveLocale() {
            this.$emit('saveLocale', this.locale);
        }

        @Watch('localeAttrChange')
        getLocaleAttr(){
            if(this.localeAttr !== null){
                this.locale.copyLocale(this.localeAttr);
            }else{
                this.clearForm();
            }
        }

        clearForm(){
            this.locale.id = null;
            this.locale.name = null;
            this.locale.activity = true;
        }
    }
</script>

<style scoped>

</style>