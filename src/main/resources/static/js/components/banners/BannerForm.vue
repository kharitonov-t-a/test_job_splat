<template>
    <div>
        <input type="text" placeholder="imgSrc" v-model="banner.imgSrc"/>
        <input type="text" placeholder="width" v-model="banner.width"/>
        <input type="text" placeholder="height" v-model="banner.height"/>
        <input type="text" placeholder="targetUrl" v-model="banner.targetUrl"/>
        <input type="text" placeholder="langId" v-model="banner.langId"/>
        <input type="text" placeholder="priority" v-model="banner.priority"/>
        <input type="text" placeholder="activity" v-model="banner.activity"/>
        <input type="button" value="Save" @click="saveBanner(banner)"/>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import BannerRow from 'components/banners/BannerRow.vue'


    @Component
    export default class BannerForm extends Vue {

        @Prop() bannerAttr!: BannerRow;
        @Prop() readonly bannerList!: Array<BannerRow>;
        @Prop() readonly changeableBanner!: Vue;
        banner : BannerRow = new BannerRow();

        @Watch('bannerAttr')
        getBannerAttr(){
            if(this.bannerAttr !== null){
                this.banner.id = this.bannerAttr.id;
                this.banner.imgSrc = this.bannerAttr.imgSrc;
                this.banner.width = this.bannerAttr.width;
                this.banner.height = this.bannerAttr.height;
                this.banner.targetUrl = this.bannerAttr.targetUrl;
                this.banner.langId = this.bannerAttr.langId;
                this.banner.priority = this.bannerAttr.priority;
                this.banner.activity = this.bannerAttr.activity;
            }else{
                this.banner.id = null;
                this.banner.imgSrc = null;
                this.banner.width = null;
                this.banner.height = null;
                this.banner.targetUrl = null;
                this.banner.langId = null;
                this.banner.priority = null;
                this.banner.activity = null;
            }
        }

        saveBanner() {
            this.changeableBanner.$emit('saveBanner', this.banner);
        }
    }
</script>

<style scoped>

</style>