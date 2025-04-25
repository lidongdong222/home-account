<script setup lang="ts">
import { apiExporSubjecttUrl, Subject, ApiGetSubjectList, ApiAddSubject, ApiDelSubject, ApiUpdSubject } from "@/utils/api/account-api";
import Base from '@/utils/base';
import { RepCloumn, ApigetRepColumns } from "@/utils/api/report-api";
import { ApiResource } from "@/utils/api/common/resource-api";

const total = ref(0);
const subjectTableRef: any = ref();
const queryForm = ref<any>({
    pageSize: Base.defalutPageSize,
    pageNum: Base.defalutPageNum
});
const subjectList = ref<Subject[]>([]);
const columnList = ref<RepCloumn[]>([]);
const subjectForm = ref<Subject>({});
const isEdit = ref(false);
const subjectWinShow = ref(false)
const subjectFormRef = ref();
let repId = 5;
const subjectFormRules = ({
    subType: [
        { required: true, message: "科目分类不能为空！", trigger: 'blur' },
    ],
    subCode: [
        { required: true, message: "科目编码不能为空！", trigger: 'blur' }
    ],
    subName: [
        { required: true, message: "科目名称不能为空！", trigger: 'blur' },
    ],
    subDesc: [
        { max: 100, message: "长度不能超过100！", trigger: 'blur' }
    ]

});
watch([() => queryForm.value.pageNum, () => queryForm.value.pageSize], () => {
    reloadData();
}, { immediate: false });
onMounted(() => {
    reloadData();
    loadColumns();
})
// 显示窗口
function showAddWin(row: any) {
    isEdit.value = row ? true : false;
    if (isEdit.value) {
        Object.assign(subjectForm.value, row)
    } else {
        subjectFormRef.value?.resetFields();
        subjectForm.value = {};
    }
    subjectWinShow.value = true;
}
// 新增、修改
const addSubjectLoading = ref(false);
function addSubject() {
    subjectFormRef.value.validate((valid: boolean) => {
        if (valid) {
            addSubjectLoading.value = true;
            if (isEdit.value) {
                ApiUpdSubject(subjectForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    } else {
                        ElMessage.error(res.msg)
                    }
                    addSubjectLoading.value = false;
                })
            } else {
                ApiAddSubject(subjectForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    } else {
                        ElMessage.error(res.msg)
                    }
                    addSubjectLoading.value = false;
                })
            }
        }
    })
}
function delSubject(row: any) {
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
    ).then(() => {
        ApiDelSubject(row.subId + "").then((res: any) => {
            if (res.code == "200") {
                handlerQuerySeccess(res.msg);
            }
        })
    })
        .catch(() => {
        })
}

function reloadData() {
    ApiGetSubjectList(queryForm.value).then(res => {
        if (res.code == 200) {
            cacheExportParams = queryForm.value;
            subjectList.value = res.list;
            total.value = res.total;
        }
    });
}
function loadColumns() {
    ApigetRepColumns({ repId: repId }).then(res => {
        columnList.value = res.list
    });
}
function handlerQuerySeccess(msg: string) {
    subjectWinShow.value = false
    reloadData()
    ElMessage({
        message: msg,
        type: 'success',
    })
}
function reset() {
    queryForm.value = {
        pageSize: Base.defalutPageSize,
        pageNum: Base.defalutPageNum
    };
}
const resourceExportLoading = ref(false);
let cacheExportParams: any = null;
function exportData(exportType: string) {
    resourceExportLoading.value = true;
    Object.assign(cacheExportParams, { exportType: exportType, repId: repId })
    ApiResource(cacheExportParams, apiExporSubjecttUrl).then(() => resourceExportLoading.value = false);
}
</script>

