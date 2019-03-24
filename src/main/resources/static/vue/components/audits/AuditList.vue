<template>
    <div class="content-container">

        <h1 v-if="!showSearchForm">History of banner changes</h1>

        <div class="flex-container">

            <div class="form" v-if="showSearchForm">
                <div class="field">
                    <label for="searchValue">Username or banner ID</label>
                    <input id="searchValue" type="text" placeholder="Search value" v-model="searchValue"/>
                    <input type="button" value="Search" @click="searchHistory"/>
                </div>
            </div>

            <div class="flex-table" style="display:table">


                <div class="table-row table-header" style="display:table-row">
                    <div style="display:table-cell">id</div>
                    <div style="display:table-cell">date</div>
                    <div style="display:table-cell">crud</div>
                    <div style="display:table-cell">idBanner</div>
                    <div style="display:table-cell">idUser</div>
                    <div style="display:table-cell">description</div>
                </div>

                <div class="table-row-group" style="display:table-row-group">
                    <audit-row v-for="(audit, index) in itemList"
                               :key="audit.id"
                               :audit="audit"
                               :index="index">
                    </audit-row>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import AuditRow from 'components/audits/AuditRow.vue';
    import Audit from "components/audits/Audit.ts";

    @Component({
        name: 'AuditList',
        components: {
            AuditRow
        }
    })
    export default class AuditList extends Vue {
        @Prop({
            default(): Array<Audit> {
                return []
            }
        }) readonly auditList: Array<Audit>;
        itemList: Array<Audit> = this.auditList;
        searchValue: string | number = null;
        @Prop({
            default(): boolean {
                return true
            }
        }) readonly showSearchForm: boolean;
        // update(){
        //     this.auditList.forEach(value => this.itemList.push(value));
        // }

        @Watch("auditList")
        changeAuditList() {
            this.itemList = this.auditList;
        }

        searchHistory() {
            if (this.searchValue === null || this.searchValue === "")
                this.itemList = new Array<Audit>();
            else if (!isNaN(Number(this.searchValue))) {
                this.searchHistoryBy("/banner");
            } else {
                this.searchHistoryBy("/username");
            }
        }

        searchHistoryBy(pathURL: string) {
            this.itemList = new Array<Audit>();
            this.$resource('audit/list' + pathURL + '{/id}').get({id: this.searchValue}).then(result =>
                result.json().then((data: Audit[]) => {
                    data.forEach((audit: Audit) => this.itemList.push(audit))
                })
            );
        }
    }

</script>

<style lang="less" scoped>

    .flex-container{
        display: flex;
        flex-direction: column;
    }

    .flex-table{
        width: 100%;
    }

    .form{
        width: 100%;
    }

    .content-container .flex-container{
        display: flex;
        flex-direction: column;
    }

    .field{
        text-align: center;
        margin-bottom: 10px;
    }

    .field label {
        float: none;
    }

    h1 {
        font-size: 2em;
    }

</style>