import Vue from 'vue'
import VueRouter from 'vue-router'
import adminPanel from 'pages/adminPanel.vue'
import PreviewBanner from 'components/preview/PreviewBanner.vue'
import PreviewList from 'components/preview/PreviewList.vue'
import login from 'pages/login.vue'
Vue.use(VueRouter);


const routes = [
    { path: '/', component: PreviewList },
    { path: '/login', component: login },
    { path: '/admin', component: adminPanel, meta: { requiresAuth: true } },
    { path: '/banner/:id', component: PreviewBanner },
    // { path: '*', component: PreviewList },
];

export default new VueRouter({
    mode: 'history',
    // @ts-ignore
    base: contextPath,
    routes
})

