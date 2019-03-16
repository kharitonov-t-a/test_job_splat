<template xmlns:v-drag-and-drop="http://www.w3.org/1999/xhtml">
    <div>
        <!--<div v-drag-and-drop:options="options" class="drag-wrapper">-->
            <!--<ul @added="added"-->
                <!--@removed="removed"-->
                <!--@reordered="reordered">-->
                <!--<li>Item 1</li>-->
                <!--<li>Item 2</li>-->
                <!--<li>Item 3</li>-->
            <!--</ul>-->
            <!--<ul >-->
                <!--<li>Item 4</li>-->
                <!--<li>Item 5</li>-->
                <!--<li>Item 6</li>-->
            <!--</ul>-->
            <!--<ul>-->
                <!--<li>Item 7</li>-->
                <!--<li>Item 8</li>-->
                <!--<li>Item 9</li>-->
            <!--</ul>-->
        <!--</div>-->
        <banner-form :itemAttr="itemAttr"
                     :itemAttrChange="itemAttrChange"
                     :isSortBanners="isSortBanners"
                     :localeList="localeList"
                     v-on:saveItem="saveItem(fillSaveFormData($event), $event.id)"/>

        <input type="button" v-bind:value="isSortBannersButtonText" @click="sortBanner"/>

        <select v-model="selectedLocaleId" @change="filterItem" :disabled="isSortBanners">
            <option v-bind:value="0">All</option>
            <option v-for="locale in localeList" v-bind:value="locale.id"  v-if="locale.activity">
                {{ locale.name }}
            </option>
        </select>

        <select v-model="selectedActivity" @change="filterItem" :disabled="isSortBanners">
            <option v-bind:value="true">Actived</option>
            <option v-bind:value="false">Disabled</option>
        </select>

        <div class="drag-wrapper" style="display:table" v-drag-and-drop:options="options"> <!---->

            <div style="display:table-row">
                <div style="display:table-cell"></div>
                <div style="display:table-cell">id</div>
                <div style="display:table-cell">imgSrc</div>
                <div style="display:table-cell">width</div>
                <div style="display:table-cell">height</div>
                <div style="display:table-cell">targetUrl</div>
                <div style="display:table-cell">langId</div>
                <div style="display:table-cell">priority</div>
                <div style="display:table-cell">activity</div>
                <div style="display:table-cell"></div>
                <div style="display:table-cell"></div>
            </div>

            <div class="table-row-group" style="display:table-row-group" @reordered="reordered">
                <banner-row v-for="item in itemList"
                            :key="item.id"
                            :item="item"
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
        <audit-list
                v-if="showAuditTab"
                class="col-sm-10"
                :auditList="auditList"
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
        components:{
            BannerRow,
            BannerForm,
            AuditList
        },
        data (){
            return {
                options:{
                    dropzoneSelector: '.table-row-group',
                    draggableSelector: '.table-row',
                    handlerSelector: '.handle',
                    reactivityEnabled: true,
                    showDropzoneAreas: true,
                    // onDrop: function(event : any) {
                    // console.log(event.nativeEvent);},
                    // onDragstart: function(event : any) {console.log(event.nativeEvent);},
                    // onDragend: function(event : any) {console.log(event.nativeEvent);}
                }
            }
        }
    })

    export default class BannerList extends GenericListImpl<Banner> {

        selectedLocaleId : number = 0;
        selectedActivity : boolean = true;
        isSortBanners : boolean = false;
        isSortBannersButtonText : string = "Sort banners!";

        @Prop() readonly localeList!: Array<Locale>;

        // added($event : any){
        //     console.log($event);
        // }
        // removed($event : any){
        //     console.log($event);
        // }
        reordered($event : any){
            if(this.isSortBanners){
                let destinateIndex = $event.detail.index;
                let banner = <Banner>$event.detail.items[0].__vue__.item;
                let currentIndex = GenericListImpl.getIndex(this.itemList, banner.id);
                while(destinateIndex != currentIndex){
                    if(destinateIndex < currentIndex){
                        this.upBanner(banner);
                        currentIndex--;
                    }else{
                        this.downBanner(banner)
                        currentIndex++;
                    }
                }
            }
        }

        @Watch('totalItemList')
        getBannerAttr(){
            this.selectedLocaleId = 0;
            this.filterItem();
        }

        filterItem(){
            this.showAuditTab = false;
            let selectedLocaleId = this.selectedLocaleId;
            let selectedActivity = this.selectedActivity;
            this.itemList = this.totalItemList.filter(function (banner) {

                return checkActivity() && checkLocale();

                function checkLocale() {
                    if(selectedLocaleId===0)
                        return true;
                    else
                        return banner.langId === selectedLocaleId;
                }
                function checkActivity() {
                    return banner.activity === selectedActivity;
                }
            }).sort((a, b) => a.priority>b.priority?1:-1);
        }

        public upBanner(banner: Banner) {
            this.swapTwoBanners(banner, -1);
        }
        public downBanner(banner: Banner) {
            this.swapTwoBanners(banner, 1);
        }

        private swapTwoBanners(banner: Banner, indent : number){
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

        sortBanner(){
            this.showAuditTab = false;
            this.cleanForm();
            if(this.isSortBanners){
                this.$resource(this.pathURL + '{/id}').update(this.itemList).then(result => {
                        if (result.ok) {
                            this.filterItem();
                            this.isSortBanners = false;
                            this.isSortBannersButtonText = "Sort banners!!"
                        }
                    },
                    reason => alert("Error!"));
            }else{
                this.isSortBanners = true;
                this.isSortBannersButtonText = "Save!"
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

<style scoped>
    /*.drag-wrapper {*/
        /*display: flex;*/
        /*justify-content: center;*/
    /*}*/
    /*.table-row-group {*/
        /*display: flex;*/
        /*flex-direction: column;*/
        /*padding: 3px !important;*/
        /*min-height: 70vh;*/
        /*float:left;*/
        /*list-style-type:none;*/
        /*overflow-y:auto;*/
        /*border:2px solid #888;*/
        /*border-radius:0.2em;*/
        /*background:#8adccc;*/
        /*color:#555;*/
        /*margin-right: 5px;*/
    /*}*/

    /*!* drop target state *!*/
    /*.table-row-group[aria-dropeffect="move"] {*/
        /*border-color:#68b;*/
        /*background:#fff;*/
    /*}*/

    /*!* drop target focus and dragover state *!*/
    /*.table-row-group[aria-dropeffect="move"]:focus,*/
    /*.table-row-group[aria-dropeffect="move"].dragover*/
    /*{*/
        /*outline:none;*/
        /*box-shadow:0 0 0 1px #fff, 0 0 0 3px #68b;*/
    /*}*/

    /*!* draggable items *!*/
    /*.table-row {*/
        /*display:block;*/
        /*list-style-type:none;*/
        /*margin:0 0 2px 0;*/
        /*padding:0.2em 0.4em;*/
        /*border-radius:0.2em;*/
        /*line-height:1.3;*/
    /*}*/

    /*.table-row:hover {*/
        /*box-shadow:0 0 0 2px #68b, inset 0 0 0 1px #ddd;*/
    /*}*/

    /*!* items focus state *!*/
    /*.table-row:focus*/
    /*{*/
        /*outline:none;*/
        /*box-shadow:0 0 0 2px #68b, inset 0 0 0 1px #ddd;*/
    /*}*/

    /*!* items grabbed state *!*/
    /*.table-row[aria-grabbed="true"]*/
    /*{*/
        /*background:#5cc1a6;*/
        /*color:#fff;*/
    /*}*/

    /*@keyframes nodeInserted {*/
        /*from { opacity: 0.2; }*/
        /*to { opacity: 0.8; }*/
    /*}*/

    /*.item-dropzone-area {*/
        /*height: 2rem;*/
        /*background: #888;*/
        /*opacity: 0.8;*/
        /*animation-duration: 0.5s;*/
        /*animation-name: nodeInserted;*/
    /*}*/
</style>