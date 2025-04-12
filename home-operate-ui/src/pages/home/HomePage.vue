<script setup lang="ts">
import Center from '@/pages/layout/Center.vue';
import Sidebar from '@/pages/layout/Sidebar.vue';
import Top from '@/pages/layout/Top.vue';
</script>

<template>
        <div class="top">
            <Top />
        </div>
        <div class="main">
            <Sidebar />
            <Center />
        </div>
    
</template>

<style scoped lang="scss">
body{
    height: 100vh;
    display: flex;
    flex-direction: column;
    
}

.top {
  height: 42px;
}

.main {
  height: calc(100vh - 42px);
  display: flex;
}
</style>