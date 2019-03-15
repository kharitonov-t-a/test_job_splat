<template>
    <div id="app">
        <div class="container">
            <header is="Header" :appTabList="appTabList" @change-tab="changeTab"></header>
            <keep-alive>
                <component
                        :is="currentTab"
                        class="col-sm-10"
                        :localeList="localeList"
                        :userList="userList"
                        :totalBannerList="totalBannerList"
                        :localeMap="localeMap"
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
    import Locale from 'components/locales/Locale.ts';
    import Banner from 'components/banners/Banner.ts';
    import User from 'components/users/User.ts';


    @Component({
        components: {
            BannerList,
            LocaleList,
            UserList,
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
            component: 'UserList',
            title: 'Users'
        }];
        currentTab: string = this.appTabList[0].component;

        localeList: Array<Locale> = new Array<Locale>();
        totalBannerList: Array<Banner> = new Array<Banner>();
        localeMap : Map<number, string> = new Map();
        userList: Array<User> = new Array<User>();

        changeTab(appTab: { component: string, title: string }) {
            this.currentTab = appTab.component;
        }

        constructor(){
            super();

            this.$resource('/locale/list').get().then(result =>
                result.json().then((data : Locale[]) => {
                    data.forEach((locale : Locale)=> {
                        this.localeList.push(locale);
                        this.localeMap.set(locale.id, locale.name);
                    });
                    this.$resource('/banner/list').get().then(result =>
                        result.json().then((data : Banner[]) => {
                            data.forEach((banner: Banner) => this.totalBannerList.push(banner))

                        })
                    );
                })
            );
            this.$resource('/user/list').get().then(result =>
                result.json().then((data : User[]) => {
                    data.forEach((user: User) => this.userList.push(user))

                })
            );
        }

        reloadBanners(){
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