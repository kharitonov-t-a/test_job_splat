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
               <input type="button" value="Edit" @click="edit"/>
            </span>
        </td>
        <td>
            <span>
                <input type="button" value="Delete" @click="del"/>
            </span>
        </td>
    </tr>
</template>

<script>
    export default {
        name: "BannerRow",
        props: ['banner', 'editBanner', 'deleteBanner', 'banners'],
        methods: {
            edit() {
                this.editBanner(this.banner);
            },
            del() {
                this.deleteBanner(this.banner);
            }
        }
    }
</script>

<style scoped>

</style>