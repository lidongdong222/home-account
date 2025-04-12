import { defineStore } from 'pinia';
import { Menu, ApiGetMenuList } from "@/utils/api/sys-menu-api";
import { ApiGetLoginUser, User } from '@/utils/api/sys-user-api';

export const useMenuStore = defineStore("menu", () => {
  const menuList = ref<Menu[]>([]);
  const activeMenuIndex = ref('');
  async function reloadMenuList() {
    await ApiGetMenuList({}).then(res => {
      if (res.code == 200) {
        menuList.value = res.list;
      }
    });
    return new Promise((resolve) => {
      resolve("")
    })
  }

  return { activeMenuIndex,menuList, reloadMenuList }
})

export const useTabsStore = defineStore("tabs", () => {  
  const router = useRouter();
  let storageTabs = JSON.parse(sessionStorage.getItem("centerTabs")+'');
  if(!storageTabs){
    storageTabs=[];
  }
  const centerTabs = ref<any>(storageTabs);
  const currentTab = ref<string>('');
  function addTab(title: string, name: string, route: string) {
    for (let i in centerTabs.value) {
      if (centerTabs.value[i].name + '' == name) {
        if(currentTab.value!=name){
          currentTab.value=name;
          router.push(route)
        }
        return;
      }
    }
    centerTabs.value.push({
      title: title,
      name: name,
      route: route
    })
    currentTab.value=name;
    router.push(route)
    sessionStorage.setItem("centerTabs",JSON.stringify(centerTabs.value))
    
  }
  function delTab(name: string) {
    let removeName = "", removeIndex = "";
    for (let i in centerTabs.value) {
      if (centerTabs.value[i].name + '' == name) {
        removeName = name;
        removeIndex = i;
        break;
      }
    }
    if (removeName) {
      centerTabs.value = centerTabs.value.filter((tab: any) => tab.name != removeName)
      sessionStorage.setItem("centerTabs",JSON.stringify(centerTabs.value))
    }
    if(centerTabs.value.length<1){
      useMenuStore().activeMenuIndex = "";
      router.push("/")
    }else{
      if (removeName == currentTab.value) {
        let selectTab = centerTabs.value.length > removeIndex ? centerTabs.value[removeIndex] : centerTabs.value[centerTabs.value.length - 1];
        currentTab.value = selectTab.name;
        useMenuStore().activeMenuIndex = currentTab.value+'';
        router.push(selectTab.route)
      }
    }
  }

  function changeTab(){
    for (let i in centerTabs.value) {
      if (centerTabs.value[i].name + '' == currentTab.value) {
        router.push(centerTabs.value[i].route);
        useMenuStore().activeMenuIndex = currentTab.value+'';
      }
    }
  }

  function getCenterTabs(){
    storageTabs = JSON.parse(sessionStorage.getItem("centerTabs")+'');
    if(!storageTabs){
      storageTabs=[];
      centerTabs.value=storageTabs;
    }
    return centerTabs.value;
  }
  return { currentTab, getCenterTabs, addTab, delTab,changeTab }
})

export const useUserStore = defineStore("user", () => {
  const loginUser = ref<User>();
  async function loadLoginUser() {
    await ApiGetLoginUser().then(res => {
      if (res.code == 200) {
        loginUser.value = res.data;
      }
    });
    return new Promise((resolve) => {
      resolve("")
    })
  }
  return { loginUser, loadLoginUser };
})