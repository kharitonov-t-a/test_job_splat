<template>
    <div class="table-row" style="display:table-row">
        <div style="display:table-cell">{{ this.item.id }}
            <a :href="item.targetUrl">
                <img :src="this.$root.$data.baseURL + '/image' + (item.imgSrc.startsWith('/')?'':'/') + item.imgSrc" :width="item.width" :height="item.height"/>
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
        item : Banner = new Banner();
        mounted(){
            this.item = (<Banner><undefined>this.$router.currentRoute.query.item);
            if(this.item === undefined || this.item.id === undefined){
                this.$resource('preview/{id}').get({id: this.$router.currentRoute.params.id}).then(result => {
                    result.json().then((data: Banner) => {
                        this.item = data;
                    });
                });
            }
        }
    }
</script>

<style scoped>

</style>