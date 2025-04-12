<script setup lang="ts">
import HomeIcon from '@/components/HomeIcon.vue'
import { useMenuStore, useTabsStore } from '@/store/sysStore'

const menuStore = useMenuStore();
const tabStore = useTabsStore();

onMounted(() => {
  menuStore.reloadMenuList().then(() => {
    menuStore.menuList.forEach(m => {
      if (getActiveIndex(m)) return;
    })
  });
})
function menuItemClick(menu:any) {
  tabStore.addTab(menu.menuName,menu.menuId,menu.router);
}
function getActiveIndex(menu: any) {
  if (menu.children) {
    let flag = false;
    menu.children.forEach((m: any) => {
      flag = getActiveIndex(m);
      if (flag) return;
    })
    if (flag) return flag;
  }
  if (location.hash.includes(menu.router + '')) {
    menuStore.activeMenuIndex = menu.menuId + '';
    tabStore.currentTab = menu.menuId;
    return true;
  }
  return false;
}
</script>
<template>
  <div class="sidebar">
    <el-scrollbar>
      <el-menu active-text-color="#ffd04b" background-color="#545c64" class="el-menu-vertical-demo" text-color="#fff"
        :default-active="menuStore.activeMenuIndex">
        <el-sub-menu :index="m.menuId + ''" v-for="m in menuStore.menuList" :key="m.menuId">
          <template #title>
            <HomeIcon :icon="m.icon"></HomeIcon>
            <span>{{ m.menuName }}</span>
          </template>
          <el-menu-item :index="c.menuId + ''" v-if="m.children" v-for="c in m.children" :key="c.menuId"
            @click="menuItemClick(c)">
            <HomeIcon :icon="c.icon"></HomeIcon>
            {{ c.menuName }}
          </el-menu-item>
          <!-- <el-sub-menu index="1-4">
                    <template #title>item four</template>
                    <el-menu-item index="1-4-1">item one</el-menu-item>
                </el-sub-menu> -->
        </el-sub-menu>
      </el-menu>
    </el-scrollbar>
  </div>
</template>
<style lang="scss" scoped>
.sidebar {
  min-width: 180px;
  background-color: #545c64;

  ::v-deep(.el-menu-item) {
    height: 40px;
    line-height: 0;
    text-align: center;
    font-size: 13px;
  }

  ::v-deep(.el-sub-menu__title) {
    height: 40px;
    line-height: 0;
    text-align: center;
    font-size: 13px;
  }

}
</style>