<template xmlns:v-drag-and-drop="http://www.w3.org/1999/xhtml">
    <div class="content-container">


        <div class="formSortTable">

            <input type="button" v-bind:value="isSortBannersButtonText" @click="sortBanner"/>

            <select v-model="selectedLocaleId" @change="filterItem" :disabled="isSortBanners">
                <option v-bind:value="0">All locale</option>
                <option v-for="locale in localeList" v-bind:value="locale.id" v-if="locale.activity">
                    {{ locale.name }}
                </option>
            </select>

            <select v-model="selectedActivity" @change="filterItem" :disabled="isSortBanners">
                <option v-bind:value="true">Actived</option>
                <option v-bind:value="false">Disabled</option>
            </select>
        </div>

        <div class="flex-container">
            <banner-form :itemAttr="itemAttr"
                         :itemAttrChange="itemAttrChange"
                         :isSortBanners="isSortBanners"
                         :localeList="localeList"
                         :errorsForm="errorsForm"
                         v-on:saveItem="saveItem($event, $event.id, fillSaveFormData($event))"/>

            <div class="drag-wrapper flex-table" style="display:table" v-drag-and-drop:options="options"> <!---->

                <div class="table-row table-header" style="display:table-row">
                    <div style="display:table-cell"></div>
                    <div style="display:table-cell">id</div>
                    <div style="display:table-cell">imgSrc</div>
                    <div style="display:table-cell">width</div>
                    <div style="display:table-cell">height</div>
                    <div style="display:table-cell">targetUrl</div>
                    <div style="display:table-cell">langId</div>
                    <div style="display:table-cell">activity</div>
                    <div style="display:table-cell"></div>
                    <div style="display:table-cell"></div>
                    <div style="display:table-cell"></div>
                    <div style="display:table-cell"></div>
                </div>
                <div class="table-row-group" style="display:table-row-group" @reordered="reordered">
                    <banner-row v-for="(item, index) in itemList"
                                :key="item.id"
                                :item="item"
                                :index="index"
                                :itemList="itemList"
                                :isSortBanners="isSortBanners"
                                :selectedActivity="selectedActivity"
                                :localeMap="localeMap"
                                v-on:editItem="editItem(item)"
                                v-on:deleteItem="deleteItem(item)"
                                v-on:activateItem="activateItem(item)"
                                v-on:upBanner="upBanner(item)"
                                v-on:downBanner="downBanner(item)"
                                v-on:showHistory="showHistory(item)"
                    >
                    </banner-row>
                </div>

            </div>
        </div>
        <audit-list
                v-if="showAuditTab"
                class="col-sm-10"
                :auditList="auditList"
                :showSearchForm="false"
        ></audit-list>

    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import BannerRow from 'components/banners/BannerRow.vue';
    import BannerForm from 'components/banners/BannerForm.vue';
    import AuditList from 'components/audits/AuditList.vue';
    import Banner from 'components/banners/Banner.ts';
    import Locale from "components/locales/Locale.ts";
    import GenericListImpl from "../generics/implementations/GenericListImpl";

    @Component({
        name: 'BannerList',
        components: {
            BannerRow,
            BannerForm,
            AuditList
        },
        data() {
            return {
                options: {
                    dropzoneSelector: '.table-row-group',
                    draggableSelector: '.table-row',
                    handlerSelector: '.handle',
                    // reactivityEnabled: true,
                    // showDropzoneAreas: true,
                    // onDrop: function(event : any) {
                    // console.log(event.nativeEvent);},
                    // onDragstart: function(event : any) {console.log(event.nativeEvent);},
                    // onDragend: function(event : any) {console.log(event.nativeEvent);}
                }
            }
        }
    })

    export default class BannerList extends GenericListImpl<Banner> {

        selectedLocaleId: number = 0;
        selectedActivity: boolean = true;
        isSortBanners: boolean = false;
        isSortBannersButtonText: string = "Sort banners! (d&d)";

        @Prop() readonly localeList!: Array<Locale>;
        @Prop() readonly totalItemListChange: boolean;

        // added($event : any){
        //     console.log($event);
        // }
        // removed($event : any){
        //     console.log($event);
        // }
        reordered($event: any) {
            if (this.isSortBanners) {
                let destinateIndex = $event.detail.index;
                let banner = <Banner>$event.detail.items[0].__vue__.item;
                let currentIndex = GenericListImpl.getIndex(this.itemList, banner.id);
                if (destinateIndex > currentIndex)
                    destinateIndex--;
                while (destinateIndex != currentIndex) {
                    if (destinateIndex < currentIndex) {
                        this.upBanner(banner);
                        currentIndex--;
                    } else if (destinateIndex > currentIndex) {
                        this.downBanner(banner)
                        currentIndex++;
                    }
                }
            }
        }

        @Watch('totalItemList')
        getBannerAttr() {
            this.selectedLocaleId = 0;
            this.selectedActivity = true;
            this.filterItem();
        }

        filterItem() {
            this.showAuditTab = false;
            let selectedLocaleId = this.selectedLocaleId;
            let selectedActivity = this.selectedActivity;
            this.itemList = this.totalItemList.filter(function (banner) {

                return checkActivity() && checkLocale();

                function checkLocale() {
                    if (selectedLocaleId === 0)
                        return true;
                    else
                        return banner.langId === selectedLocaleId;
                }

                function checkActivity() {
                    return banner.activity === selectedActivity;
                }
            }).sort((a, b) => a.priority > b.priority ? 1 : -1);
        }

        public upBanner(banner: Banner) {
            this.swapTwoBanners(banner, -1);
        }

        public downBanner(banner: Banner) {
            this.swapTwoBanners(banner, 1);
        }

        private swapTwoBanners(banner: Banner, indent: number) {
            this.cleanForm();
            this.showAuditTab = false;
            const index1 = GenericListImpl.getIndex(this.itemList, banner.id);
            const index2 = index1 + indent;
            const currentPriority = banner.priority;
            this.itemList[index1].priority = this.itemList[index2].priority;
            this.itemList[index2].priority = currentPriority;
            this.itemList.splice(index1, 1, this.itemList[index2]);
            this.itemList.splice(index2, 1, banner);
        }

        sortBanner() {
            this.showAuditTab = false;
            this.cleanForm();
            if (this.isSortBanners) {
                this.$resource(this.pathURL + '{/id}').update(this.itemList).then(result => {
                        if (result.ok) {
                            this.filterItem();
                            this.isSortBanners = false;
                            this.isSortBannersButtonText = "Sort banners! (d&d)"
                        }
                    },
                    reason => alert("Error!"));
            } else {
                this.isSortBanners = true;
                this.isSortBannersButtonText = "Save! (pull the left part row)"
            }
        }

        fillSaveFormData(item: Banner): FormData {
            let formData = new FormData();
            formData.append('image', item.imgFile);
            formData.append('imgSrc', item.imgSrc);
            formData.append('width', item.width.toString());
            formData.append('height', item.height.toString());
            formData.append('targetUrl', item.targetUrl);
            formData.append('langId', item.langId.toString());
            formData.append('activity', item.activity.toString());

            if (item.id !== null)
                formData.append('priority', item.priority.toString());
            return formData;
        }


    }
</script>

<style lang="less">

    .form {
        float: left;
        width: 25%;
        margin-top: 25px;
    }

    .field {
        clear: both;
        text-align: right;
        line-height: 25px;
    }

    .field label {
        float: left;
        line-height: 10px;
        margin-top: -4px;
    }

    .fieldNotInput {
        line-height: 35px;
    }

    .table-row-group[aria-dropeffect="move"] {
        border-color: #68b;
        background: #fff;
    }

    .table-row[aria-grabbed="true"] {
        background: #5cc1a6;
        color: #fff;
    }

    .table-header{
        background: #6d8285;
    }

    @keyframes nodeInserted {
        from {
            opacity: 0.2;
        }
        to {
            opacity: 0.8;
        }
    }

    .item-dropzone-area {
        height: 2rem;
        background: #888;
        opacity: 0.8;
        animation-duration: 0.5s;
        animation-name: nodeInserted;
    }

    .content-container{
        display: flex;
        flex-direction: column;
        margin-top: 25px;
    }

    .content-container .flex-container{
        display: flex;
        flex-direction: row;
    }

    .flex-table{
        float: right;
        margin: 0 0 0 auto;
        width: 70%;
    }

    .formSortTable{
        margin-bottom: 10px;
    }

</style>