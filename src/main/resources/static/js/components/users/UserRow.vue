<template>
    <div class="table-row" style="display:table-row">
        <div style="display:table-cell">{{ user.id }}</div>
        <div style="display:table-cell">{{ user.username }}</div>
        <div style="display:table-cell">{{ user.activity }}</div>
        <div style="display:table-cell">
            <span>
               <input type="button" value="Edit" @click="editUser"/>
            </span>
        </div>
        <div style="display:table-cell">
            <span>
                <input type="button" value="Delete" @click="deleteUser"/>
            </span>
        </div>
        <div style="display:table-cell" v-if="!user.activity">
            <span>
                <input type="button" value="Activate" @click="activateUser"/>
            </span>
        </div>
        <div style="display:table-cell">
                <span>
                   <input type="button" value="History" @click="showHistory"/>
                </span>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import User from "components/users/User.ts";

    @Component({
        name: 'UserRow'
    })
    export default class UserRow extends Vue {

        @Prop() readonly user!: User;

        editUser() {
            this.$emit('editUser')
        }

        deleteUser() {
            this.$emit('deleteUser')
        }

        activateUser() {
            this.$emit('activateUser')
        }

        showHistory() {
            this.$emit('showHistory')
        }
    }
</script>

<style scoped>

</style>