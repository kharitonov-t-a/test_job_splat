<template>
    <header class="sticky">
        <a href="/" class="logo">Webdevkin</a>
        <button v-for="appTab in appTabList"
                :key="appTab.component"
                @click="$emit('change-tab', appTab)"
                class="button">
            {{ appTab.title }}
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
    }
</script>

<style scoped>

</style>