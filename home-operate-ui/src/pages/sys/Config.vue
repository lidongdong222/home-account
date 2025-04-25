<script setup lang="ts">
import { SysConfig,ApiGetSysConfigList,ApiAddSysConfig,ApiUpdSysConfig,ApiDelSysConfig} from "@/utils/api/sys-config-api";
import Base from '@/utils/base';
import { FormRules } from 'element-plus';


const queryForm = ref({
    mhcx: "",
    pageNum: Base.defalutPageNum,
    pageSize: Base.defalutPageSize
});
const configList = ref<SysConfig[]>([]);
const configListLoading = ref(false);
const configTotal = ref(0);
const configForm = ref<SysConfig>({});
const isEdit = ref(false);
const configWinShow = ref(false)
const configFormRef = ref();
const configFormRules: FormRules<SysConfig> = ({
    configKey: [
        { required: true, message: "配置参数不能为空！", trigger: 'change' }
    ],
    configValue: [
        { required: true, message: "配置值不能为空", trigger: 'change' },
    ]
});

onMounted(() => {
    reloadData();
})
watch([() => queryForm.value.pageNum, () => queryForm.value.pageSize], () => {
    reloadData();
}, { immediate: false });

function reset(){
    queryForm.value = {
        mhcx: "",
        pageNum: queryForm.value.pageNum,
        pageSize: queryForm.value.pageSize, 
    }
}
// 显示窗口
function showAddWin() {
    isEdit.value = false;
    configFormRef.value?.resetFields();
    configForm.value = {};
    configWinShow.value = true
}
var addConfigLoading = ref(false);
// 新增、修改
function addConfig() {
    addConfigLoading.value = true;
    configFormRef.value.validate((valid: boolean) => {
        if (valid) {
            if (isEdit.value) {
                ApiUpdSysConfig(configForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addConfigLoading.value = false;
                })
            } else {
                ApiAddSysConfig(configForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addConfigLoading.value = false;
                })
            }
        } else {
            addConfigLoading.value = false;
        }

    })
}
function showUpdWin(config: SysConfig) {
    isEdit.value = true;
    Object.assign(configForm.value, config)
    configWinShow.value = true
    configFormRef.value?.clearValidate()
}
function reloadData() {
    configListLoading.value = true;
    ApiGetSysConfigList(queryForm.value).then((res: any) => {
        if (res.code == 200) {
            configList.value = res.list;
            configTotal.value = res.total;
        }
    });
    configListLoading.value = false;
}

function handlerQuerySeccess(msg: string) {
    configWinShow.value = false
    reloadData()
    ElMessage({
        message: msg,
        type: 'success',
    })
}
function delSysConfig(config: SysConfig) {
    let myInstance: any = null;
    ElMessageBox.confirm(
        '请再次确认是否删除？',
        '操作确认',
        {
            type: 'warning',
            showClose: false,
            buttonSize: 'small',
            cancelButtonText: '取消',
            confirmButtonText: '确认',
            beforeClose: (action, instance, done) => {
                if (action === 'confirm') {
                    myInstance = instance;
                    myInstance.confirmButtonLoading = true
                    done();
                } else {
                    done();
                }
            },
        }
    )
        .then(() => {
            ApiDelSysConfig(config.configId + '').then((res: any) => {
                if (res.code == "200") {
                    handlerQuerySeccess(res.msg);
                }
                myInstance.confirmButtonLoading = false;
            })
        })
        .catch(() => {
        })
}
</script>


<template>
    <div class="h-standard">
        <div class="h-query">
            <el-row>
                <el-col :span="18">
                    <div class="h-query-item">
                        <label>批量信息</label>
                        <el-input v-model="queryForm.mhcx" placeholder="请输入..." clearable />
                    </div>
                </el-col>
                <el-col :span="6">
                    <div class="h-query-button">
                        <el-button type="primary" @click="reloadData">查询</el-button>
                        <el-button type="primary" @click="reset">重置</el-button>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="h-operate">
            <el-button type="primary" @click="showAddWin">新增</el-button>
        </div>
        <el-table :data="configList" border row-key="configId" stripe :fit="true" v-loading="configListLoading" row-class-name="h-table-hover"
            :header-cell-style="{ 'text-align': 'center' }">
            <el-table-column prop="configKey" label="配置参数" />
            <el-table-column prop="configValue" label="配置值" />
            <el-table-column prop="configDesc" label="配置描述" show-overflow-tooltip/>
            <el-table-column align="right" width="240">
                <template #header>
                    操作
                </template>
                <template #default="scope">
                    <el-button link @click="showUpdWin(scope.row)" type="primary">修改</el-button>
                    <el-button link @click="delSysConfig(scope.row)" type="danger">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="h-page">
            <el-pagination :current-page="queryForm.pageNum" :page-size="queryForm.pageSize"
                :page-sizes="Base.defaultPageSizeList" :background="true" layout=" prev, pager, next,total, sizes,"
                :total="configTotal" @size-change="(size: number) => queryForm.pageSize = size"
                @current-change="(size: number) => queryForm.pageNum = size" />
        </div>
    </div>
    <el-dialog v-model="configWinShow" :title="'配置' + (isEdit ? '修改' : '新增')" width="400" draggable
            :close-on-click-modal="false">
            <el-form :model="configForm" label-position="right" :rules="configFormRules" ref="configFormRef"
                label-width="90px">
                <el-form-item label="配置参数：" prop="configKey">
                    <el-input v-model="configForm.configKey" clearable :disabled="isEdit" />
                </el-form-item>
                <el-form-item label="配置值：" prop="configValue">
                    <el-input v-model="configForm.configValue" clearable />
                </el-form-item>
                <el-form-item label="配置描述：" prop="configDesc">
                    <el-input v-model="configForm.configDesc" clearable type="textarea" style="width: 220px;"/>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button :loading="addConfigLoading" size="small" type="primary"
                        @click="addConfig()">保存</el-button>
                </div>
            </template>
        </el-dialog>
</template>



<style scoped lang="scss">
.el-input {
    --el-input-width: 220px;
}

.el-input-number {
    width: 220px;
}

.el-select {
    --el-select-width: 220px;
}

.dialog-footer {
    text-align: center;
}


.data-query-page {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
}

.el-pagination {
    margin-top: 4px;
    display: inline-flex;
    margin-right: 10px;
}
</style>