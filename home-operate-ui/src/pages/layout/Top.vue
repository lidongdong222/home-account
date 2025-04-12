<script setup lang="ts">
import { ArrowDown } from '@element-plus/icons-vue'
import { PasswordForm, ApiLogout, ApiUpdPassword } from '@/utils/api/sys-user-api';
import { useUserStore } from '@/store/sysStore'
import { FormRules } from 'element-plus';

const userStore = useUserStore();
const passWin = ref(false),userWin=ref(false);
const passForm = ref<PasswordForm>({});
const passFormRef = ref();
const passFormRules: FormRules<PasswordForm> = ({
    oldPass: [
        { required: true, message: "旧密码不能为空！", trigger: 'change' }
    ],
    newPass: [
        { required: true, message: "新密码不能为空！", trigger: 'change' }
    ],
    newPassAgain: [
        { required: true, message: "新密码不能为空！", trigger: 'change' }
    ]

});
onMounted(() => {
    userStore.loadLoginUser();
})
function logout() {
    ApiLogout().then((res: any) => {
        if (res.code == "200") {
            location.reload();
        }
    })
}
function showUpdPassWin() {
    passWin.value = true;
}
function showUserWin(){
    userWin.value = true;
}

const updPasswordLoading = ref(false);
function updPassword() {
    passFormRef.value.validate((valid: boolean) => {
        if (valid) {
            updPasswordLoading.value=true;
            ApiUpdPassword(passForm.value).then((res: any) => {
                if (res.code == "200") {
                    handlerQuerySeccess(res.msg);
                }
                updPasswordLoading.value = false;
            })
        }
    })
}
function handlerQuerySeccess(msg: string) {
    passWin.value = false
    ElMessage({
        message: msg,
        type: 'success',
    })
}

</script>

<template>
    <div class="top">
        <div class="left">
            <img src="/vite.svg" alt="logo">
            <!-- <span>家庭账本</span> -->
        </div>

        <div class="right">
            <div class="h-top-right-item">
                <span>待办</span>
            </div>
            <div class="h-top-right-item">
                <span>消息</span>
            </div>
            <div class="h-top-right-end">
                <span class="h-top-right-welcome">欢迎！</span>
                <el-dropdown size="default" :show-timeout="10" >
                    <span class="el-dropdown-link">
                        {{ userStore.loginUser?.username }}
                        <el-icon>
                            <arrow-down />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click="showUserWin">个人信息</el-dropdown-item>
                            <el-dropdown-item @click="showUpdPassWin">修改密码</el-dropdown-item>
                            <el-dropdown-item divided @click="logout">注销</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </div>
    </div>
    <el-dialog  v-model="passWin" title="修改密码" width="400" height="300" draggable :close-on-click-modal="false">
        <el-form :inline="true" :model="passForm" label-position="left" :rules="passFormRules" ref="passFormRef"
            label-width="70px">
            <el-form-item label="旧密码" prop="oldPass">
                <el-input v-model="passForm.oldPass" placeholder="请输入旧密码" type="password" />
            </el-form-item>
            <el-form-item label="新密码" prop="newPass">
                <el-input v-model="passForm.newPass" placeholder="请输入新密码" type="password"  />
            </el-form-item>
            <el-form-item label="新密码" prop="newPassAgain">
                <el-input v-model="passForm.newPassAgain" placeholder="请再次输入新密码" type="password"  />
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="updPassword" type="primary" :loading="updPasswordLoading">保存</el-button>
        </template>
    </el-dialog>
    <el-dialog  v-model="userWin" title="个人信息" width="600" height="400" draggable :close-on-click-modal="false">
        <el-divider content-position="left"><strong>基本信息</strong></el-divider>
        <div class="h-info"><label>账号</label>{{userStore.loginUser?.usercode}}</div>
        <div class="h-info"><label>姓名</label>{{userStore.loginUser?.username}}</div>
        <div class="h-info"><label>手机号</label>{{userStore.loginUser?.phone}}</div>
        <el-divider content-position="left"><strong>角色信息</strong></el-divider>
        <div>
            <template v-for="item in userStore.loginUser?.viewRoleNames?.split(',')">
                <el-tag>{{ item }}</el-tag>
            </template>
        </div>
        <el-divider content-position="left"><strong>个人备注</strong></el-divider>
        <div class="h-info">{{ userStore.loginUser?.signature }}</div>
        <template #footer>
        </template>
    </el-dialog>
</template>

<style lang="scss" scoped>
.top {
    height: 42px;
    background-color: #454c54;
    color: rgb(216, 222, 227);
    display: flex;
    align-items: center;
    justify-content: space-between;

    img {
        width: 30px;
        height: 30px;
    }

    .left {
        display: flex;
        align-items: center;
        font-size: large;
        font-weight: bold;
        margin-left: 15px;

        span {
            margin-left: 6px;
            letter-spacing: 1mm;
        }
    }
}

span:focus-visible {
    outline: 0px;
}

.el-form{
    text-align: center;
}
.el-input {
    --el-input-width: 220px;
}

.el-dropdown-link {
    // cursor: pointer;
    // display: flex;
    // align-content: center;
    color: rgb(216, 222, 227);
}

.h-top-right-end {
    font-size: 14px;
    color: rgb(216, 222, 227);
    display: inline-block;
    margin-right: 6px;
    cursor: pointer;

}

.h-top-right-item {
    font-size: 14px;
    color: rgb(216, 222, 227);
    display: inline-block;
    margin-right: 22px;
    cursor: pointer;

    span:hover {
        color: rgb(100, 118, 254)
    }
}
.h-info{
    margin-bottom: 10px;
    font-family:Times, serif;
    label{
        display: inline-block;
        width: 70px;
    }
}
</style>