<template>
    <banners-list :banners="banners"/>
</template>

<script lang="ts">
    import { Vue, Component } from "vue-property-decorator";

    import BannersList from 'components/banners/BannerList.vue'

    @Component({
        components:{
            BannersList
        }
    })
    export default class App extends Vue{
        banners: BannersList = new BannersList;
    }
</script>

<style>
</style>