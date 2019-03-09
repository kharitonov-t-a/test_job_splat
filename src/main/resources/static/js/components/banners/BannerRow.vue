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

    @Component
    export default class BannerRow extends Vue{

        @Prop() readonly banner!: BannerRow;
        @Prop() readonly changeableBanner!: Vue;


        constructor(){
            super();
        }

        id : number | null = null;
        imgSrc : string | null = null;
        width : number | null = null;
        height : number| null = null;
        targetUrl : string | null = null;
        langId : number | null = null;
        priority : number | null = null;
        activity : boolean = true;

        editB() {
            this.changeableBanner.$emit('banner-edit', this.banner)
        }
        deleteB() {
            this.changeableBanner.$emit('banner-delete', this.banner)
        }
    }
</script>

<style scoped>
</style>