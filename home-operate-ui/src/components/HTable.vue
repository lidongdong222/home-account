<script setup lang="ts">
import { ElScrollbar } from 'element-plus/es';

const emits = defineEmits(["routeMetadata"])
const props = defineProps({
    sheet: {
        type: Object,
        require: true
    }
})
const { sheet } = toRefs<any>(props);
const hasSplitHeader = computed(() => {
    return props.sheet?.splitHeader && props.sheet?.splitHeader.length > 0
})
function dataClass(colDataType: any) {
    if (colDataType == 'string') {
        return 'h-statistics-data-string';
    }
    return 'h-statistics-data-number';
}
function dataFormat(data: any, colInfo: any) {
    if (data) {
        if (colInfo.colDataType == 'number' && colInfo.isNumberFormat == 1 && !isNaN(data[colInfo.colProp])) {
            return parseFloat(data ? data[colInfo.colProp] : 0).toFixed(2);
        }
    }
    return data[colInfo.colProp];
}
function dataRowSpan(headerItem: any, dataItem: any, index: number) {
    let rowSpan = 1;
    if (headerItem.isDataMergeCell == 1) {
        for (let i = index + 1; i < props.sheet?.data.length; i++) {
            if (props.sheet?.data[i][headerItem.colProp] == dataItem[headerItem.colProp]) {
                rowSpan++;
            } else {
                break;
            }
        }
    }
    return rowSpan;
}
function isdataRowSpan(headerItem: any, dataItem: any, index: number) {
    if (headerItem.isDataMergeCell == 1) {
        for (let i = 0; i < props.sheet?.data.length; i++) {
            if (i == index - 1 && props.sheet?.data[i][headerItem.colProp] == dataItem[headerItem.colProp]) {
                return false;
            }
        }
    }
    return true;
}
function routeMetadata(colInfo: any, rowInfo: any, dataIndex: number) {
    console.log(dataIndex)
    if (colInfo.isMetadata == '1') {
        if (props.sheet?.leftHeaders.length > 0) {
            if(props.sheet?.leftHeaders[dataIndex].isMetadata=="1"){
                emits("routeMetadata", colInfo, rowInfo, dataIndex)
            }
            return;
        }
        emits("routeMetadata", colInfo, rowInfo, dataIndex)
    }
}
function metadataClass(dItem: any, index: number) {
    if (dItem.isMetadata == '1') {
        if (props.sheet?.leftHeaders.length > 0) {
            return props.sheet?.leftHeaders[index].isMetadata=="1"?"h-metadata":"";
        }
        return "h-metadata";
    }
    return "";
}
</script>
<template>
    <ElScrollbar>
        <table style="width: 100%;height:100%">
            <thead>
                <tr>
                    <th v-if="hasSplitHeader" class="h-statistics-header-split"
                        :colspan="sheet?.headers[0].colSpan">
                        <span v-for="(dItem, dIndex) in sheet?.splitHeader" :key="dIndex">{{ dItem }}</span>
                    </th>
                    <template v-for="(item, index) in sheet?.headers" :key="index">
                        <th v-if="item.colSpan > 0 && (hasSplitHeader && index > 0) || !hasSplitHeader">
                            <span>{{ item.colName }}</span>
                        </th>
                    </template>

                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in sheet!.data" :key="index">
                    <td v-if="sheet!.leftHeaders.length > 0" class="h-statistics-header-left">{{
                        sheet!.leftHeaders[index].rowName }}</td>
                    <template v-for="(dItem, dIndex) in sheet?.headers" :key="dIndex"
                        :class="dataClass(dItem.colDataType)">
                        <td v-if="(sheet!.leftHeaders.length == 0 || dIndex > 0) && isdataRowSpan(dItem, item, index)"
                            :class="dataClass(dItem.colDataType)" :rowspan="dataRowSpan(dItem, item, index)">
                            <span :class="metadataClass(dItem, index)" @click="routeMetadata(dItem, item, index)">
                                {{ dataFormat(sheet?.data[index], dItem) }}
                            </span>
                        </td>
                    </template>

                </tr>
            </tbody>
        </table>
    </ElScrollbar>
</template>

<style lang="scss" scoped>
table,
thead,
tbody,
tr,
th,
td {
    border-spacing: 0;
    border-width: 1px;
    border-style:solid;
    border-color: rgb(130, 126, 126);
    border-collapse:collapse;
}

table {
    padding: 0 10px;
    table-layout: fixed;
    border: 0;
    font-size: small;
}

thead {
    background-color: #99CCFF;
    color: #606266;

    tr{
        height: 45px;
    }
    th {
        position: sticky;
        top: 0;
        background-color: #99CCFF;
    }
}

tbody tr {
    height: 40px;
}

.h-statistics-header-left {
    background-color: #99CCFF;
    font-weight: bold;
    text-align: center;
    color: #606266;
    position: sticky;

    span {
        display: inline-block;
        position: absolute;
    }

    :nth-child(0) {
        bottom: 10px;
    }
}

.h-statistics-header-split {
    background: #99CCFF url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiPjxsaW5lIHgxPSIwIiB5MT0iMCIgeDI9IjEwMCUiIHkyPSIxMDAlIiBzdHJva2U9InJnYigxMzAsIDEyNiwgMTI2KSIgc3Ryb2tlLXdpZHRoPSIxIi8+PC9zdmc+) no-repeat 100% center;
    position: sticky;
    width: 120px;

    span {
        display: inline-block;
        width: 50%;
        height: 50%;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    :nth-child(1) {
        position: absolute;
        top: 0px;
        right: 0px;
    }

    :nth-child(2) {
        position: absolute;
        bottom: 0px;
        left: 0px;
    }
}

.h-statistics-header-no-split {
    height: 45px;
}

.h-statistics-data-number {
    text-align: right;
    color: #606266;
    padding: 3px;
}

.h-statistics-data-string {
    text-align: left;
    font-size: 12px;
    color: #606266;
    padding: 3px;
}

.h-metadata:hover {
    color: blue;
    cursor: pointer;
    text-decoration: underline;
}
</style>