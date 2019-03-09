<template>
    <div>
        <banner-form :bannerList="bannerList" :bannerAttr="bannerAttr"/>
        <table>
            <thead>
            <tr>
                <th></th>
                <th>id</th>
                <th>imgSrc</th>
                <th>width</th>
                <th>height</th>
                <th>targetUrl</th>
                <th>langId</th>
                <th>priority</th>
                <th>activity</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody class="list-group drag">
            <!--<draggable :list="banners" class="dragArea" :options="{handle:'.handle'}">-->
            <banner-row v-for="banner in bannerList"
                        :key="banner.id"
                        :banner="banner"
                        :changeableBanner="changeableBanner"/>
            <!--</draggable>-->
            </tbody>
        </table>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";

    import BannerRow from 'components/banners/BannerRow.vue'
    import BannerForm from 'components/banners/BannerForm.vue'

    @Component({
        components:{
            BannerRow,
            BannerForm
        }
    })
    export default class BannerList extends Vue {

        bannerList: Array<BannerRow> = new Array<BannerRow>();
        @Prop() readonly banners!: BannerList;
        changeableBanner: Vue = new Vue();
        bannerAttr: BannerRow = new BannerRow();

        constructor(){
            super();

            this.$http.get('/banner{/id}').then(result =>
                result.json().then((data : BannerRow[]) =>
                    data.forEach((banner : BannerRow)=> this.bannerList.push(banner))
                )
            )

            this.changeableBanner.$on('banner-edit', (bannerEdit : BannerRow) => {
                this.editBanner(bannerEdit);
            });
            this.changeableBanner.$on('banner-delete', (bannerEdit : BannerRow) => {
                this.deleteBanner(bannerEdit);
            });
        }

        private editBanner(banner: BannerRow) {
            this.bannerAttr = banner;
        }

        private deleteBanner(banner : BannerRow){
            if (banner.activity === false) {
                this.$resource('/banner{/id}').remove({id: banner.id}).then(result => {
                    if (result.ok) {
                        this.bannerList.splice(this.bannerList.indexOf(banner), 1);
                    }
                });
            } else {
                var dataBanner = {
                    imgSrc: banner.imgSrc,
                    width: banner.width,
                    height: banner.height,
                    targetUrl: banner.targetUrl,
                    langId: banner.langId,
                    priority: banner.priority,
                    activity: false
                };
                this.$resource('/banner{/id}').update({id: banner.id}, dataBanner).then(result =>
                    result.json().then((data : Object) => {
                        banner.activity = false;
                    })
                );
            }
        }

    }
</script>

<style>
</style>