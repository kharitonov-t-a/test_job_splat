<template>
    <div id="app">
        <router-link to="/">Home</router-link>
        <router-link v-if="!this.$root.$data.profile" to="/login">Login</router-link>
        <a v-if="this.$root.$data.profile" @click="logout" href="#">Logout</a>
        <router-link v-if="this.$root.$data.profile" to="/admin">Admin panel</router-link>
        <router-view></router-view>
    </div>
</template>

<script lang="ts">

    import {Component, Vue} from 'vue-property-decorator';

    @Component({
        name: "App"
    })
    export default class App extends Vue {
        logout(){
            this.$resource('/logout').get({}).then(value => {
                this.$root.$data.profile = null;
                this.$router.push('/');
            });
        }
    }
</script>

<style>
</style>