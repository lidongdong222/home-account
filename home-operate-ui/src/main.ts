import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import {createPinia} from 'pinia';
import router from "@/router";
import 'default-passive-events';



const app = createApp(App);
app.use(router).use(createPinia());
app.mount('#app')
