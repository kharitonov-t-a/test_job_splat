<template>
    <div>
        <banner-form :bannerList="bannerList"
                     :bannerAttr="bannerAttr"
                     :changeableBanner="changeableBanner"/>
        <input type="button" v-bind:value="isSortBannersButtonText" @click="sortBanner"/>
        <div class="drop list" style="display:table" v-drag-and-drop:options="options">
            <div style="display:table-row">
                <div style="display:table-cell"></div>
                <div style="display:table-cell">id</div>
                <div style="display:table-cell">imgSrc</div>
                <div style="display:table-cell">width</div>
                <div style="display:table-cell">height</div>
                <div style="display:table-cell">targetUrl</div>
                <div style="display:table-cell">langId</div>
                <div style="display:table-cell">priority</div>
                <div style="display:table-cell">activity</div>
                <div style="display:table-cell"></div>
                <div style="display:table-cell"></div>
                <div style="display:table-cell"></div>
                <div style="display:table-cell"></div>
            </div>
            <div class="table-row-group" style="display:table-row-group">
                <banner-row v-for="banner in bannerList"
                            :key="banner.id"
                            :banner="banner"
                            :bannerList="bannerList"
                            :isSortBanners="isSortBanners"
                            v-on:editBanner="editBanner(banner)"
                            v-on:deleteBanner="deleteBanner(banner)"
                            v-on:upBanner="upBanner(banner)"
                            v-on:downBanner="downBanner(banner)
">
                </banner-row>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import BannerRow from 'components/banners/BannerRow.vue';
    import BannerForm from 'components/banners/BannerForm.vue';

    @Component({
        components:{
            BannerRow,
            BannerForm
        },
        data (){
            return {
                options:{
                    dropzoneSelector: 'div.table-row-group',
                    draggableSelector: 'div.table-row',
                    handlerSelector: 'span.handle',
                    multipleDropzonesItemsDraggingEnabled: true,
                    showDropzoneAreas: true,
                    onDrop: function(event : any) {
                        console.log(event.nativeEvent);},
                    onDragstart: function(event : any) {console.log(event.nativeEvent);},
                    onDragend: function(event : any) {console.log(event.nativeEvent);}
                }
            }
        }
    })
    export default class BannerList extends Vue {

        bannerList: Array<BannerRow> = new Array<BannerRow>();
        @Prop() readonly banners!: BannerList;
        changeableBanner: Vue = new Vue();
        bannerAttr: BannerRow = new BannerRow();
        isSortBanners : boolean = false;
        isSortBannersButtonText : string = "Sort banners!";


        constructor(){
            super();

            this.$http.get('/banner{/id}').then(result =>
                result.json().then((data : BannerRow[]) =>
                    data.forEach((banner : BannerRow)=> this.bannerList.push(banner))
                )
            );

            this.changeableBanner.$on('saveBanner', (banner : BannerRow) => {
                this.saveBanner(banner);
            });

        }

        static getIndex(list : Array<BannerRow>, id : Number) {
            for (var i = 0; i < list.length; i++) {
                if (list[i].id === id) {
                    return i;
                }
            }
            return -1;
        }

        public editBanner(banner: BannerRow) {
            this.bannerAttr = null;
            this.$nextTick(() =>{
                this.bannerAttr = banner;
            });
        }

        public deleteBanner(banner : BannerRow){
            this.bannerAttr = null;
            if (banner.activity === false) {
                this.$resource('/banner{/id}').remove({id: banner.id}).then(result => {
                    if (result.ok) {
                        this.bannerList.splice(this.bannerList.indexOf(banner), 1);
                    }
                });
            } else {
                const bannerDel = {
                    imgSrc: banner.imgSrc,
                    width: banner.width,
                    height: banner.height,
                    targetUrl: banner.targetUrl,
                    langId: banner.langId,
                    priority: banner.priority,
                    activity: false,
                };
                this.$resource('/banner{/id}').update({id: banner.id}, bannerDel).then(result =>
                    result.json().then((data : Object) => {
                        banner.activity = false;
                    })
                );
            }
        }

        public saveBanner(banner : BannerRow){
            if (banner.id !== null) {
                this.$resource('/banner{/id}').update({id: banner.id}, (banner.$data as BannerRow)).then(result =>
                    result.json().then((data : BannerRow) => {
                        const index = BannerList.getIndex(this.bannerList, data.id);
                        this.bannerList.splice(index, 1, data);
                        this.bannerAttr = null;
                    })
                );
            } else {
                banner.priority = this.bannerList.length;
                this.$resource('/banner{/id}').save({}, (banner.$data as BannerRow)).then(result =>
                    result.json().then((data : BannerRow) => {
                        this.bannerList.push(data);
                        this.bannerAttr = null;
                    })
                )
            }
        }

        public upBanner(banner: BannerRow) {
            const index = BannerList.getIndex(this.bannerList, banner.id);
            this.bannerList.splice(index, 1, this.bannerList[index-1]);
            this.bannerList.splice(index-1, 1, banner);
            this.bannerAttr = null;
        }
        public downBanner(banner: BannerRow) {
            const index = BannerList.getIndex(this.bannerList, banner.id);
            this.bannerList.splice(index, 1, this.bannerList[index+1]);
            this.bannerList.splice(index+1, 1, banner);
            this.bannerAttr = null;
        }

        sortBanner(){
            if(this.isSortBanners){
                this.bannerList.forEach((value, index) => value.priority = index);
                this.$resource('/banner{/id}').update(this.bannerList).then(result =>
                    result.json().then((data : Object) => {
                        alert(data);
                        this.isSortBanners = false;
                        this.isSortBannersButtonText = "Sort banners!!"
                    })
                );

            }else{
                this.isSortBanners = true;
                this.isSortBannersButtonText = "Save!"
            }
        }

    }
</script>

<style scoped>

</style>