<template>
    <header class="sticky">
        <button v-for="(appTab, index) in appTabList"
                :key="appTab.component"
                @click="clickTab(appTab, index)"
                class="buttonTab"
                v-bind:class="isSelectedTabs[index]">
            {{ appTab.title }}{{flag?"":""}}
        </button>
    </header>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";

    @Component({
        name: 'Header'
    })
    export default class Header extends Vue {
        @Prop() readonly appTabList!: Array<{component : string, title : string}>;
        isSelectedTabs : Array<string> = [];
        flag:boolean = false;


        mounted(){
            this.appTabList.forEach(value => this.isSelectedTabs.push(""));
            this.isSelectedTabs[0] = "selected";
        }

        clickTab(appTab : {component : string, title : string}, index : number): void {
            this.flag = !this.flag;
            this.isSelectedTabs.forEach((value, index1) =>  this.isSelectedTabs[index1] = "");
            this.isSelectedTabs[index] = "selected";
            this.$emit('change-tab', appTab);
        }
    }
</script>

<style lang="less" scoped>
         .buttonTab {
             background-color: #FFFFFF;
             border: 1px solid #CCCCCC;
             box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
             transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
             border-radius: 4px;
             color: #555555;
             width:120px;
             font-size: 14px;
             text-align:center;
             height: 30px;
             line-height: 20px;
             margin: -4px auto 10px;
             padding: 4px 6px;
             vertical-align: middle;
             text-decoration:none;
         }
         .buttonTab:hover, .buttonTab:focus, .buttonTab.selected {
             border-color: rgba(82, 168, 236, 0.8);
             box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(82, 168, 236, 0.6);
             outline: 0 none;
             height: 35px;
             margin: -8px 0 0 0;
         }
</style>