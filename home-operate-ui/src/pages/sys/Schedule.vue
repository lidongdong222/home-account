<script setup lang="ts">
import { Search } from '@element-plus/icons-vue'
import { Schedule, ApiAddSchedule, ApiUpdSchedule, ApiGetScheduleList, ApiDelSchedule, ApiExecImmediateSchedule } from "@/utils/api/sys-schedule-api";
import Base from '@/utils/base';
import { FormRules } from 'element-plus';


const queryForm = ref<any>({
    mhcx: "",
    pageNum: Base.defalutPageNum,
    pageSize: Base.defalutPageSize
});
const scheduleList = ref<Schedule[]>([]);
const scheduleListLoading = ref(false);
const scheduleTotal = ref(0);
const scheduleForm = ref<Schedule>({});
const isEdit = ref(false);
const scheduleWinShow = ref(false)
const scheduleFormRef = ref();
const scheduleFormRules: FormRules<Schedule> = ({
    jobId: [
        { required: true, message: "批量id不能为空！", trigger: 'change' }
    ],
    groupId: [
        { required: true, message: "批量组不能为空", trigger: 'change' },
    ],
    cron: [
        { required: true, message: "执行时间不能为空！", trigger: 'change' }
    ],
    className: [
        { required: true, message: "执行类不能为空！", trigger: 'change' }
    ],
    status: [
        { required: true, message: "启用状态不能为空！", trigger: 'change' }
    ]

});

onMounted(() => {
    reloadData();
})
watch([() => queryForm.value.pageNum, () => queryForm.value.pageSize], () => {
    reloadData();
}, { immediate: false });

// 显示窗口
function showAddWin() {
    isEdit.value = false;
    scheduleFormRef.value?.resetFields();
    scheduleForm.value = { status: '1', highFrequency: '2' };
    scheduleWinShow.value = true
}
var addAccountLoading = ref(false);
// 新增、修改
function addAccount() {
    addAccountLoading.value = true;
    scheduleFormRef.value.validate((valid: boolean) => {
        if (valid) {
            if (isEdit.value) {
                ApiUpdSchedule(scheduleForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addAccountLoading.value = false;
                })
            } else {
                ApiAddSchedule(scheduleForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addAccountLoading.value = false;
                })
            }
        } else {
            addAccountLoading.value = false;
        }

    })
}
function showUpdWin(schedule: Schedule) {
    isEdit.value = true;
    Object.assign(scheduleForm.value, schedule)
    scheduleForm.value.status += '';
    scheduleWinShow.value = true
    scheduleFormRef.value?.clearValidate()
}
function reloadData() {
    scheduleListLoading.value = true;
    ApiGetScheduleList(queryForm.value).then((res: any) => {
        if (res.code == 200) {
            scheduleList.value = res.list;
            scheduleTotal.value = res.total;
        }
    });
    scheduleListLoading.value = false;
}

function handlerQuerySeccess(msg: string) {
    scheduleWinShow.value = false
    reloadData()
    ElMessage({
        message: msg,
        type: 'success',
    })
}
function delSchedule(schedule: Schedule) {
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
            ApiDelSchedule(schedule.scheId + '').then((res: any) => {
                if (res.code == "200") {
                    handlerQuerySeccess(res.msg);
                }
                myInstance.confirmButtonLoading = false;
            })
        })
        .catch(() => {
        })
}
const execImmediateLoading = ref(false);
function execImmediate(row: Schedule) {
    execImmediateLoading.value = true;
    ApiExecImmediateSchedule(row.scheId + '').then((res: any) => {
        if (res.code == "200") {
            ElMessage.success(res.msg)
        }
        execImmediateLoading.value = false;
    })
}
function reset() {
    queryForm.value = {
        mhcx: '',
        pageNum: Base.defalutPageNum,
        pageSize: Base.defalutPageSize,
    };
}
</script>


<template>
    <div class="h-standard">
        <div class="h-query">
            <el-row>
                <el-col :span="18">
                    <div class="h-query-item">
                        <label>批量信息：</label>
                        <el-input v-model="queryForm.mhcx" placeholder="请输入..." :prefix-icon="Search" clearable />
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
        <el-table :data="scheduleList" border row-key="scheduleId" stripe :fit="true" v-loading="scheduleListLoading" row-class-name="h-table-hover">
            <el-table-column prop="jobId" label="批量id" />
            <el-table-column prop="groupId" label="批量组" />
            <el-table-column prop="cron" label="执行时间" width="100" />
            <el-table-column prop="className" label="执行类" show-overflow-tooltip />
            <el-table-column prop="remark" label="批量描述" show-overflow-tooltip />
            <el-table-column prop="statusName" label="状态" width="60" />
            <el-table-column align="right" width="240">
                <template #header>
                    操作
                </template>
                <template #default="scope">
                    <el-button link @click="execImmediate(scope.row)" :loading="execImmediateLoading"
                        type="primary">立即执行</el-button>
                    <el-button link @click="showUpdWin(scope.row)" type="primary">修改</el-button>
                    <el-button link @click="delSchedule(scope.row)" type="danger">删除</el-button>
                    <el-button link @click="showUpdWin(scope.row)" type="primary">执行记录</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="h-page">
            <el-pagination :current-page="queryForm.pageNum" :page-size="queryForm.pageSize"
                :page-sizes="Base.defaultPageSizeList" :background="true" layout="->,prev, pager, next,total, sizes,"
                :total="scheduleTotal" @size-change="(size: number) => queryForm.pageSize = size"
                @current-change="(size: number) => queryForm.pageNum = size" />
        </div>
    </div>
    <el-dialog v-model="scheduleWinShow" :title="'账单' + (isEdit ? '修改' : '新增')" width="500" draggable
        :close-on-click-modal="false">
        <el-form :model="scheduleForm" label-position="right" :rules="scheduleFormRules" ref="scheduleFormRef"
            label-width="90px">
            <el-form-item label="批量id：" prop="jobId">
                <el-input v-model="scheduleForm.jobId" clearable :disabled="isEdit" />
            </el-form-item>
            <el-form-item label="批量组：" prop="groupId">
                <el-input v-model="scheduleForm.groupId" clearable :disabled="isEdit" />
            </el-form-item>
            <el-form-item label="执行时间：" prop="cron">
                <el-input v-model="scheduleForm.cron" clearable />
                &nbsp;<small style="color:tomato;">cron表达式</small>
            </el-form-item>
            <el-form-item label="执行类：" prop="className">
                <el-input v-model="scheduleForm.className" clearable :disabled="isEdit" />
                &nbsp;<small style="color:tomato;">全路径类名</small>
            </el-form-item>
            <el-form-item label="是否高频：" prop="highFrequency">
                <el-select v-model="scheduleForm.highFrequency" :disabled="isEdit">
                    <el-option label="是" value="1" />
                    <el-option label="否" value="2" />
                </el-select>
            </el-form-item>
            <el-form-item label="状态：" prop="status">
                <el-select v-model="scheduleForm.status">
                    <el-option label="启用" value="1" />
                    <el-option label="停用" value="2" />
                </el-select>
            </el-form-item>
            <el-form-item label="批量描述：" prop="remark">
                <el-input v-model="scheduleForm.remark" clearable type="textarea" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button :loading="addAccountLoading" size="small" type="primary" @click="addAccount()">保存</el-button>
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

.el-pagination {
    margin-top: 4px;
    display: inline-flex;
    margin-right: 10px;
}
</style>