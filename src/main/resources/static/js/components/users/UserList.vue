<template>
    <div>

        <user-form :userAttr="userAttr"
                     :userAttrChange="userAttrChange"
                     v-on:saveUser="saveUser($event)"/>

        <div class="drop list" style="display:table">

            <div style="display:table-row">
                <div style="display:table-cell">id</div>
                <div style="display:table-cell">username</div>
                <div style="display:table-cell">activity</div>
                <div style="display:table-cell"></div>
                <div style="display:table-cell"></div>
            </div>

            <div class="table-row-group" style="display:table-row-group">
                <user-row v-for="user in userList"
                            :key="user.id"
                            :user="user"
                            v-on:activateUser="activateUser(user)"
                            v-on:editUser="editUser(user)"
                            v-on:deleteUser="deleteUser(user)"
                          v-on:showHistory="showHistory(user)">
                </user-row>
            </div>

        </div>
        <audit-list
                v-if="showAuditTab"
                class="col-sm-10"
                :auditList="auditList"
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

    @Component({
        name: 'UserList',
        components:{
            UserRow,
            UserForm,
            AuditList
        }
    })
    export default class UserList extends Vue {
        @Prop() userList!: Array<User>;
        userAttr: User = new User();
        userAttrChange: Boolean = true;
        showAuditTab : boolean = false;
        auditList: Array<Audit> = new Array<Audit>();

        constructor(){
            super();
        }

        public saveUser(user : User){

            if (user.id !== null) {
                this.$resource('/user{/id}').update({id: user.id}, user).then(result =>
                        result.json().then((data : User) => {
                            const index = UserList.getIndex(this.userList, data.id);
                            this.userList.splice(index, 1, data);
                            this.clearForm();
                        }),
                    reason => alert("Error!")
                );
            } else {
                this.$resource('/user{/id}').save({}, user).then(result =>
                        result.json().then((data : User) => {
                            this.userList.push(data);
                            this.clearForm();
                        }),
                    reason => alert("Save error!"))
            }
        }

        public editUser(user: User) {
            this.clearForm();
            this.userAttr = user;
        }

        public deleteUser(user : User){
            this.clearForm();
            if (user.activity === false) {
                this.$resource('/user{/id}').remove({id: user.id}).then(result => {
                        if (result.ok) {
                            this.userList.splice(this.userList.indexOf(user), 1);
                        }
                    },
                    reason => alert("Error!"));
            } else {
                this.switchActivity(user, false);
            }
        }

        public activateUser(user : User){
            this.switchActivity(user, true);
        }

        private switchActivity(user : User, newActivityState : boolean){
            let formData = new FormData();
            formData.append('newActivityState', newActivityState.toString());
            this.$resource('/user/delete{/id}').update({id: user.id}, formData).then(result =>
                    result.json().then((data : Object) => {
                        user.activity= newActivityState;
                    }),
                reason => alert("Error!")
            );
        }

        private clearForm(){
            this.userAttr = null;
            this.userAttrChange = !this.userAttrChange;
        }

        static getIndex(list : Array<User>, id : Number) {
            for (var i = 0; i < list.length; i++) {
                if (list[i].id === id) {
                    return i;
                }
            }
            return -1;
        }

        showHistory(user : User){
            this.auditList = new Array<Audit>();
            this.$resource('/audit/list/user{/id}').get({id: user.id}).then(result =>
                result.json().then((data : Audit[]) => {
                    data.forEach((audit: Audit) => this.auditList.push(audit))
                    this.showAuditTab = true;
                })
            );
        }

    }

</script>

<style scoped>

</style>