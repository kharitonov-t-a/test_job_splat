<template>
    <div>
        <input type="text" placeholder="username" v-model="item.username"/>
        <div class="errorForm">{{errorsFormMap.get("username")}}</div>

        <input type="text" placeholder="password" v-model="item.password"/>
        <div class="errorForm">{{errorsFormMap.get("password")}}</div>

        <input type="button" value="Save" @click="saveItem"/>
        <input type="button" value="Cancel" @click="cleanForm"/>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import User from "components/users/User.ts";
    import GenericFormImpl from "../generics/implementations/GenericFormImpl";

    @Component({
        name: 'UserForm'
    })
    export default class UserForm extends GenericFormImpl<User> {

        constructor(){
            super();
            this.item = new User();
        }

        checkItemBeforeSave(): boolean {
            return this.item.username != null && this.item.username.length != 0;
        }

    }
</script>

<style scoped>

</style>