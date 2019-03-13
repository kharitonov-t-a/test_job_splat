<template>
    <div>
        <locale-form :localeAttr="localeAttr"
                     :localeAttrChange="localeAttrChange"
                     v-on:saveLocale="saveLocale($event)"/>
        <div class="drop list" style="display:table">
            <div style="display:table-row">
                <div style="display:table-cell">id</div>
                <div style="display:table-cell">name</div>
                <div style="display:table-cell">activity</div>
                <div style="display:table-cell"></div>
                <div style="display:table-cell"></div>
            </div>
            <div class="table-row-group" style="display:table-row-group">
                <locale-row v-for="(locale, index) in localeList"
                            :key="locale.id"
                            :locale="locale"
                            v-on:editLocale="editLocale(locale)"
                            v-on:deleteLocale="deleteLocale(locale)">
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

    @Component({
        name: 'LocaleList',
        components:{
            LocaleRow,
            LocaleForm
        }
    })
    export default class LocaleList extends Vue {
        @Prop() localeList!: Array<Locale>;
        localeAttr: Locale = new Locale();
        localeAttrChange: Boolean = true;

        constructor(){
            super();
            // this.$resource('/locale/list').get().then(result =>
            //     result.json().then((data : Locale[]) =>
            //         data.forEach((locale : Locale)=> this.localeList.push(locale))
            //     )
            // );
        }

        public saveLocale(locale : Locale){

            let formData = new FormData();
            formData.append('name', locale.name);
            formData.append('activity', locale.activity.toString());

            if (locale.id !== null) {
                this.$resource('/locale{/id}').update({id: locale.id}, locale).then(result =>
                        result.json().then((data : Locale) => {
                            const index = LocaleList.getIndex(this.localeList, data.id);
                            this.localeList.splice(index, 1, data);
                            this.clearForm();
                        }),
                    reason => alert("Error!")
                );
            } else {
                this.$resource('/locale{/id}').save({}, locale).then(result =>
                        result.json().then((data : Locale) => {
                            this.localeList.push(data);
                            this.clearForm();
                        }),
                    reason => alert("Save error!"))
            }
        }

        public editLocale(locale: Locale) {
            this.clearForm();
            this.localeAttr = locale;
        }

        public deleteLocale(locale : Locale){
            this.clearForm();
            if (locale.activity === false) {
                this.$resource('/locale{/id}').remove({id: locale.id}).then(result => {
                        if (result.ok) {
                            this.localeList.splice(this.localeList.indexOf(locale), 1);
                        }
                    },
                    reason => alert("Error!"));
            } else {
                this.$resource('/locale/delete{/id}').update({id: locale.id}, {}).then(result =>
                        result.json().then((data : Object) => {
                            locale.activity= false;
                        }),
                    reason => alert("Error!")
                );
            }
        }

        clearForm(){
            this.localeAttr = null;
            this.localeAttrChange = !this.localeAttrChange;
        }

        static getIndex(list : Array<Locale>, id : Number) {
            for (var i = 0; i < list.length; i++) {
                if (list[i].id === id) {
                    return i;
                }
            }
            return -1;
        }

    }

</script>

<style scoped>

</style>