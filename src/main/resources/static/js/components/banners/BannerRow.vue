<template>
    <div class="table-row" style="display:table-row">
        <div style="display:table-cell">
            <span class="handle">
                <i class="glyphicon glyphicon-menu-hamburger"></i>
            </span>
        </div>
        <div style="display:table-cell">{{ banner.id }}</div>
        <div style="display:table-cell"><img :src="banner.imgSrc" width="100"/></div>
        <div style="display:table-cell">{{ banner.width }}</div>
        <div style="display:table-cell">{{ banner.height }}</div>
        <div style="display:table-cell">{{ banner.targetUrl }}</div>
        <div style="display:table-cell">{{ banner.langId }}</div>
        <div style="display:table-cell">{{ banner.activity }}</div>
        <div style="display:table-cell">
            <template v-if="!isSortBanners">
                <span>
                   <input type="button" value="Edit" @click="editBanner"/>
                </span>
            </template>
        </div>
        <div style="display:table-cell">
            <template v-if="!isSortBanners">
                <span>
                    <input type="button" value="Delete" @click="deleteBanner"/>
                </span>
            </template>
        </div>
        <div style="display:table-cell">
            <template v-if="isSortBanners">
                <span>
                   <input type="button" value="Up" @click="upBanner"
                          :disabled="isFirstUpButton()"/>
                </span>
            </template>
        </div>
        <div style="display:table-cell">
            <template v-if="isSortBanners">
                <span>
                    <input type="button" value="Down" @click="downBanner"
                           :disabled="isLastDownButton()"/>
                </span>
            </template>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import BannerList from 'components/banners/BannerList.vue';

    @Component
    export default class BannerRow extends Vue{

        @Prop() readonly banner!: BannerRow;
        @Prop() readonly bannerList!: Array<BannerRow>;
        @Prop() readonly isSortBanners!: boolean;

        isLastDownButton(){
            return BannerList.getIndex(this.bannerList, this.banner.id)===this.bannerList.length - 1;
        }
        isFirstUpButton(){
            return BannerList.getIndex(this.bannerList, this.banner.id)===0;
        }

        constructor(){
            super();
        }


        static copyBanner(bannerRowFrom : BannerRow, bannerRowTo : BannerRow){
            bannerRowTo.id = bannerRowFrom.id;
            bannerRowTo.imgSrc = bannerRowFrom.imgSrc;
            bannerRowTo.width = bannerRowFrom.width;
            bannerRowTo.height = bannerRowFrom.height;
            bannerRowTo.targetUrl = bannerRowFrom.targetUrl;
            bannerRowTo.langId = bannerRowFrom.langId;
            bannerRowTo.priority = bannerRowFrom.priority;
            bannerRowTo.activity = bannerRowFrom.activity;
        }

        id : number | null = null;
        imgSrc : string | null = null;
        imgFile : any | null = null;
        width : number | null = null;
        height : number| null = null;
        targetUrl : string | null = null;
        langId : number | null = null;
        priority : number | null = null;
        activity : boolean = true;

        editBanner() {
            this.$emit('editBanner')
        }
        deleteBanner() {
            this.$emit('deleteBanner')
        }
        upBanner() {
            this.$emit('upBanner')
        }
        downBanner() {
            this.$emit('downBanner')
        }
    }
</script>

<style scoped>

</style>