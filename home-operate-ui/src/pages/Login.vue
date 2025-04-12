<script setup lang="ts">
import { FormRules } from 'element-plus';
import { ApiLogin, LoginForm } from '@/utils/api/sys-user-api';
import router from "@/router";

const queryForm = ref<LoginForm>({});
const formRef = ref();
const formRules: FormRules<LoginForm> = ({
    usercode: [
        { required: true, message: "账号不能为空！", trigger: 'blur' }
    ],
    password: [
        { required: true, message: "密码不能为空！", trigger: 'blur' }
    ],
});
onMounted(() => {
    apilogin(true);
    //添加键盘监听
    window.addEventListener('keydown', handleKeyDown);
})
onUnmounted(() => {
  // 组件卸载时移除键盘事件监听
  window.removeEventListener('keydown', handleKeyDown);
});
const loginBtnLoading = ref(false);
function login() {
    formRef.value.validate((valid: boolean) => {
        if (valid) {
            apilogin(false);
        }
    });
}
function apilogin(autoLogin:boolean) {
    loginBtnLoading.value = true;
    queryForm.value.authorization = localStorage.getItem("authorization");
    ApiLogin(queryForm.value).then((res: any) => {
        if (res.code == "200") {
            router.push("/")
        }else{
            if(!autoLogin)ElMessage.error(res.msg);
        }
        loginBtnLoading.value = false;
    })
}
function handleKeyDown(event:KeyboardEvent) {
  // 键盘按下事件处理函数
  if (event.key === 'Enter') {
    // 例如：监听退出键
    login();
  }
}
</script>
<template>
    <div class="h-main">
        <div class="h-top">
            <div class="left">
                <img src="/vite.svg" alt="logo">
                <!-- <span>家庭账本</span> -->
            </div>
        </div>
        <div class="h-login">
            <div class="h-login-brief">
                <el-carousel height="70vh">
                    <el-carousel-item>
                        <img :src="'/image/letter.jpg'" width="100%" height="100%" alt="">
                    </el-carousel-item>
                    <el-carousel-item>
                        <img :src="'/image/heart.jpg'" width="100%" height="100%" alt="">
                    </el-carousel-item>
                    <el-carousel-item>
                        <img :src="'/image/lettering.jpg'" width="100%" height="100%" alt="">
                    </el-carousel-item>
                    <el-carousel-item>
                        <img :src="'/image/roses.jpg'" width="100%" height="100%" alt="">
                    </el-carousel-item>
                </el-carousel>
            </div>
            <div class="h-login-form">
                <div class="h-login-head">用户登录</div>
                <el-form :model="queryForm" label-position="left" :rules="formRules" ref="formRef" label-width="52px"
                    hide-required-asterisk size="default">
                    <el-form-item label="账号" prop="usercode">
                        <el-input v-model="queryForm.usercode" placeholder="请输入..." clearable />
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="queryForm.password" type="password" placeholder="请输入..." clearable />
                    </el-form-item>
                </el-form>
                <div>
                    <el-button type="primary" size="default" style="width: 270px;" plain @click="login"
                        :loading="loginBtnLoading">登录</el-button>
                </div>
                <div class="h-login-foot">
                    <div class="h-login-foot-left">
                        <small class="h-tip"><el-checkbox v-model="queryForm.isExempt" size="small" /> 30天免登陆</small>
                    </div>
                    <div class="h-login-foot-right">
                        <small class="h-tip">没有账号？去<a>注册</a></small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<style lang="scss" scoped>
.h-main {
    background-color: rgb(254, 245, 231);
    width: 100%;
    height: 100vh;
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

.h-login {
    height: calc(100% - 43px);
    display: flex;
    justify-content: space-evenly;
    align-items: center;
}

.h-top {
    height: 42px;
    border-bottom: 1px solid gainsboro;
    background-color: rgb(254, 245, 231);
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

    .right {
        font-size: small;
        margin-right: 20px;
    }
}

.h-login-brief {
    width: calc(80% - 350px);
}

.h-login-form {
    position: relative;
    top: -7%;
    overflow: hidden;
    border-radius: 2%;
    border: 1px solid rgb(248, 236, 220);
    background-color: rgb(255, 253, 250);
    font-weight: bold;
    display: flex;
    /* 水平居中 */
    justify-content: space-between;
    flex-direction: column;
    /* 垂直居中 */
    align-items: center;
    width: 350px;
    height: 350px;
}

.h-login-head {
    background-color: black;
    color: white;
    width: 100%;
    align-content: center;
    text-align: center;
    height: 40px;
}

.h-login-foot {
    width: 100%;
}

.h-login-foot-left {
    float: left;
    margin-left: 30px;
    margin-bottom: 10px;
}

.h-login-foot-right {
    float: right;
    margin-right: 30px;
    margin-bottom: 10px;
}

.el-input {
    --el-input-width: 220px;
}

.el-checkbox {
    position: relative;
    top: 1px;
}

a {
    color: rgb(254, 136, 136);
}
</style>