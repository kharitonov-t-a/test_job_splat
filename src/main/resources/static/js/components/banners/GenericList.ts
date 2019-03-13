// import {Vue, Component, Prop} from "vue-property-decorator";
// import LocaleForm from 'components/locales/LocaleForm.vue';
// import LocaleRow from 'components/locales/LocaleRow.vue';
// import Locale from "components/locales/Locale.ts";
//
// @Component({
//     name: 'LocaleList',
//     components:{
//         LocaleRow,
//         LocaleForm
//     }
// })
// export default class GenericList<T> extends Vue {
//     @Prop() itemList!: Array<T>;
//     itemAttr: T = new T();
//     indexItem: number;
//
//     public saveLocale(item : T){
//
//         let formData = new FormData();
//         formData.append('name', item.name);
//         formData.append('activity', item.activity.toString());
//
//         if (item.id !== null) {
//             this.$resource('/locale{/id}').update({id: item.id}, item).then(result =>
//                     result.json().then((data : Locale) => {
//                         const index = LocaleList.getIndex(this.localeList, data.id);
//                         this.localeList.splice(index, 1, data);
//                         this.clearForm();
//                     }),
//                 reason => alert("Error!")
//             );
//         } else {
//             this.$resource('/locale{/id}').save({}, item).then(result =>
//                     result.json().then((data : Locale) => {
//                         this.localeList.push(data);
//                         this.clearForm();
//                     }),
//                 reason => alert("Save error!"))
//         }
//     }
//
//     public editLocale(locale: Locale) {
//         this.clearForm();
//         this.$nextTick(() =>{
//             this.localeAttr = locale;
//         });
//     }
//
//     public deleteLocale(locale : Locale){
//         this.clearForm();
//         if (locale.activity === false) {
//             this.$resource('/locale{/id}').remove({id: locale.id}).then(result => {
//                     if (result.ok) {
//                         this.localeList.splice(this.localeList.indexOf(locale), 1);
//                     }
//                 },
//                 reason => alert("Error!"));
//         } else {
//             this.$resource('/locale/delete{/id}').update({id: locale.id}, {}).then(result =>
//                     result.json().then((data : Object) => {
//                         locale.activity= false;
//                     }),
//                 reason => alert("Error!")
//             );
//         }
//     }
//
//     clearForm(){
//         this.localeAttr = null;
//         this.indexLocale = null;
//     }
//
//     static getIndex(list : Array<Locale>, id : Number) {
//         for (var i = 0; i < list.length; i++) {
//             if (list[i].id === id) {
//                 return i;
//             }
//         }
//         return -1;
//     }
//
// }
