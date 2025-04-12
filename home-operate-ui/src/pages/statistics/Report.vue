<script setup lang="ts">
import HTable from '@/components/HTable.vue';
import { StatisticsData, ApiGetStatistics } from "@/utils/api/statistics-api";
import { useRoute, useRouter } from 'vue-router';
import { ApiResource } from "@/utils/api/common/resource-api";

const route = useRoute();
const router = useRouter();
const repId = ref(route.query.repId);
const statisticsData = ref<StatisticsData>({
    sheets:[{}]
});
const queryForm = ref<any>({
    year: new Date().getFullYear() + '',
    unit:'1'
});
watch(() => route.query.repId, (newValue) => {
    repId.value = newValue;
    if (newValue) reloadData();
})
const statisticsDate = ref<string>('');
onMounted(() => {
    reloadData();
})
// 新增、修改
function reloadData() {
    ApiGetStatistics({
        repId: repId.value,
        requestParam: JSON.stringify(queryForm.value)
    }).then(res => {
        if (res.code == 200) {
            statisticsData.value = res.data;
            if (statisticsData.value.sheets && statisticsData.value.sheets[0].data!.length>0) {
                statisticsDate.value = statisticsData.value.sheets[0].data![0].createDate;
            }
        }
    })
}
function reset() {
    queryForm.value = {
        year: new Date().getFullYear() + '',
        unit:'1'
    };
}
function routeMetadata(colInfo: any, rowInfo: any, dataIndex: number) {
    let routerPath = colInfo.metadataRouter;
    const routerParams: any = {};
    Object.assign(routerParams, rowInfo)
    Object.assign(routerParams, JSON.parse(colInfo.metadataSelfParams))
    if (statisticsData.value.sheets![0].leftHeaders && statisticsData.value.sheets![0].leftHeaders.length > 0) {
        Object.assign(routerParams, JSON.parse(statisticsData.value.sheets![0].leftHeaders[dataIndex].metadataSelfParams))
    }
    Object.assign(routerParams, { year: queryForm.value.year })
    Object.assign(routerParams, {
        beginMonthPeriod: parseInt(routerParams.year) * 100 + parseInt(routerParams.beginMonth),
        endMonthPeriod: parseInt(routerParams.year) * 100 + parseInt(routerParams.endMonth)
    })
    while (true) {
        let m = routerPath.match(/\$\{[0-9a-zA-Z]+\}/)
        if (m != null) {
            routerPath = routerPath.replace(m[0], routerParams[m[0].replace('${', '').replace('}', '')])
        } else {
            break;
        }
    }
    router.push(routerPath);
}
const exportLoading = ref(false);
function exportStatistics() {
    exportLoading.value = true;
    ApiResource({
        repId: repId.value,
        requestParam: JSON.stringify(queryForm.value)
    }, "/report/exportStatistics").then(()=>{
        exportLoading.value = false;
    });
}
</script>


<template>
    <div class="h-standard">
        <div class="h-query">
            <el-row>
                <el-col :span="6">
                    <div class="h-query-item-short">
                        <label>年度</label>
                        <el-date-picker v-model="queryForm.year" type="year" placeholder="请选择年度" value-format="YYYY"
                            format="YYYY" :disabled-date="(date: any) => date > new Date()" :clearable="false"/>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div class="h-query-item-short">
                        <label>单位</label>
                        <el-select v-model="queryForm.unit">
                            <el-option label="元" value="1" />
                            <el-option label="万元" value="10000" />
                            <el-option label="亿元" value="100000000" />
                        </el-select>
                    </div>
                </el-col>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <div class="h-query-button">
                        <div class="h-query-button-group">
                            <el-button type="primary" @click="reloadData">查询</el-button>
                            <el-button type="primary" @click="reset">重置</el-button>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="h-statistics">
            <div class="h-statistics-title">
                <h3>{{ statisticsData['sheets']![0].title }}</h3>
                
                <h6 v-show="statisticsDate ? true : false">统计时间：{{ statisticsDate }}</h6>
            </div>
            <div class="h-statistics-operate">
                <el-button type="primary" @click="exportStatistics" :loading="exportLoading">导出</el-button>
            </div>
        </div>
        <HTable :sheet="statisticsData['sheets']![0]" @route-metadata="routeMetadata"></HTable>
    </div>
</template>



<style scoped lang="scss">
.el-input-number {
    width: 220px;
}

.el-select {
    --el-select-width: 220px;
}

.el-input {
    --el-input-width: 220px;
}
</style>