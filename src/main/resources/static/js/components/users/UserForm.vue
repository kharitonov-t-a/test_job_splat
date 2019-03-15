<template>
    <div>
        <input type="text" placeholder="username" v-model="user.username"/>
        <input type="text" placeholder="password" v-model="user.password"/>
        <input type="button" value="Save" @click="saveUser"/>
        <input type="button" value="Cancel" @click="clearForm"/>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import User from "components/users/User.ts";

    @Component({
        name: 'UserForm'
    })
    export default class UserForm extends Vue {

        @Prop() userAttr!: User;
        user: User = new User();
        @Prop() readonly userAttrChange : boolean;

        saveUser() {
            this.$emit('saveUser', this.user);
        }

        @Watch('userAttrChange')
        getUserAttr(){
            if(this.userAttr !== null){
                this.user.copyUser(this.userAttr);
            }else{
                this.clearForm();
            }
        }

        clearForm(){
            this.user.id = null;
            this.user.username = null;
            this.user.password = null;
            this.user.activity = true;
        }
    }
</script>

<style scoped>

</style>