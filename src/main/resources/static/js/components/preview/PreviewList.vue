<template>
    <div class="drop list" style="display:table">
        <div class="table-row" style="display:table-row">
            <preview-row v-for="item in itemList"
                       :key="item.id"
                       :item="item">
            </preview-row>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import Banner from "../banners/Banner";
    import PreviewRow from "components/preview/PreviewRow.vue";

    @Component({
        name: 'PreviewList',
        components:{
            PreviewRow
        }
    })
    export default class AuditList extends Vue {

        itemList: Array<Banner> = [];


        beforeCreate(){
            this.$resource('/banner/list').get().then(result => {
                result.json().then((data: Banner[]) => {
                    data.forEach((banner: Banner) => {
                        this.itemList.push(banner);
                    });
                });
            });
        }

    }
</script>

<style scoped>

</style>