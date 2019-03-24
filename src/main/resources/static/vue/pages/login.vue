<template>
    <div class="dynamic-content">
        <input type="text" placeholder="username" v-model="username"/>
        <input type="password" placeholder="password" v-model="password"/>
        <input type="button" value="Log In" @click="logIn"/>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    @Component({
        name: "login"
    })
    export default class login extends Vue {
        username : string = null;
        password : string = null;
        logIn(){
            let formData = new FormData();
            formData.append("username", this.username);
            formData.append("password", this.password);
            this.$resource('login').save({}, formData).then(value => {
                this.$resource('user').get({}, formData).then(value1 => this.$root.$data.profile = value1.data);
                this.$router.push('/');
            });
        }
    }
</script>

<style lang="less" scoped>
    .dynamic-content{
        padding-top: 10%;
    }
</style>