<template>
    <div>
        <input type="text" placeholder="imgSrc" v-model="banner.imgSrc"/>
        <input type="text" placeholder="width" v-model="banner.width"/>
        <input type="text" placeholder="height" v-model="banner.height"/>
        <input type="text" placeholder="targetUrl" v-model="banner.targetUrl"/>
        <input type="text" placeholder="langId" v-model="banner.langId"/>
        <input type="text" placeholder="priority" v-model="banner.priority"/>
        <input type="text" placeholder="activity" v-model="banner.activity"/>
        <input type="button" value="Save" @click="saveB"/>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import BannerRow from './BannerRow.vue'

    function getIndex(list : Array<BannerRow>, id : Number) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].id === id) {
                return i;
            }
        }
        return -1;
    }

    @Component
    export default class BannerForm extends Vue {

        @Prop() bannerAttr!: BannerRow;
        @Prop() readonly bannerList!: Array<BannerRow>;
        banner : BannerRow = new BannerRow();

        @Watch('bannerAttr')
        getBannerAttr(){
            this.banner.id = this.bannerAttr.id;
            this.banner.imgSrc = this.bannerAttr.imgSrc;
            this.banner.width = this.bannerAttr.width;
            this.banner.height = this.bannerAttr.height;
            this.banner.targetUrl = this.bannerAttr.targetUrl;
            this.banner.langId = this.bannerAttr.langId;
            this.banner.priority = this.bannerAttr.priority;
            this.banner.activity = this.bannerAttr.activity;
        }

        saveB() {
            if (this.banner.id !== 0) {
                this.$resource('/banner{/id}').update({id: this.banner.id}, this.banner.$data).then(result =>
                    result.json().then((data : BannerRow) => {
                        const index = getIndex(this.bannerList, data.id);
                        this.bannerList.splice(index, 1, data);
                        this.banner = new BannerRow();
                    })
                );
            } else {
                this.$resource('/banner{/id}').save({}, this.banner.$data).then(result =>
                    result.json().then((data : BannerRow) => {
                        this.bannerList.push(data);
                        this.banner = new BannerRow();
                    })
                )
            }
        }
    }
</script>

<style scoped>

</style>