<script setup lang="ts">
import { Role, ApiGetRoleList, ApiAddRole, ApiUpdRole } from "@/utils/api/sys-user-api";
import Base from '@/utils/base';
import { FormRules } from 'element-plus';


const queryForm = ref({
    mhcx: "",
    pageNum: Base.defalutPageNum,
    pageSize: Base.defalutPageSize
});
const roleList = ref<Role[]>([]);
const roleListLoading = ref(false);
const roleTotal = ref(0);
const roleForm = ref<Role>({});
const isEdit = ref(false);
const roleWinShow = ref(false)
const roleFormRef = ref();
const roleFormRules: FormRules<Role> = ({
    roleCode: [
        { required: true, message: "角色编码不能为空", trigger: 'change' },
    ],
    roleName: [
        { required: true, message: "角色名不能为空", trigger: 'change' },
    ],
    roleDesc: [
        {},
    ]
});

onMounted(() => {
    reloadData();
})
watch([() => queryForm.value.pageNum, () => queryForm.value.pageSize], () => {
    reloadData();
}, { immediate: false });


function reset() {
    queryForm.value = {
        mhcx: "",
        pageNum: queryForm.value.pageNum,
        pageSize: queryForm.value.pageSize, 
    }
}
// 显示窗口
function showAddWin() {
    isEdit.value = false;
    roleFormRef.value?.resetFields();
    roleForm.value = {};
    roleWinShow.value = true
}
var addRoleLoading = ref(false);
// 新增、修改
function addRole() {
    addRoleLoading.value = true;
    roleFormRef.value.validate((valid: boolean) => {
        if (valid) {
            if (isEdit.value) {
                ApiUpdRole(roleForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addRoleLoading.value = false;
                })
            } else {
                ApiAddRole(roleForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addRoleLoading.value = false;
                })
            }
        } else {
            addRoleLoading.value = false;
        }

    })
}
function showUpdWin(role: Role) {
    isEdit.value = true;
    Object.assign(roleForm.value, role)
    roleWinShow.value = true
    roleFormRef.value?.clearValidate()
}
function reloadData() {
    roleListLoading.value = true;
    ApiGetRoleList(queryForm.value).then((res: any) => {
        if (res.code == 200) {
            roleList.value = res.list;
            roleTotal.value = res.total;
        }
    });
    roleListLoading.value = false;
}

function handlerQuerySeccess(msg: string) {
    roleWinShow.value = false
    reloadData()
    ElMessage({
        message: msg,
        type: 'success',
    })
}
function disable(role: Role) {
    let myInstance: any = null;
    ElMessageBox.confirm(
        '请再次确认是否停用？',
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
            role.status = '0';
            ApiUpdRole(role).then((res: any) => {
                if (res.code == "200") {
                    handlerQuerySeccess(res.msg);
                }
                myInstance.confirmButtonLoading = false;
            })
        })
        .catch(() => {
        })
}
function enable(role: Role) {
    role.status = '1';
    ApiUpdRole(role).then((res: any) => {
        if (res.code == "200") {
            handlerQuerySeccess(res.msg);
        }
    })
}
</script>


<template>
    <div class="h-standard">
        <div class="h-query">
            <el-row>
                <el-col :span="18">
                    <div class="h-query-item">
                        <label>用户信息：</label>
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
        <el-table :data="roleList" border row-key="roleId" stripe :fit="true" v-loading="roleListLoading" row-class-name="h-table-hover"
            :header-cell-style="{ 'text-align': 'center' }">
            <el-table-column align="center" prop="roleCode" label="角色编码" />
            <el-table-column align="center" prop="roleName" label="角色名称" />
            <el-table-column align="center" prop="roleDesc" label="角色描述" />
            <el-table-column align="center" prop="statusDesc" label="状态" />
            <el-table-column align="right" width="240">
                <template #header>
                    操作
                </template>
                <template #default="scope">
                    <el-button link @click="showUpdWin(scope.row)" type="primary">修改</el-button>
                    <el-button v-show="scope.row.status == '1'" link @click="disable(scope.row)"
                        type="danger">停用</el-button>
                    <el-button v-show="scope.row.status == '0'" link @click="enable(scope.row)"
                        type="danger">启用</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="h-page">
            <el-pagination :current-page="queryForm.pageNum" :page-size="queryForm.pageSize"
                :page-sizes="Base.defaultPageSizeList" :background="true" layout=" prev, pager, next,total, sizes,"
                :total="roleTotal" @size-change="(size: number) => queryForm.pageSize = size"
                @current-change="(size: number) => queryForm.pageNum = size" />
        </div>
    </div>
    <el-dialog v-model="roleWinShow" :title="'角色' + (isEdit ? '修改' : '新增')" width="400" draggable
        :close-on-click-modal="false">
        <el-form :model="roleForm" label-position="right" :rules="roleFormRules" ref="roleFormRef" label-width="90px">
            <el-form-item label="角色编码：" prop="roleCode">
                <el-input v-model="roleForm.roleCode" clearable :disabled="isEdit" />
            </el-form-item>
            <el-form-item label="角色名称：" prop="roleName">
                <el-input v-model="roleForm.roleName" clearable />
            </el-form-item>
            <el-form-item label="角色描述：" prop="roleDesc">
                <el-input v-model="roleForm.roleDesc" clearable type="textarea" style="width: 220px;" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button :loading="addRoleLoading" size="small" type="primary" @click="addRole()">保存</el-button>
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