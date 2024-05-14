<!-- <template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* Global styles can be added here */
</style> -->

<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
// 导入 Vue
import Vue from 'vue';
// 导入 Bootstrap Vue
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';
// 导入 Bootstrap 和 Bootstrap Vue CSS 文件
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

// 在 Vue 实例中注册 Bootstrap Vue
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

export default {
  name: 'App'
}
</script>

<style>
/* Global styles can be added here */
</style>
