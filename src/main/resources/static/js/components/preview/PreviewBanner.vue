<template>
    <div class="table-row" style="display:table-row">
        <div style="display:table-cell">{{ $route.query.item.id }}
            <a :href="$route.query.item.targetUrl">
                <img :src="$route.query.item.imgSrc" :width="$route.query.item.width" :height="$route.query.item.height"/>
            </a>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import Banner from "../banners/Banner";

    @Component({
        name: 'PreviewBanner'
    })
    export default class PreviewBanner extends Vue {

    }
</script>

<style scoped>

</style>