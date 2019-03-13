<!--suppress ALL -->
<template>
    <div>
        <banner-form :bannerAttr="bannerAttr"
                     :bannerAttrChange="bannerAttrChange"
                     :isSortBanners="isSortBanners"
                     :localeList="localeList"
                     v-on:saveBanner="saveBanner($event)"/>
        <input type="button" v-bind:value="isSortBannersButtonText" @click="sortBanner"/>
        <select v-model="selectedLocaleId" @change="filterBanner" :disabled="isSortBanners">
            <option v-bind:value="0">All</option>
            <option v-for="locale in localeList" v-bind:value="locale.id">
                {{ locale.name }}
            </option>
        </select>
        <div class="drop list" style="display:table"> <!--v-drag-and-drop:options="options"-->
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
                            v-on:downBanner="downBanner(banner)">
                </banner-row>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import BannerRow from 'components/banners/BannerRow.vue';
    import BannerForm from 'components/banners/BannerForm.vue';
    import Banner from 'components/banners/Banner.ts';
    import Locale from "components/locales/Locale.ts";

    @Component({
        name: 'BannerList',
        components:{
            BannerRow,
            BannerForm
        }/*,
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
        }*/
    })
    export default class BannerList extends Vue {

        bannerList: Array<Banner> = new Array<Banner>();
        @Prop() totalBannerList: Array<Banner>;
        @Prop() readonly localeList!: Array<Locale>;
        bannerAttr: Banner = new Banner();
        bannerAttrChange: Boolean = true;
        isSortBanners : boolean = false;
        isSortBannersButtonText : string = "Sort banners!";
        selectedLocaleId : number = 0;

        constructor(){
            super();
        }
        mounted(){
            this.filterBanner();
        }
        
        filterBanner(){
            let selectedLocaleId = this.selectedLocaleId;
            this.bannerList = this.totalBannerList.filter(function (banner) {

                if(selectedLocaleId===0)
                    return true;
                else
                    return banner.langId === selectedLocaleId;

            }).sort((a, b) => a.priority>b.priority?1:-1);
        }

        static getIndex(list : Array<Banner>, id : Number) {
            for (var i = 0; i < list.length; i++) {
                if (list[i].id === id) {
                    return i;
                }
            }
            return -1;
        }

        public editBanner(banner: Banner) {
            this.clearForm();
            this.bannerAttr = banner;
        }

        clearForm(){
            this.bannerAttr = null;
            this.bannerAttrChange = !this.bannerAttrChange;
        }

        public deleteBanner(banner : Banner){
            this.clearForm();
            if (banner.activity === false) {
                this.$resource('/banner{/id}').remove({id: banner.id}).then(result => {
                    if (result.ok) {
                        this.totalBannerList.splice(this.bannerList.indexOf(banner), 1);
                        this.filterBanner();
                    }
                },
                reason => alert("Error!"));
            } else {
                this.$resource('/banner/delete{/id}').update({id: banner.id}, {}).then(result =>
                    result.json().then((data : Object) => {
                        banner.activity= false;
                        this.clearForm();
                    }),
                    reason => alert("Error!")
                );
            }
        }

        public saveBanner(banner : Banner){

            let formData = new FormData();
            formData.append('image', banner.imgFile);
            formData.append('imgSrc', banner.imgSrc);
            formData.append('width', banner.width.toString());
            formData.append('height', banner.height.toString());
            formData.append('targetUrl', banner.targetUrl);
            formData.append('langId', banner.langId.toString());
            formData.append('activity', banner.activity.toString());

            if (banner.id !== null) {
                formData.append('priority', banner.priority.toString());
                this.$resource('/banner{/id}').update({id: banner.id}, formData).then(result =>
                    result.json().then((data : Banner) => {
                        const index = BannerList.getIndex(this.totalBannerList, data.id);
                        this.totalBannerList.splice(index, 1, data);
                        this.clearForm();
                        this.filterBanner();
                    }),
                    reason => alert("Error!")
                );
            } else {
                this.$resource('/banner{/id}').save({}, formData).then(result =>
                    result.json().then((data : Banner) => {
                        this.totalBannerList.push(data);
                        this.clearForm();
                        this.filterBanner();
                    }),
                    reason => alert("Save error! There is no image!"))
            }
        }

        public upBanner(banner: Banner) {
            this.swapTwoBanners(banner, -1);
            this.clearForm()
        }
        public downBanner(banner: Banner) {
            this.swapTwoBanners(banner, 1);
            this.clearForm()
        }

        private swapTwoBanners(banner: Banner, indent : number){
            const index1 = BannerList.getIndex(this.bannerList, banner.id);
            const index2 = index1 + indent;
            const currentPriority = banner.priority;
            this.bannerList[index1].priority = this.bannerList[index2].priority;
            this.bannerList[index2].priority = currentPriority;
            this.bannerList.splice(index1, 1, this.bannerList[index2]);
            this.bannerList.splice(index2, 1, banner);
        }

        sortBanner(){
            this.clearForm();
            if(this.isSortBanners){
                this.$resource('/banner{/id}').update(this.bannerList).then(result =>
                    result.json().then((data : Object) => {
                        this.filterBanner();
                        this.isSortBanners = false;
                        this.isSortBannersButtonText = "Sort banners!!"
                    }),
                    reason => alert("Error!")
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