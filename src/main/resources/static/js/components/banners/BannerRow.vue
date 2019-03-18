<template>
    <div class="table-row" style="display:table-row">
        <div style="display:table-cell">
            <span class="handle">
                <i class="glyphicon glyphicon-menu-hamburger"></i>
            </span>
        </div>
        <div style="display:table-cell">{{ item.id }}</div>
        <div style="display:table-cell"><img :src="item.imgSrc" width="100"/></div>
        <div style="display:table-cell">{{ item.width }}</div>
        <div style="display:table-cell">{{ item.height }}</div>
        <div style="display:table-cell">{{ item.targetUrl }}</div>
        <div style="display:table-cell">{{ localeMap.get(item.langId) }}</div>
        <div style="display:table-cell">{{ item.activity }}</div>
        <template v-if="!isSortBanners">
            <div style="display:table-cell">
                <span>
                   <input type="button" value="Edit" @click="editItem"/>
                </span>
            </div>
            <div style="display:table-cell">
                <span>
                    <input type="button" value="Delete" @click="deleteItem"/>
                </span>
            </div>
            <div style="display:table-cell" v-if="!selectedActivity">
                <span>
                   <input type="button" value="Activate" @click="activateItem"/>
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
        <div style="display:table-cell">
                <span>
                   <input type="button" value="History" @click="showHistory"/>
                </span>
        </div>
        <div style="display:table-cell">
            <router-link :to="{
                path: '/banner/' + item.id,
                query:{
                    item: item
                }
            }" >Admin panel</router-link>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import BannerList from 'components/banners/BannerList.vue';
    import Banner from 'components/banners/Banner.ts';
    import GenericRowImpl from "../generics/implementations/GenericRowImpl";
    import GenericListImpl from "../generics/implementations/GenericListImpl";

    @Component({
        name: 'BannerRow'
    })
    export default class BannerRow extends GenericRowImpl<Banner> {

        @Prop() readonly itemList!: Array<Banner>;
        @Prop() readonly isSortBanners!: boolean;
        @Prop() readonly selectedActivity!: boolean;
        @Prop() readonly localeMap!: Map<number, string>;

        isLastDownButton() {
            return GenericListImpl.getIndex(this.itemList, this.item.id) === this.itemList.length - 1;
        }

        isFirstUpButton() {
            return GenericListImpl.getIndex(this.itemList, this.item.id) === 0;
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