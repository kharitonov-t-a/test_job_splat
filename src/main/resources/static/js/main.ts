import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import VueDraggable from 'vue-draggable'
import router from "./router/router";
import VueRouter from 'vue-router';

// @ts-ignore
Vue.use(VueDraggable.install);
Vue.use(VueResource);
Vue.use(VueRouter);

let application = new Vue({
    el: '#app',
    data(){

        return {
            // @ts-ignore
            profile: frontendData.profile!=null?frontendData.profile:""
        }

    },
    router,
    render: a => a(App)
});

router.beforeEach((to, from, next) => {
    if(to.matched.some(record => record.meta.requiresAuth)) {
        if (!application.$root.$data.profile) {
            next({
                path: '/login',
                params: { nextUrl: to.fullPath }
            })
        } else {
            next()
        }
    }else{
        next()
    }
});


/*
function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var bannerApi = Vue.resource('/banner{/id}');

/!*--------------------------------BANNER_CREATE_FORM--------------------------------*!/
Vue.component('banner-form', {
    props: ['banners', 'bannerAttr'],
    data: function () {
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
    template:
        '<div>' +
            '<input type="text" placeholder="imgSrc" v-model="imgSrc" />' +
            '<input type="text" placeholder="width" v-model="width" />' +
            '<input type="text" placeholder="height" v-model="height" />' +
            '<input type="text" placeholder="targetUrl" v-model="targetUrl" />' +
            '<input type="text" placeholder="langId" v-model="langId" />' +
            '<input type="text" placeholder="priority" v-model="priority" />' +
            '<input type="text" placeholder="activity" v-model="activity" />' +
            '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var banner = {
                imgSrc: this.imgSrc,
                width: this.width,
                height: this.height,
                targetUrl: this.targetUrl,
                langId: this.langId,
                priority: this.priority,
                activity: this.activity
            };

            if (this.id) {
                bannerApi.update({id: this.id}, banner).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.banners, data.id);
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
                bannerApi.save({}, banner).then(result =>
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
});


/!*--------------------------------BANNER_LIST--------------------------------*!/
Vue.component('banner-Row', {
    props: ['banner', 'editMethod', 'banners'],
    template:
        '<tr class="list-group-item">' +
            '<td>' +
                '<span class="handle" >' +
                    '<i class="glyphicon glyphicon-menu-hamburger"></i>' +
                '</span>' +
            '</td>' +
            '<td>{{ banner.id }}</td>' +
            '<td>{{ banner.imgSrc }}</td>' +
            '<td>{{ banner.width }}</td>' +
            '<td>{{ banner.height }}</td>' +
            '<td>{{ banner.targetUrl }}</td>' +
            '<td>{{ banner.langId }}</td>' +
            '<td>{{ banner.priority }}</td>' +
            '<td>{{ banner.activity }}</td>' +
            '<td>' +
                '<span>' +
                   '<input type="button" value="Edit" @click="edit"/>' +
                '</span>' +
            '</td>' +
            '<td>' +
                '<span>' +
                    '<input type="button" value="Delete" @click="del"/>' +
                '</span>' +
            '</td>' +
        '</tr>',
    methods: {
        edit: function () {
            this.editMethod(this.banner);
        },
        del: function () {

            if (this.banner.activity === false) {
                bannerApi.remove({id: this.banner.id}).then(result => {
                    if (result.ok) {
                        this.banners.splice(this.banners.indexOf(this.banner), 1);
                    }
                });
            } else {
                var dataBanner = {
                    imgSrc: this.banner.imgSrc,
                    width: this.banner.width,
                    height: this.banner.height,
                    targetUrl: this.banner.targetUrl,
                    langId: this.banner.langId,
                    priority: this.banner.priority,
                    activity: false
                };
                bannerApi.update({id: this.banner.id}, dataBanner).then(result =>
                    result.json().then(data => {
                        this.banner.activity = false;
                    })
                );
            }


        }
    }
});

Vue.component('banners-list', {
    props: ['banners'],
    data: function () {
        return {
            banner: null
        }
    },
    template:
        '<div>' +
            '<banner-form :banners="banners" :bannerAttr="banner" />' +
            '<table>' +
                '<thead>' +
                    '<tr>' +
                        '<th>id</th>' +
                        '<th>imgSrc</th>' +
                        '<th>width</th>' +
                        '<th>height</th>' +
                        '<th>targetUrl</th>' +
                        '<th>langId</th>' +
                        '<th>priority</th>' +
                        '<th>activity</th>' +
                        '<th></th>' +
                        '<th></th>' +
                    '</tr>' +
                '</thead>' +
                '<tbody class="list-group drag">' +
                    '<draggable :list="banners" class="dragArea" :options="{handle:\'.handle\'}">' +
                        '<banner-Row v-for="banner in banners" :key="banner.id" :banner="banner" ' +
                           ':editMethod="editMethod" :banners="banners" />' +
                    '</draggable>' +
                '</tbody>' +
            '</table>' +
        '</div>',
    created: function () {
        bannerApi.get().then(result =>
            result.json().then(data =>
                data.forEach(banner => this.banners.push(banner))
            )
        )
    },
    methods: {
        editMethod: function (banner) {
            this.banner = banner;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<banners-list :banners="banners"/>',
    data: {
        banners: []
    }
});*/
