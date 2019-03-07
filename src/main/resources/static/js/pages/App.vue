<template>
    <banners-list :banners="banners"/>
</template>

<script lang="ts">
    import Vue from 'vue'
    import Component from "vue-class-component";
    import BannersList from 'components/banners/BannerList.vue'

    @Component({
        components:{
            BannersList
        }
    })
    export default class App extends Vue{
        banners: any[] = [];
    }

    // export default{
    //     name: "App",
    //     components:{
    //         BannersList
    //     },
    //     data() {
    //         return {
    //             banners: []
    //         }
    //     }
    // }
</script>

<style>

</style>