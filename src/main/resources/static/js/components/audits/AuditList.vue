<template>
        <div class="drop list" style="display:table">
            <input type="text" placeholder="width" v-model="searchValue"/>
            <input type="button" value="Search" @click="searchHistory"/>
            <div style="display:table-row">
                <div style="display:table-cell">id</div>
                <div style="display:table-cell">date</div>
                <div style="display:table-cell">crud</div>
                <div style="display:table-cell">idBanner</div>
                <div style="display:table-cell">idUser</div>
                <div style="display:table-cell">description</div>
            </div>

            <div class="table-row-group" style="display:table-row-group">
                <audit-row v-for="audit in itemList"
                            :key="audit.id"
                            :audit="audit">
                </audit-row>
            </div>

        </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import AuditRow from 'components/audits/AuditRow.vue';
    import Audit from "components/audits/Audit.ts";

    @Component({
        name: 'AuditList',
        components:{
            AuditRow
        }
    })
    export default class AuditList extends Vue {
        @Prop({default () : Array<Audit> { return [] }}) readonly auditList: Array<Audit>;
        itemList: Array<Audit> = this.auditList;
        searchValue : string | number = null;

        // update(){
        //     this.auditList.forEach(value => this.itemList.push(value));
        // }

        searchHistory(){
            if(this.searchValue === null || this.searchValue === "")
                this.itemList = new Array<Audit>();
            else if(!isNaN(Number(this.searchValue))){
                this.searchHistoryBy("/banner");
            }else {
                this.searchHistoryBy("/username");
            }
        }
        searchHistoryBy(pathURL : string){
            this.itemList = new Array<Audit>();
            this.$resource('/audit/list' + pathURL + '{/id}').get({id: this.searchValue}).then(result =>
                result.json().then((data : Audit[]) => {
                    data.forEach((audit: Audit) => this.itemList.push(audit))
                })
            );
        }





    }

</script>

<style scoped>

</style>