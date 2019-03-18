<template>
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
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import BannerList from 'components/banners/BannerList.vue';
    import LocaleList from 'components/locales/LocaleList.vue';
    import UserList from 'components/users/UserList.vue';
    import AuditList from 'components/audits/AuditList.vue';
    import Header from 'components/Header.vue';
    import Locale from "../components/locales/Locale";
    import Banner from "../components/banners/Banner";
    import User from "../components/users/User";
    import Audit from "../components/audits/Audit";

    type AppTabList = {
        component: string;
        title: string;
        totalItemList: Array<any>;
        pathURL: string;
    };

    @Component({
        name: "adminPanel",
        components: {
            BannerList,
            LocaleList,
            UserList,
            AuditList,
            Header
        }
    })
    export default class adminPanel extends Vue {

        totalLocaleList: Array<Locale> = [];
        totalBannerList: Array<Banner> = [];
        totalUserList: Array<User> = [];
        auditList: Array<Audit> = [];

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
        }, {
            component: 'AuditList',
            title: 'History',
            totalItemList: [],
            pathURL: "",
        }];
        currentTab: AppTabList = this.appTabList[0];

        localeMap: Map<number, string> = new Map();

        changeTab(appTab: AppTabList) {
            this.currentTab = appTab;
        }

        constructor() {
            super();

            this.$resource('/locale/list').get().then(result =>
                result.json().then((data: Locale[]) => {
                    data.forEach((locale: Locale) => {
                        locale.className = Locale.CLASS_NAME;
                        this.totalLocaleList.push(locale);
                        // this.totalLocaleList[this.totalLocaleList.length - 1].className = Locale.CLASS_NAME;
                        this.localeMap.set(locale.id, locale.name);
                    });
                    this.reloadBanners();
                })
            );
            this.$resource('/user/list').get().then(result =>
                result.json().then((data: User[]) => {
                    data.forEach((user: User) => {
                        user.className = User.CLASS_NAME;
                        this.totalUserList.push(user);
                        // this.totalUserList[this.totalUserList.length - 1].className = User.CLASS_NAME;
                    })
                })
            );
        }

        reloadBanners() {
            if(this.totalBannerList.length > 0){
                this.totalBannerList = new Array<Banner>();
            }

            this.$resource('/banner/list').get().then(result => {
                result.json().then((data: Banner[]) => {
                    data.forEach((banner: Banner) => {
                        banner.className = Banner.CLASS_NAME;
                        this.totalBannerList.push(banner);
                        // this.totalBannerList[this.totalBannerList.length - 1].className = Banner.CLASS_NAME;
                    });
                    this.appTabList[0].totalItemList = this.totalBannerList;
                });
            });
        }
    }
</script>

<style lang="less">
    .component {
        width: 300px;
        @media (min-width: 768px) {
            width: 600px;
        }
        @media (min-width: 1280px) {
            width: 800px;
        }
    }
    .errorForm{
       color: red;
    }
</style>