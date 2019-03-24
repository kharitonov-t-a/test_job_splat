import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import VueDraggable from 'vue-draggable'
import router from "./router/router";
import VueRouter from 'vue-router';

Vue.use(VueDraggable.install);
Vue.use(VueResource);
Vue.use(VueRouter);

let application = new Vue({
    // @ts-ignore
    $resource: {
        // @ts-ignore
        root: contextPath,//process.env.BASE_URL
    },
    // @ts-ignore
    // $http: {
    //     // @ts-ignore
    //     root: contextPath,
    // },
    data(){
        return {
            // @ts-ignore
            profile: frontendData.profile!=null?frontendData.profile:"",
            // @ts-ignore
            baseURL: contextPath
        }

    },
    router,
    render: a => a(App)
}).$mount('#app');

router.beforeEach((to, from, next) => {
    if(to.matched.some(record => record.meta.requiresAuth)) {
        if (!application.$root.$data.profile) {
            next({
                path: 'login',
                params: { nextUrl: to.fullPath }
            })
        } else {
            next()
        }
    }else{
        next()
    }
});