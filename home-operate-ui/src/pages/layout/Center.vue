<script setup lang="ts">
import { useTabsStore } from '@/store/sysStore'

const tabsStore = useTabsStore();

const removeTab = (targetName: string) => {
  tabsStore.delTab(targetName);
}
const changeTab = (targetName: string) => {
  tabsStore.changeTab();
}

</script>

<template>
  <div class="center">
    <el-tabs v-model="tabsStore.currentTab" type="card" closable @tab-remove="removeTab" @tab-change="changeTab">
      <el-tab-pane v-for="item in tabsStore.getCenterTabs()" :key="item.name" :label="item.title" :name="item.name">
      </el-tab-pane>
    </el-tabs>
    <div class="center-route">
      <RouterView v-slot="{ Component }">
        <KeepAlive max="10">
          <component :is="Component"></component>
        </KeepAlive>
      </RouterView>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.center {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.center-route {
  width: 100%;
  height: calc(100% - 35px);
}

.el-tabs {
  height: 33px;
}

::v-deep(.el-tabs__header) {
  height: 30px;
}

::v-deep(.el-tabs__item) {
  height: 30px;
  font-size: 12px;
}
</style>