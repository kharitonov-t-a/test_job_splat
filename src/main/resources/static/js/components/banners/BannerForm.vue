<template>
    <div>
        <input type="text" placeholder="imgSrc" v-model="imgSrc"/>
        <input type="text" placeholder="width" v-model="width"/>
        <input type="text" placeholder="height" v-model="height"/>
        <input type="text" placeholder="targetUrl" v-model="targetUrl"/>
        <input type="text" placeholder="langId" v-model="langId"/>
        <input type="text" placeholder="priority" v-model="priority"/>
        <input type="text" placeholder="activity" v-model="activity"/>
        <input type="button" value="Save" @click="save"/>
    </div>
</template>

<script>
    function getIndex(list, id) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].id === id) {
                return i;
            }
        }
        return -1;
    }
    export default {
        name: "BannerForm",
        props: ['banners', 'bannerAttr'],
        data() {
            return {
                id: '',
                imgSrc: '',
                width: '',
                height: '',
                targetUrl: '',
                langId: '',
                priority: '',
                activity: ''
            }
        },
        watch: {
            bannerAttr: function (newVal, oldVal) {
                this.id = newVal.id;
                this.imgSrc = newVal.imgSrc;
                this.width = newVal.width;
                this.height = newVal.height;
                this.targetUrl = newVal.targetUrl;
                this.langId = newVal.langId;
                this.priority = newVal.priority;
                this.activity = newVal.activity;
            }
        },
        methods: {
            save() {
                const banner = {
                    imgSrc: this.imgSrc,
                    width: this.width,
                    height: this.height,
                    targetUrl: this.targetUrl,
                    langId: this.langId,
                    priority: this.priority,
                    activity: this.activity
                };

                if (this.id) {
                    this.$resource('/banner{/id}').update({id: this.id}, banner).then(result =>
                        result.json().then(data => {
                            const index = getIndex(this.banners, data.id);
                            this.banners.splice(index, 1, data);
                            this.id = '';
                            this.imgSrc = '';
                            this.width = '';
                            this.height = '';
                            this.targetUrl = '';
                            this.langId = '';
                            this.priority = '';
                            this.activity = '';
                        })
                    );
                } else {
                    this.$resource('/banner{/id}').save({}, banner).then(result =>
                        result.json().then(data => {
                            this.banners.push(data);
                            this.imgSrc = '';
                            this.width = '';
                            this.height = '';
                            this.targetUrl = '';
                            this.langId = '';
                            this.priority = '';
                            this.activity = '';
                        })
                    )
                }
            }
        }
    }
</script>

<style scoped>

</style>