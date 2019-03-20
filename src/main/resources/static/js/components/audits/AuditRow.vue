<template>
    <div :class="'table-row-' + (index%2+1) + ' table-row'" style="display:table-row">
        <div style="display:table-cell">{{ audit.id }}</div>
        <div style="display:table-cell">{{ audit.date }}</div>
        <div style="display:table-cell">{{ audit.crud }}</div>
        <div style="display:table-cell">{{ audit.idBanner }}</div>
        <div style="display:table-cell">{{ audit.idUser }}</div>
        <div style="display:table-cell">{{ audit.description }}</div>
    </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import Audit from "components/audits/Audit.ts";

    @Component({
        name: 'AuditRow'
    })
    export default class AuditRow extends Vue {

        @Prop() readonly audit!: Audit;
        @Prop() readonly index!: number;

    }
</script>

<!--<style lang="less" scoped src="static/css/row"></style>-->
<style scoped>

</style>