<script setup lang="ts">
import router from "@/router";
function backToIndex() {
    router.push("/");
}
</script>
<template>
    <div class="h-404">
        <p>页面内容不存在！</p>
    </div>
</template>
<style lang="scss" scoped>
.h-404{
    width: 100%;
    height: 100%;
    align-content:center;
    text-align: center;
}
</style>