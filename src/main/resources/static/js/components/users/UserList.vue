<template>
    <div class="content-container">

        <div class="flex-container">

            <user-form :itemAttr="itemAttr"
                       :itemAttrChange="itemAttrChange"
                       :errorsForm="errorsForm"
                       v-on:saveItem="saveItem($event, $event.id)"/>

            <div class="flex-table" style="display:table">

                <div class="table-row table-header" style="display:table-row">
                    <div style="display:table-cell">id</div>
                    <div style="display:table-cell">username</div>
                    <div style="display:table-cell">activity</div>
                    <div style="display:table-cell"></div>
                    <div style="display:table-cell"></div>
                    <div style="display:table-cell"></div>
                </div>

                <div class="table-row-group" style="display:table-row-group">
                    <user-row v-for="(item, index) in totalItemList"
                              :key="item.id"
                              :item="item"
                              :index="index"
                              v-on:activateItem="activateItem(item)"
                              v-on:editItem="editItem(item)"
                              v-on:deleteItem="deleteItem(item)"
                              v-on:showHistory="showHistory(item)">
                    </user-row>
                </div>
            </div>
        </div>
        <audit-list
                v-if="showAuditTab"
                class="col-sm-10"
                :auditList="auditList"
                :showSearchForm="false"
        ></audit-list>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import UserForm from 'components/users/UserForm.vue';
    import UserRow from 'components/users/UserRow.vue';
    import User from "components/users/User.ts";
    import AuditList from 'components/audits/AuditList.vue';
    import Audit from 'components/audits/Audit.ts';
    import GenericListImpl from "../generics/implementations/GenericListImpl";

    @Component({
        name: 'UserList',
        components: {
            UserRow,
            UserForm,
            AuditList
        }
    })
    export default class UserList extends GenericListImpl<User> {

        filterItem(): void {
        }

    }

</script>

<style scoped>

</style>