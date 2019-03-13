<template>
    <div id="app">
        <div class="container">
            <header is="Header" :appTabList="appTabList" @change-tab="changeTab"></header>
            <keep-alive>
                <component
                        :is="currentTab"
                        class="col-sm-10"
                        :localeList="localeList"
                        :totalBannerList="totalBannerList"
                ></component>
            </keep-alive>
            <!--<banners-list :banners="banners"/>-->
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import BannerList from 'components/banners/BannerList.vue';
    import LocaleList from 'components/locales/LocaleList.vue';
    import Header from 'components/Header.vue';
    import Locale from 'components/locales/Locale.ts';
    import Banner from 'components/banners/Banner.ts';


    @Component({
        components: {
            BannerList,
            LocaleList,
            Header
        }
    })
    export default class App extends Vue {
        appTabList: Array<{ component: string, title: string }> = [{
            component: 'BannerList',
            title: 'Banners'
        }, {
            component: 'LocaleList',
            title: 'Locales'
        }, {
            component: 'Users',
            title: 'Users'
        }];
        currentTab: string = this.appTabList[1].component;

        localeList: Array<Locale> = new Array<Locale>();
        totalBannerList: Array<Banner> = new Array<Banner>();

        changeTab(appTab: { component: string, title: string }) {
            this.currentTab = appTab.component;
        }

        constructor(){
            super();
            this.$resource('/locale/list').get().then(result =>
                result.json().then((data : Locale[]) =>
                    data.forEach((locale : Locale)=> this.localeList.push(locale))
                )
            );
            this.$http.get('/banner/list').then(result =>
                result.json().then((data : Banner[]) => {
                    data.forEach((banner: Banner) => this.totalBannerList.push(banner))

                })
            );
        }

    }
</script>

<style>
</style>