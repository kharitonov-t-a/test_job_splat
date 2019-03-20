<template>
    <div class="dynamic-content">
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

    @shadow : rgba(0,0,0,0.2);
    @background-color-form-element : #f5f5f5;
    @border : solid 1px #ccc;

    input[type="text"], input[type="password"], input[type="number"], select, input[type="file"], #fileSelect {
        width:12em;
        border-radius:2px;
        border: @border;
        padding:0.4em;
    }

    select, input[type="button"], #fileSelect {
        background: white no-repeat center right;
        -webkit-box-shadow: 0 1px 3px @shadow;
        box-shadow: 0 1px 3px @shadow;
    }

    input[type="text"], input[type="password"], input[type="number"], input[type="file"], #fileSelect {
        background-color: @background-color-form-element;
        -webkit-box-shadow: inset 0 2px 3px @shadow;
        box-shadow: inset 0 2px 3px @shadow;
    }

    label:before {
        display:inline-block;
        position:relative;
        top:0.25em;
        left:-2px;
        content:'';
        width:25px;
        height:25px;
    }

    input[type="button"] {
        padding:0.5em 1em;
        line-height:1em;
        cursor:pointer;
        border-radius:4px;
        color:#000;
        font-weight:bold;
        font-size:inherit;
        border: @border;
        box-shadow:0 1px 5px @shadow;
        background-position: center bottom;
    }

    input[type="button"]:active{
        background: lighten(@background-color-form-element, -5%);
    }

    input[type="button"]:disabled{
        background: lighten(@background-color-form-element, -5%);
    }







    /*header{*/
        /*margin-bottom: 20px;*/
    /*}*/

    /*.component {*/
        /*width: 300px;*/
        /*@media (min-width: 768px) {*/
            /*width: 600px;*/
        /*}*/
        /*@media (min-width: 1280px) {*/
            /*width: 800px;*/
        /*}*/
    /*}*/
    /*.errorForm{*/
       /*color: red;*/
    /*}*/
</style>