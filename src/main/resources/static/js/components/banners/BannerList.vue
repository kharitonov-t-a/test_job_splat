<template>
    <div>

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

        <div class="drop list" style="display:table"> <!--v-drag-and-drop:options="options"-->

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

            <div class="table-row-group" style="display:table-row-group">
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
                            v-on:showHistory="showHistory(item)">
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
    import Audit from 'components/audits/Audit.ts';
    import Banner from 'components/banners/Banner.ts';
    import Locale from "components/locales/Locale.ts";
    import GenericListImpl from "../generics/implementations/GenericListImpl";

    @Component({
        name: 'BannerList',
        components:{
            BannerRow,
            BannerForm,
            AuditList
        }/*,
        data (){
            return {
                options:{
                    dropzoneSelector: 'div.table-row-group',
                    draggableSelector: 'div.table-row',
                    handlerSelector: 'span.handle',
                    multipleDropzonesItemsDraggingEnabled: true,
                    showDropzoneAreas: true,
                    onDrop: function(event : any) {
                        console.log(event.nativeEvent);},
                    onDragstart: function(event : any) {console.log(event.nativeEvent);},
                    onDragend: function(event : any) {console.log(event.nativeEvent);}
                }
            }
        }*/
    })

    export default class BannerList extends GenericListImpl<Banner> {

        selectedLocaleId : number = 0;
        selectedActivity : boolean = true;
        isSortBanners : boolean = false;
        isSortBannersButtonText : string = "Sort banners!";

        @Prop() readonly localeList!: Array<Locale>;


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
            this.cleanForm()
        }
        public downBanner(banner: Banner) {
            this.swapTwoBanners(banner, 1);
            this.cleanForm()
        }

        private swapTwoBanners(banner: Banner, indent : number){
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

</style>