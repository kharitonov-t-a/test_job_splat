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
        <template v-if="!isSortBanners">
            <div style="display:table-cell">
                <span>
                   <input type="button" value="Edit" @click="editBanner"/>
                </span>
            </div>
            <div style="display:table-cell">
                <span>
                    <input type="button" value="Delete" @click="deleteBanner"/>
                </span>
            </div>
        </template>
        <template v-if="isSortBanners">
            <div style="display:table-cell">
                <span>
                   <input type="button" value="Up" @click="upBanner"
                          :disabled="isFirstUpButton()"/>
                </span>
            </div>
            <div style="display:table-cell">
                <span>
                    <input type="button" value="Down" @click="downBanner"
                           :disabled="isLastDownButton()"/>
                </span>
            </div>
        </template>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import BannerList from 'components/banners/BannerList.vue';
    import Banner from 'components/banners/Banner.ts';

    @Component({
        name: 'BannerRow'
    })
    export default class BannerRow extends Vue {

        @Prop() readonly banner!: Banner;
        @Prop() readonly bannerList!: Array<Banner>;
        @Prop() readonly isSortBanners!: boolean;

        isLastDownButton() {
            return BannerList.getIndex(this.bannerList, this.banner.id) === this.bannerList.length - 1;
        }

        isFirstUpButton() {
            return BannerList.getIndex(this.bannerList, this.banner.id) === 0;
        }

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