<template>
    <div class="h-standard">
        <div class="h-query">
            <el-row>
                <el-col :span="6">
                    <div class="h-query-item">
                        <label>科目分类</label>
                        <el-select v-model="queryForm.subType" clearable>
                            <el-option label="收入" value="1" />
                            <el-option label="支出" value="2" />
                        </el-select>
                    </div>
                    <div class="h-query-item">
                        <label>科目名称1</label>
                        <el-input v-model="queryForm.subName1" clearable />
                    </div>
                </el-col>
                <el-col :span="6">
                    <div class="h-query-item">
                        <label>科目代码</label>
                        <el-input v-model="queryForm.subCode" clearable />
                    </div>
                    <div class="h-query-item">
                        <label>科目代码2</label>
                        <el-input v-model="queryForm.subCode2" clearable />
                    </div>
                </el-col>
                <el-col :span="6">
                    <div class="h-query-item">
                        <label>科目名称</label>
                        <el-input v-model="queryForm.subName" clearable />
                    </div>
                    <div class="h-query-item">
                        <label>科目名称2</label>
                        <el-input v-model="queryForm.subName2" clearable />
                    </div>
                </el-col>
                <el-col :span="6">
                    <div class="h-query-item">
                        <label>科目代码1</label>
                        <el-input v-model="queryForm.subCode1" clearable />
                    </div>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="18">
                    <div class="h-query-item">
                        <label>创建时间</label>
                        <el-date-picker v-model="queryForm.startCreateDate" type="datetime" placeholder="请选择开始时间" ,
                            value-format="YYYY-MM-DD HH:mm:ss" :disabled-date="(date: any) => date > new Date()" />
                        至
                        <el-date-picker v-model="queryForm.endCreateDate" type="datetime" placeholder="请选择结束时间" ,
                            value-format="YYYY-MM-DD HH:mm:ss" :disabled-date="(date: any) => date > new Date()" />
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
            <el-button type="primary" @click="showAddWin(null)">新增</el-button>
            <el-button type="primary" @click="exportData('EXCEL')" :loading="resourceExportLoading">导出</el-button>
        </div>
        <el-table :data="subjectList" row-key="subId" border :lazy="true" ref="subjectTableRef" row-class-name="h-table-hover" stripe>
            <el-table-column align="center" type="index" label="序号" width="50" />
            <template v-for="item in columnList" :key="item.colId">
                <el-table-column :align="item.colDataType == 'number' ? 'right' : 'center'" :prop="item.colProp"
                    :label="item.colName" :width="item.width ? item.width : ''" show-overflow-tooltip>
                    <template v-if="item.colDataType == 'number' && item.isNumberFormat == '1'" #default="scope">
                        {{ scope.row[item.colProp!].toFixed(2) }}
                    </template>
                </el-table-column>
            </template>
            <el-table-column align="center" width="100">
                <template #header>
                    操作
                </template>
                <template #default="scope">
                    <el-button link @click="showAddWin(scope.row)" type="primary">编辑</el-button>
                    <el-button v-show="scope.row.hasChildren ? false : true" link @click="delSubject(scope.row)"
                        type="primary">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="h-page">
            <el-pagination :current-page="queryForm.pageNum" :page-size="queryForm.pageSize"
                :page-sizes="Base.defaultPageSizeList" :background="true" layout=" ->, prev, pager, next,total,sizes"
                :total="total" @size-change="(size: number) => queryForm.pageSize = size"
                @current-change="(size: number) => queryForm.pageNum = size" />
        </div>
    </div>

    <el-dialog v-model="subjectWinShow" :title="'科目' + (isEdit ? '修改' : '新增')" width="600" draggable
        :close-on-click-modal="false">
        <el-form :model="subjectForm" label-position="right" :rules="subjectFormRules" ref="subjectFormRef"
            label-width="90px">
            <el-form-item label="科目分类：" prop="subType">
                <el-select v-model="subjectForm.subType" clearable>
                    <el-option label="收入" value="1" />
                    <el-option label="支出" value="2" />
                </el-select>
            </el-form-item>
            <el-form-item label="科目编码：" prop="subCode">
                <el-input v-model="subjectForm.subCode" clearable />
            </el-form-item>
            <el-form-item label="科目名称：" prop="subName">
                <el-input v-model="subjectForm.subName" clearable />
            </el-form-item>
            <el-form-item v-show="isEdit ? true : false" label="排序值：" prop="sort">
                <el-input v-model="subjectForm.sort" clearable />
            </el-form-item>
            <el-form-item label="科目描述：" prop="subDesc">
                <el-input v-model="subjectForm.subDesc" clearable type="textarea" style="width: 400px;" />
            </el-form-item>
        </el-form>
        <div class="h-tip">
            温馨提示：“科目代码”和“科目名称”不同级次之间用“-”连接，保存后，自动将各级次单独的科目进行保存！
        </div>
        <template #footer>
            <div class="dialog-footer">
                <el-button :loading="addSubjectLoading" size="small" type="primary" @click="addSubject">保存</el-button>
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

.el-form {
    .el-input {
        --el-input-width: 400px;
    }

    .el-select {
        --el-select-width: 400px;
    }
}

.h-tip {
    margin-left: 20px;
}

.el-button {
    margin-left: 6px;
}
</style>