<template>
    <div class="form">
        <div v-if="!image" class="field">
            <label for="fileField">Image</label>
            <input id="fileField" type="file" @change="onFileChange">
        </div>
        <div v-else id="fileSelect" class="field">
            <img :src="image" width="100"/>
            <button @click="removeImage">Remove image</button>
        </div>
        <div class="field">
            <label for="widthField">Width</label>
            <input id="widthField" type="number" placeholder="width" v-model="item.width > 1000?item.width=1000:item.width<1?item.width=1:item.width"/>
            <div class="errorForm">{{errorsFormMap.get("width")}}</div>
        </div>
        <div class="field">
            <label for="heightField">Height</label>
            <input id="heightField" type="number" placeholder="height" v-model="item.height > 1000?item.height=500:item.height<1?item.height=1:item.height"/>
            <div class="errorForm">{{errorsFormMap.get("height")}}</div>
        </div>
        <div class="field">
            <label for="targetUrlField">Target Url</label>
            <input id="targetUrlField" type="text" placeholder="targetUrl" v-model="item.targetUrl"/>
            <div class="errorForm">{{errorsFormMap.get("targetUrl")}}</div>
        </div>
        <div class="field fieldNotInput">
            <label for="localeField">Locale</label>
            <select id="localeField" v-model="item.langId">
                <option v-bind:value="null" selected>Select locale</option>
                <option v-for="locale in localeList" v-bind:value="locale.id" v-if="locale.activity">
                    {{ locale.name }}
                </option>
            </select>
        </div>
        <div class="field fieldNotInput">
            <input type="button" value="Save" @click="saveItem" :disabled="isSortBanners"/>
            <input type="button" value="Cancel" @click="cleanForm"/>
        </div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import Banner from "components/banners/Banner.ts";
    import Locale from "components/locales/Locale.ts";
    import GenericFormImpl from "components/generics/implementations/GenericFormImpl.ts";


    @Component({
        name: 'BannerForm'
    })
    export default class BannerForm extends GenericFormImpl<Banner> {

        @Prop() readonly localeList!: Array<Locale>;
        @Prop() readonly isSortBanners : boolean;
        image : any = null;

        constructor(){
            super();
            this.item = new Banner();
        }

        @Watch('itemAttrChange')
        getItemAttr(){
            if(this.itemAttr !== null){
                this.item.copyItem(this.itemAttr);
                this.image = '/image' + this.itemAttr.imgSrc;
            }else{
                this.cleanForm();
            }
        }

        checkItemBeforeSave(): boolean {
            return (this.item.imgFile != null || this.item.imgSrc != null)
                && (this.item.height != null && this.item.height.toString().length != 0)
                && (this.item.width != null && this.item.width.toString().length != 0)
                && this.item.langId != null
                && (this.item.targetUrl != null && this.item.targetUrl.length != 0);
        }

        cleanForm(): void {
            this.item.clean();
            this.image = '';
            this.errorsFormMap.clear();
        }

        onFileChange(e : any) {
            let files = e.target.files || e.dataTransfer.files;
            if (!files.length)
                return;
            if(files[0].size > 500000) {
                this.removeImage();
                alert("File is too large. Max - 500Kb.");
                return;
            } else{
                let name = files[0].name;
                const fileExtension = ['png', 'jpg', 'jpeg']; // допустимые типы файлов
                if (fileExtension.indexOf(name.split('.').pop().toLowerCase()) == -1) {
                    this.removeImage();
                    alert("File have wrong type. Proper types: " + fileExtension);
                    return;
                }else {
                    this.item.imgFile = files[0];
                    this.createImage(files[0]);
                }
            }
        }
        createImage(file : any) {
            let reader : FileReader = new FileReader();
            let vm = this;

            reader.onload = ((event: Event) => {
                vm.image = reader.result;
            });

            reader.readAsDataURL(file);
        }
        removeImage () {
            this.image = '';
            this.item.imgFile = null;
            this.item.imgSrc = null;
        }
    }

</script>

<style lang="less" scoped>


    #fileSelect{
        margin: 0 0 0 auto;
    }


</style>