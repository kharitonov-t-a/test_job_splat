<template>
    <div class="table-cell" style="display:table-cell">
        <router-link :to="{
                path: '/banner/' + item.id,
                query:{
                    item: item
                }
            }" >

            <img :src="item.imgSrc" :width="100"/>

        </router-link>

    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import Banner from "../banners/Banner";

    @Component({
        name: 'PreviewRow'
    })
    export default class PreviewRow extends Vue {
        @Prop() readonly item : Banner;
    }
</script>

<style scoped>

</style>