<template>
    <div>
        <!--<banner-form :banners="banners" :bannerAttr="bannerAttr"/>-->
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
                        :bannerObj="bannerObj"
                        :banners="banners"/>
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
        // props: ['banners', 'editBanner', 'deleteBanner'],
        components:{
            BannerRow,
            BannerForm
        }
    })
    export default class BannerList extends Vue {

        bannerList: Array<BannerRow> = new Array<BannerRow>();
        @Prop() readonly banners!: BannerList;
        // changeBanner: BannerRow = new BannerRow();
        bannerObj: Vue = new Vue();

        constructor(){
            super();

            this.$http.get('/banner{/id}').then(result =>
                result.json().then((data : BannerRow[]) =>
                    data.forEach((banner : BannerRow)=> this.bannerList.push(banner))
                )
            )

            this.bannerObj.$on('banner-edit', (bannerEdit : BannerRow) => {
                this.editBanner(bannerEdit);
            });
            this.bannerObj.$on('banner-delete', (bannerEdit : BannerRow) => {
                this.deleteBanner(bannerEdit);
            });
            // this.$resource('/banner{/id}').get().then(result =>
            //     result.json().then(data =>
            //         data.forEach(banner => this.banners.push(banner))
            //     )
            // )
        }

        private editBanner(banner: BannerRow) {
            alert(banner.id);
            // this.changeBanner = new BannerRow();
            // this.ba = banner;
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

    // export default {
    //     name: "BannerList",
    //     props: ['banners'],
    //     components:{
    //         BannerRow,
    //         BannerForm
    //     },
    //     data() {
    //         return {
    //             banner: null
    //         }
    //     },
    //     created() {
    //
    //         var ss;
    //         ss = axios.get('/banner{/id}').then(response => {
    //             (<BannerList>response.data)
    //         })
    //
    //             .then(result =>
    //             result.json().then(data =>
    //                 data.forEach(banner => this.banners.push(banner))
    //             )
    //         )
    //     },
    //     methods: {
    //         editBanner(banner) {
    //             this.banner = banner;
    //         },
    //         deleteBanner(banner){
    //             if (banner.activity === false) {
    //                 this.$resource('/banner{/id}').remove({id: banner.id}).then(result => {
    //                     if (result.ok) {
    //                         this.banners.splice(this.banners.indexOf(banner), 1);
    //                     }
    //                 });
    //             } else {
    //                 var dataBanner = {
    //                     imgSrc: banner.imgSrc,
    //                     width: banner.width,
    //                     height: banner.height,
    //                     targetUrl: banner.targetUrl,
    //                     langId: banner.langId,
    //                     priority: banner.priority,
    //                     activity: false
    //                 };
    //                 this.$resource('/banner{/id}').update({id: banner.id}, dataBanner).then(result =>
    //                     result.json().then(data => {
    //                         banner.activity = false;
    //                     })
    //                 );
    //             }
    //         }
    //     }
    // }
</script>

<style>
</style>