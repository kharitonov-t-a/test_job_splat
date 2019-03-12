<template>
    <div>
        <div v-if="!image">
            <h2>Select an image</h2>
            <input type="file" @change="onFileChange">
        </div>
        <div v-else>
            <img :src="image" width="100"/>
            <button @click="removeImage">Remove image</button>
        </div>
        <input type="text" placeholder="width" v-model="banner.width"/>
        <input type="text" placeholder="height" v-model="banner.height"/>
        <input type="text" placeholder="targetUrl" v-model="banner.targetUrl"/>
        <input type="text" placeholder="langId" v-model="banner.langId"/>
        <input type="button" value="Save" @click="saveBanner(banner)" :disabled="isSortBanners"/>
        <input type="button" value="Cancel" @click="clearForm"/>
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
        @Prop() readonly isSortBanners : boolean;

        banner : BannerRow = new BannerRow();
        image : any = null;

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
                this.image = this.bannerAttr.imgSrc;
            }else{
                this.clearForm();
            }
        }

        saveBanner() {
            this.changeableBanner.$emit('saveBanner', this.banner);
        }

        clearForm(){
            this.banner.id = null;
            this.banner.imgSrc = null;
            this.banner.width = null;
            this.banner.height = null;
            this.banner.targetUrl = null;
            this.banner.langId = null;
            this.banner.priority = null;
            this.banner.activity = true;
            this.image = '';
        }

        onFileChange(e : any) {
            let files = e.target.files || e.dataTransfer.files;
            if (!files.length)
                return;
            this.banner.imgFile = files[0];
            this.createImage(files[0]);

        }
        createImage(file : any) {
            let reader : FileReader = new FileReader();
            let vm = this;

            reader.onload = ((event: Event) => {
                vm.image = reader.result;
            });

            reader.readAsDataURL(file);
        }
        removeImage () {
            this.image = '';
        }

    }
</script>

<style scoped>

</style>