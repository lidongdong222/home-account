<script setup lang="ts">
import { ElConfigProvider } from 'element-plus';
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
</script>

<template>
  <el-config-provider :locale="zhCn" size="small">
    <router-view />
  </el-config-provider>
</template>

<style scoped lang="scss">
  
</style>
