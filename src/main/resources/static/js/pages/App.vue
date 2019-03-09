<template>
    <banners-list :banners="banners"/>
</template>

<script lang="ts">
    import { Vue, Component, Prop } from "vue-property-decorator";

    import BannersList from 'components/banners/BannerList.vue'

    @Component({
        components:{
            BannersList
        }
    })
    export default class App extends Vue{
        banners: BannersList = new BannersList;
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