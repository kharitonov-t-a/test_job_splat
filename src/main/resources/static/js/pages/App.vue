<template>
    <div id="app">
        <div id="container">
            <header>
                <div class="header-left"><h1>Banners</h1></div>
                <div class="header-center">
                    <nav><router-link to="/">Home</router-link></nav>
                    <template v-if="this.$root.$data.profile">
                        <span>|</span>
                        <nav><router-link to="/admin">Admin panel</router-link></nav>
                    </template>
                </div>
                <div class="header-right">
                    <nav><router-link v-if="!this.$root.$data.profile" to="/login">Login</router-link></nav>
                    <nav><a v-if="this.$root.$data.profile" @click="logout" href="#">Logout</a></nav>
                </div>
            </header>
            <div id="content">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script lang="ts">

    import {Component, Vue} from 'vue-property-decorator';

    @Component({
        name: "App"
    })
    export default class App extends Vue {
        logout() {
            this.$resource('/logout').get({}).then(value => {
                this.$root.$data.profile = null;
                this.$router.push('/');
            });
        }
    }
</script>

<style lang="less" scoped>

    #container {
        margin: auto auto;
        text-align: center;
        /*position: absolute;*/
        /*width: 100%;*/
        /*height: 100vh;*/
        /*background: #DCDCDC no-repeat fixed;*/
    }

    header {
        /* Включаем режим Flexbox. */
        display: flex;

        /* Распределяем элементы внутри шапки */
        justify-content: space-between;

        /* Выравниваем элементы вертикально по центру. */
        align-items: center;
    }

    @media (max-width: 1000px) {
        header {
            /* Меняем оси шапки, делая её вертикальной. */
            flex-direction: column;

            /* Выравнивание элементов шапки с начала (по левому краю). */
            align-items: flex-start;
        }
    }

    @paddingHeader : 10px;
    @heightHeader : 25px;
    header {
        text-align: justify;
        letter-spacing: 1px;
        padding: @paddingHeader 10%;
        height: @heightHeader;
        width: 80%;
        background: #85abca;
        color: #000000;
        position: fixed;
        z-index: 999;
    }


    header h1 {
        font-size: @heightHeader;
    }
    header h1,
    header nav {
        display: inline-block;
    }

    @media screen and (max-width: 720px){

        header {
            height: auto;
        }

        header > div,
        header > div h1,
        header nav {
            height: auto;
            width: auto;
            display: block;
            text-align: center;
        }

    }

    #content {
        /*float: right;*/
        /*width: 100%;*/
        position: relative;
        top: @heightHeader + @paddingHeader * 2;
        position: relative;
        top: 45px;
        margin: 0px 8em;
        padding: 0px 10px 10px 10px;
        background: #0000001a;
        min-height: 40vh;
    }
</style>

<style lang="less">
    @colorRow: rgb(170, 170, 170);
    .handle {
        width: 10px;
        height: 10px;
        border-radius: 10px;
    }

    .table-row-1, .table-row-2 > .handle {
        background: @colorRow;
    }

    .table-row-2, .table-row-1 > .handle {
        background: lighten(@colorRow, 15%);
    }

    .table-row-group:first-child {
        border-top-right-radius: 10px;
        border-top-left-radius: 10px;
    }

    .table-row-group:last-child {
        border-bottom-left-radius: 10px;
        border-bottom-left-radius: 10px;
    }

    .table-row > div {
        text-align: center;
        vertical-align: middle;
        padding: 5px 7px;
    }

    .table-row:hover {
        background: lighten(@colorRow, -15%);
    }

    @previewColor: lightslategrey;
    .preview {
        background: @previewColor;
        border-radius: 10px;
        padding: 3px;
    }

    .preview:hover {
        background: lighten(@previewColor, 15%)
    }

    @buttonColor: #a384a3;
    input[type=button] {
        background: -webkit-gradient(linear, left top, left bottom, from(@buttonColor + #151515), to(@buttonColor - #151515));
        background: -moz-linear-gradient(top,  @buttonColor + #151515,  @colorRow - #151515);
    }
</style>