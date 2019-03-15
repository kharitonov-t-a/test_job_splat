<template>
        <div class="drop list" style="display:table">

            <div style="display:table-row">
                <div style="display:table-cell">id</div>
                <div style="display:table-cell">date</div>
                <div style="display:table-cell">crud</div>
                <div style="display:table-cell">idBanner</div>
                <div style="display:table-cell">idUser</div>
                <div style="display:table-cell">description</div>
            </div>

            <div class="table-row-group" style="display:table-row-group">
                <audit-row v-for="audit in auditList"
                            :key="audit.id"
                            :audit="audit">
                </audit-row>
            </div>

        </div>
</template>

<script lang="ts">
    import {Vue, Component, Prop} from "vue-property-decorator";
    import AuditRow from 'components/audits/AuditRow.vue';
    import Audit from "components/audits/Audit.ts";

    @Component({
        name: 'AuditList',
        components:{
            AuditRow
        }
    })
    export default class AuditList extends Vue {
        @Prop() auditList!: Array<Audit>;
    }

</script>

<style scoped>

</style>