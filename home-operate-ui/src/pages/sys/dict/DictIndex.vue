<script setup lang="ts">
import { Search, Edit } from '@element-plus/icons-vue'
import { ApiGetDictList, Dict,  ApiAddDict, ApiDelDict, ApiUpdDict } from "@/utils/api/sys-dict-api";
import { FormRules } from 'element-plus';
import {
    dictType, dictDtlList, dictDtlForm, isDictDtlEdit, dictDtlWinShow, dictDtlFormRef, dictDtlFormRules,
    showAddDictDtlWin, addDictDtl, showUpdDictDtlWin,  reloadDictDtlData
} from "./dict-dtl";
import Base from '@/utils/base';

const total = ref(0);
const currentSelectRow = ref(-1);
const dictList = ref([])
const queryForm = ref<any>({
    pageNum: Base.defalutPageNum,
    pageSize: Base.defalutPageSize,
});
const dictForm = ref(new Dict());
const isDictEdit = ref(false);
const dictWinShow = ref(false)
const dictFormRef = ref();
const dictTableRef = ref();
const dictFormRules = ref<FormRules<Dict>>({
    dictType: [
        { required: true, message: "字典编码不能为空！", trigger: 'blur' },
        { max: 30, message: "长度不能超过30！", trigger: 'blur' }
    ],
    dictName: [
        { required: true, message: "字典名称不能为空！", trigger: 'blur' },
        { max: 30, message: "长度不能超过30！", trigger: 'blur' }
    ],
    status: [
        { required: true, message: "状态不能为空！", trigger: 'blur' },
    ],
    remark: [
        { max: 100, message: "长度不能超过100！", trigger: 'blur' }
    ]

});
onMounted(() => {
    dictType.value = "";
    reloadData();

})
// 显示窗口
function showAddWin() {
    isDictEdit.value = false;
    dictFormRef.value?.resetFields();
    dictForm.value = {};
    dictWinShow.value = true
}

// 新增、修改字典
const addDictLoading = ref(false);
function addDict() {
    addDictLoading.value = true;
    dictFormRef.value.validate((valid: boolean) => {
        if (valid) {
            if (isDictEdit.value) {
                ApiUpdDict(dictForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addDictLoading.value = false;
                })
            } else {
                ApiAddDict(dictForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addDictLoading.value = false;
                })
            }
        }else{
            addDictLoading.value = false;
        }
    })
}
function showUpdWin(dict: Dict) {
    isDictEdit.value = true;
    dictFormRef.value?.clearValidate()
    Object.assign(dictForm.value, dict);
    dictWinShow.value = true
}
function delDict(dict: Dict) {
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
            ApiDelDict(dict.dictType!).then((res: any) => {
                if (res.code == "200") {
                    handlerQuerySeccess(res.msg);
                }
                myInstance.confirmButtonLoading = false;
            })
        })
        .catch(() => {
        })
}
function reloadData() {
    ApiGetDictList(queryForm.value).then(res => {
        total.value = res.total;
        dictList.value = res.list;
    });
}

function handlerQuerySeccess(msg: string) {
    dictWinShow.value = false
    reloadData()
    ElMessage({
        message: msg,
        type: 'success',
    })
}
function currentRow(row: any) {
    return row.row.dictType == currentSelectRow.value ? "color:#CC0033;font-weight:bold;font-size:110%" : ""
}
function selectionChange(currRow: any) {
    currentSelectRow.value = currRow.dictType;
    dictType.value = currRow.dictType;
    reloadDictDtlData();
}
</script>

