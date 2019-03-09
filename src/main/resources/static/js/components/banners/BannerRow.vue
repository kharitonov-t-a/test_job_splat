<template>
    <tr><!--class="list-group-item"-->
        <td>
            <span class="handle">
                <i class="glyphicon glyphicon-menu-hamburger"></i>
            </span>
        </td>
        <td>{{ banner.id }}</td>
        <td>{{ banner.imgSrc }}</td>
        <td>{{ banner.width }}</td>
        <td>{{ banner.height }}</td>
        <td>{{ banner.targetUrl }}</td>
        <td>{{ banner.langId }}</td>
        <td>{{ banner.priority }}</td>
        <td>{{ banner.activity }}</td>
        <td>
            <span>
               <input type="button" value="Edit" @click="editB"/>
            </span>
        </td>
        <td>
            <span>
                <input type="button" value="Delete" @click="deleteB"/>
            </span>
        </td>
    </tr>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import BannerList from 'BannerList.vue'

    @Component
    export default class BannerRow extends Vue{

        @Prop() readonly banner!: BannerRow;
        @Prop() readonly banners!: BannerList;
        @Prop() readonly bannerObj!: Vue;


        constructor(){
            super();
        }

        id : Number = 0;
        imgSrc : String = '';
        width : Number = 0;
        height : Number = 0;
        targetUrl : String = '';
        langId : Number = 0;
        priority : Number = 0;
        activity : Boolean = true;

        editB() {
            this.bannerObj.$emit('banner-edit', this.banner)
        }
        deleteB() {
            this.bannerObj.$emit('banner-delete', this.banner)
        }
    }


</script>

<style scoped>
</style>