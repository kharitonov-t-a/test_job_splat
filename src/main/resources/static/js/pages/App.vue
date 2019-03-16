<template>
    <div id="app">
        <div class="container">
            <header is="Header" :appTabList="appTabList" @change-tab="changeTab"></header>
            <keep-alive>
                <component
                        :is="currentTab.component"
                        class="col-sm-10"
                        :totalItemList="currentTab.totalItemList"
                        :localeMap="localeMap"
                        :localeList="totalLocaleList"
                        :pathURL="currentTab.pathURL"
                        v-on:reloadBanners="reloadBanners()"
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
    import UserList from 'components/users/UserList.vue';
    import Header from 'components/Header.vue';
    import Locale from "../components/locales/Locale";
    import Banner from "../components/banners/Banner";
    import User from "../components/users/User";

    type AppTabList = {
        component: string;
        title: string;
        totalItemList: Array<any>;
        pathURL: string;
    };

    @Component({
        components: {
            BannerList,
            LocaleList,
            UserList,
            Header
        }
    })
    export default class App extends Vue {




        totalLocaleList: Array<Locale> = [];
        totalBannerList: Array<Banner> = [];
        totalUserList: Array<User> = [];

        appTabList: AppTabList[] = [{
            component: 'BannerList',
            title: 'Banners',
            totalItemList: this.totalBannerList,
            pathURL: '/banner',
        }, {
            component: 'LocaleList',
            title: 'Locales',
            totalItemList: this.totalLocaleList,
            pathURL: '/locale',
        }, {
            component: 'UserList',
            title: 'Users',
            totalItemList: this.totalUserList,
            pathURL: '/user',
        }];
        currentTab: AppTabList = this.appTabList[0];

        localeMap : Map<number, string> = new Map();

        changeTab(appTab: AppTabList) {
            this.currentTab = appTab;
        }

        constructor(){
            super();

            this.$resource('/locale/list').get().then(result =>
                result.json().then((data : Locale[]) => {
                    data.forEach((locale : Locale)=> {
                        this.totalLocaleList.push(locale);
                        this.localeMap.set(locale.id, locale.name);
                    });
                    this.reloadBanners();
                })
            );
            this.$resource('/user/list').get().then(result =>
                result.json().then((data : User[]) => {
                    data.forEach((user: User) => this.totalUserList.push(user))

                })
            );
        }

        reloadBanners(){
            if(this.totalBannerList.length > 0)
                this.totalBannerList = new Array<Banner>();
            this.$resource('/banner/list').get().then(result =>
                result.json().then((data : Banner[]) => {
                    data.forEach((banner: Banner) => this.totalBannerList.push(banner))

                })
            );
        }
    }
</script>

<style>
</style>