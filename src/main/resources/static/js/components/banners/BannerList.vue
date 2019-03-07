<template>
    <div>
        <banner-form :banners="banners" :bannerAttr="banner"/>
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
                    <banner-row v-for="banner in banners"
                                :key="banner.id"
                                :banner="banner"
                                :editBanner="editBanner"
                                :deleteBanner="deleteBanner"
                                :banners="banners"/>
                <!--</draggable>-->
            </tbody>
        </table>
    </div>
</template>

<script>
    // import Vue from 'vue'
    // import Component from "vue-class-component";
    import BannerRow from 'components/banners/BannerRow.vue'
    import BannerForm from 'components/banners/BannerForm.vue'

    // @Component({
    //     props: ['banners'],
    //     components:{
    //         BannerRow,
    //         BannerForm
    //     }
    // })
    // export default class BannerList extends Vue{
    //
    //     banner: BannerRow = null;
    //
    //     constructor(){
    //         super();
    //         this.$resource('/banner{/id}').get().then(result =>
    //             result.json().then(data =>
    //                 data.forEach(banner => this.banners.push(banner))
    //             )
    //         )
    //     }
    //
    //     editBanner(banner) {
    //         this.banner = banner;
    //     }
    //
    //     deleteBanner(banner){
    //         if (banner.activity === false) {
    //             this.$resource('/banner{/id}').remove({id: banner.id}).then(result => {
    //                 if (result.ok) {
    //                     this.banners.splice(this.banners.indexOf(banner), 1);
    //                 }
    //             });
    //         } else {
    //             var dataBanner = {
    //                 imgSrc: banner.imgSrc,
    //                 width: banner.width,
    //                 height: banner.height,
    //                 targetUrl: banner.targetUrl,
    //                 langId: banner.langId,
    //                 priority: banner.priority,
    //                 activity: false
    //             };
    //             this.$resource('/banner{/id}').update({id: banner.id}, dataBanner).then(result =>
    //                 result.json().then(data => {
    //                     banner.activity = false;
    //                 })
    //             );
    //         }
    //     }
    //
    // }

    export default {
        name: "BannerList",
        props: ['banners'],
        components:{
            BannerRow,
            BannerForm
        },
        data() {
            return {
                banner: null
            }
        },
        created() {
            this.$resource('/banner{/id}').get().then(result =>
                result.json().then(data =>
                    data.forEach(banner => this.banners.push(banner))
                )
            )
        },
        methods: {
            editBanner(banner) {
                this.banner = banner;
            },
            deleteBanner(banner){
                if (banner.activity === false) {
                    this.$resource('/banner{/id}').remove({id: banner.id}).then(result => {
                        if (result.ok) {
                            this.banners.splice(this.banners.indexOf(banner), 1);
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
                        result.json().then(data => {
                            banner.activity = false;
                        })
                    );
                }
            }
        }
    }
</script>

<style>

</style>