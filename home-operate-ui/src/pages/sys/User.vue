<script setup lang="ts">
import { User,Role, ApiGetUserList, ApiAddUser, ApiUpdUser, ApiDelUser,ApiGetRoleList} from "@/utils/api/sys-user-api";
import Base from '@/utils/base';
import { FormRules } from 'element-plus';


const queryForm = ref({
    mhcx: "",
    pageNum: Base.defalutPageNum,
    pageSize: Base.defalutPageSize
});
const roleList = ref<Role[]>();
const userList = ref<User[]>([]);
const userListLoading = ref(false);
const userTotal = ref(0);
const userForm = ref<User>({});
const isEdit = ref(false);
const userWinShow = ref(false)
const userFormRef = ref();
const userFormRules: FormRules<User> = ({
    usercode: [
        { required: true, message: "用户账号不能为空", trigger: 'change' },
    ],
    username: [
        { required: true, message: "用户名不能为空", trigger: 'change' },
    ],
    password: [
        { required: true, message: "密码不能为空", trigger: 'change' },
    ],
    roleIds: [
        { required: true, message: "角色不能为空", trigger: 'change' },
    ]
});

onMounted(() => {
    reloadData();
    ApiGetRoleList({
        mhcx: "",
        pageNum: 1,
        pageSize: 1000
    }).then((res: any) => {
        if (res.code == 200) {
            roleList.value = res.list;
        }
    });
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
    userFormRef.value?.resetFields();
    userForm.value = {};
    userWinShow.value = true
}
var addUserLoading = ref(false);
// 新增、修改
function addUser() {
    addUserLoading.value = true;
    userFormRef.value.validate((valid: boolean) => {
        if (valid) {
            if (isEdit.value) {
                ApiUpdUser(userForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addUserLoading.value = false;
                })
            } else {
                ApiAddUser(userForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    }
                    addUserLoading.value = false;
                })
            }
        } else {
            addUserLoading.value = false;
        }

    })
}
function showUpdWin(user: User) {
    isEdit.value = true;
    Object.assign(userForm.value, user)
    userWinShow.value = true
    userFormRef.value?.clearValidate()
}
function reloadData() {
    userListLoading.value = true;
    ApiGetUserList(queryForm.value).then((res: any) => {
        if (res.code == 200) {
            userList.value = res.list;
            userTotal.value = res.total;
        }
    });
    userListLoading.value = false;
}

function handlerQuerySeccess(msg: string) {
    userWinShow.value = false
    reloadData()
    ElMessage({
        message: msg,
        type: 'success',
    })
}

function disable(user: User) {
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
            user.status = '0';
            ApiUpdUser(user).then((res: any) => {
                if (res.code == "200") {
                    handlerQuerySeccess(res.msg);
                }
                myInstance.confirmButtonLoading = false;
            })
        })
        .catch(() => {
        })
}
function enable(user: User) {
    user.status = '1';
    ApiUpdUser(user).then((res: any) => {
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
        <el-table :data="userList" border row-key="userId" stripe :fit="true" v-loading="userListLoading" row-class-name="h-table-hover"
            :header-cell-style="{ 'text-align': 'center' }">
            <el-table-column align="center" prop="usercode" label="用户账号" />
            <el-table-column align="center" prop="username" label="用户名称" />
            <el-table-column align="center" prop="viewRoleNames" label="用户角色" />
            <el-table-column align="center" prop="phone" label="手机号" />
            <el-table-column align="center" prop="remark" label="备注" show-overflow-tooltip />
            <el-table-column align="center" prop="statusDesc" label="状态"  />
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
                :total="userTotal" @size-change="(size: number) => queryForm.pageSize = size"
                @current-change="(size: number) => queryForm.pageNum = size" small />
        </div>
        <el-dialog v-model="userWinShow" :title="'用户' + (isEdit ? '修改' : '新增')" width="400" draggable
            :close-on-click-modal="false">
            <el-form :model="userForm" label-position="right" :rules="userFormRules" ref="userFormRef"
                label-width="90px">
                <el-form-item label="用户账号" prop="usercode">
                    <el-input v-model="userForm.usercode" clearable :disabled="isEdit" />
                </el-form-item>
                <el-form-item label="用户名称" prop="username">
                    <el-input v-model="userForm.username" clearable />
                </el-form-item>
                <el-form-item label="用户角色" prop="roleIds">
                    <el-select v-model="userForm.roleIds" clearable  multiple >
                        <el-option v-for="item in roleList" :key="item.roleId" 
                        :label="item.roleName" :value="item.roleId + ''" />
                    </el-select>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="userForm.phone" clearable />
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="userForm.remark" clearable type="textarea" style="width: 220px;" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button :loading="addUserLoading" size="small" type="primary" @click="addUser()">保存</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
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