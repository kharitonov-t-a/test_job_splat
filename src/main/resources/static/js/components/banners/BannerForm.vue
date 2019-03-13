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
        <select v-model="banner.langId">
            <option v-for="locale in localeList" v-bind:value="locale.id">
                {{ locale.name }}
            </option>
        </select>
        <input type="button" value="Save" @click="saveBanner" :disabled="isSortBanners"/>
        <input type="button" value="Cancel" @click="clearForm"/>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import Banner from "components/banners/Banner.ts"
    import Locale from "components/locales/Locale.ts";

    @Component
    export default class BannerForm extends Vue {

        @Prop() bannerAttr!: Banner;
        @Prop() readonly localeList!: Array<Locale>;
        @Prop() readonly isSortBanners : boolean;
        @Prop() readonly bannerAttrChange : boolean;

        banner : Banner = new Banner();
        image : any = null;

        /**
         * set locale only for create new banner
         */
        mounted(){//updated
            if(this.banner.langId === null)
                this.banner.langId = this.localeList[0].id;
        }

        @Watch('bannerAttrChange')
        getBannerAttr(){
            if(this.bannerAttr !== null){
                this.banner.copyBanner(this.bannerAttr);
                this.image = this.bannerAttr.imgSrc;
            }else{
                this.clearForm();
            }
        }

        saveBanner() {
            this.$emit('saveBanner', this.banner);
        }

        clearForm(){
            this.banner.clean();
            this.banner.langId = this.localeList[0].id;
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