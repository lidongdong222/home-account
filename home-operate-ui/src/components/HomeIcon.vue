<template>
    <el-icon><img :class="size"  :src="'/icon/'+icon+'.svg'"></el-icon>
</template>

<script setup lang="ts">
    defineProps({
        icon:{
            type:String,
            require:true
        },
        size:{
            type:String,
            require:false,
            default:'normal'
        }
    })
</script>

<style lang="scss" scoped>
    .normal{
        width: 16px;
        height: 16px;
    }
    .small{
        width: 12px;
        height: 12px;
    }
</style>