<template>
    <div class="h-standard">
        <div class="data">
            <div style="width: 40%;">
                <div style="margin-bottom: 6px;">
                    <el-input v-model="queryForm.mhcx" placeholder="请输入..." :prefix-icon="Search" clearable />
                    <el-button type="primary" @click="showAddWin" style="float: right;margin-right: 6px;">新增</el-button>
                    <el-button type="primary" @click="reloadData" style="float: right;margin-right: 6px;">查询</el-button>
                </div>
                <el-table ref="dictTableRef" :row-style="currentRow" @current-change="selectionChange"
                    highlight-current-row :data="dictList" border row-key="dictId" stripe>
                    <el-table-column type="index" label="序号" />
                    <el-table-column prop="dictName" label="数据字典" />
                    <el-table-column prop="statusName" label="状态" />
                    <el-table-column width="80">
                        <template #header>
                            操作
                        </template>
                        <template #default="scope">
                            <el-button :icon="Edit" @click="showUpdWin(scope.row)" circle plain />
                        </template>
                    </el-table-column>
                </el-table>
                <div class="h-page">
                    <el-pagination :current-page="queryForm.pageNum" :page-size="queryForm.pageSize"
                        :page-sizes="Base.defaultPageSizeList" :background="true" layout="->,prev, pager, next,total"
                        :total="total" @size-change="(size: number) => queryForm.pageSize = size"
                        @current-change="(size: number) => queryForm.pageNum = size" />
                </div>
            </div>

            <div style="width: 59%;">
                <div style="margin:0 6px 0 0px;">
                    <el-button type="primary" @click="showAddDictDtlWin"
                        style="float: right;margin-bottom: 6px;">新增</el-button>
                </div>
                <el-table :data="dictDtlList" border row-key="dictId" stripe empty-text="暂无数据字典" >
                    <el-table-column type="index" label="序号" width="50" />
                    <el-table-column prop="dictCode" label="字典代码" />
                    <el-table-column prop="dictValue" label="字典描述" />
                    <el-table-column prop="statusName" label="状态" />
                    <el-table-column width="80">
                        <template #header>
                            操作
                        </template>
                        <template #default="scope">
                            <el-button :icon="Edit" @click="showUpdDictDtlWin(scope.row)" circle plain />
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>


    <el-dialog v-model="dictWinShow" :title="'字典' + (isDictEdit ? '修改' : '新增')" width="400" draggable
        :close-on-click-modal="false">
        <el-form :model="dictForm" label-position="right" :rules="dictFormRules" ref="dictFormRef" label-width="90px">
            <el-form-item label="字典编码：" prop="dictType">
                <el-input v-model="dictForm.dictType" clearable :disabled="isDictEdit" />
            </el-form-item>
            <el-form-item label="字典名称：" prop="dictName">
                <el-input v-model="dictForm.dictName" clearable />
            </el-form-item>
            <el-form-item label="状态：" prop="status">
                <el-select v-model="dictForm.status">
                    <el-option label="启用" value="1" />
                    <el-option label="禁用" value="0" />
                </el-select>
            </el-form-item>
            <el-form-item label="备注：" prop="remark">
                <el-input v-model="dictForm.remark" clearable />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button :loading="addDictLoading" type="primary" @click="addDict()">保存</el-button>
            </div>
        </template>
    </el-dialog>
    <el-dialog v-model="dictDtlWinShow" :title="'字典项' + (isDictEdit ? '修改' : '新增')" width="400" draggable
        :close-on-click-modal="false">
        <el-form :model="dictDtlForm" label-position="right" :rules="dictDtlFormRules" ref="dictDtlFormRef"
            label-width="90px">
            <el-form-item label="字典代码：" prop="dictCode">
                <el-input v-model="dictDtlForm.dictCode" clearable :disabled="isDictDtlEdit" />
            </el-form-item>
            <el-form-item label="字典描述：" prop="dictValue">
                <el-input v-model="dictDtlForm.dictValue" clearable />
            </el-form-item>
            <el-form-item label="状态：" prop="status">
                <el-select v-model="dictDtlForm.status">
                    <el-option label="启用" value="1" />
                    <el-option label="禁用" value="0" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="primary" @click="addDictDtl">保存</el-button>
            </div>
        </template>
    </el-dialog>
</template>



<style scoped lang="scss">
.el-input {
    --el-input-width: 220px;
}

.el-select {
    --el-select-width: 220px;
}

.dialog-footer {
    text-align: center;
}

.data {
    display: flex;
}

.h-page {
    margin-right: 12px;
}
</style>