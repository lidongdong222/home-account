<script setup lang="ts">
import { Menu, ApiGetMenuList, ApiAddMenu, ApiDelMenu, ApiUpdMenu, ApiSortMenu } from "@/utils/api/sys-menu-api";
import { useMenuStore } from '@/store/sysStore'
import { FormRules } from 'element-plus';
const queryForm = ref<any>({
    mhcx: ""
});
const isMhcx = ref(false);
const sysStore = useMenuStore();
const menuList = ref<Menu[]>(new Array());
const menuForm = ref<Menu>({});
const isEdit = ref(false);
const menuWinShow = ref(false)
const menuFormRef = ref();
const menuFormRules = ref<FormRules<Menu>>({
    menuCode: [
        { required: true, message: "菜单编码不能为空！", trigger: 'blur' },
        { max: 30, message: "长度不能超过30！", trigger: 'blur' }
    ],
    menuName: [
        { required: true, message: "菜单名称不能为空！", trigger: 'blur' },
        { max: 30, message: "长度不能超过30！", trigger: 'blur' }
    ],
    router: [
        { max: 50, message: "长度不能超过50！", trigger: 'blur' }
    ],
    icon: [
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
    reloadData();
})
// 显示窗口
function showAddWin(menu: Menu | undefined | null) {
    isEdit.value = false;
    menuFormRef.value?.resetFields();
    menuForm.value = {};
    if (!menu) {
        menuForm.value.parentId = 0;
    } else {
        menuForm.value.parentId = menu.menuId;
    }
    menuWinShow.value = true
}
// 新增、修改菜单
const addMenuLoading = ref(false);
function addMenu() {
    addMenuLoading.value = true;
    menuFormRef.value.validate((valid: boolean) => {
        if (valid) {
            if (isEdit.value) {
                ApiUpdMenu(menuForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess();
                    }
                    addMenuLoading.value = false;
                })
            } else {
                ApiAddMenu(menuForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess();
                    }
                    addMenuLoading.value = false;
                })
            }
        }
    })
}
function showUpdWin(menu: Menu) {
    isEdit.value = true;
    Object.assign(menuForm.value, menu)
    menuWinShow.value = true
    menuFormRef.value?.clearValidate()
}
function delMenu(menu: Menu) {
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
            ApiDelMenu(menu.menuId + "").then((res: any) => {
                if (res.code == "200") {
                    handlerQuerySeccess();
                }
                myInstance.confirmButtonLoading = false;
            })
        })
        .catch(() => {
        })
}
function reloadData() {
    ApiGetMenuList(queryForm.value).then(res => {
        isMhcx.value = queryForm.value.mhcx ? true : false;
        menuList.value = res.list;
    });
}
function handlerQuerySeccess() {
    menuWinShow.value = false
    sysStore.reloadMenuList();
    reloadData()
}

function isUpDown(menu: any, isUp: number) {
    let sameLevelMenus = new Array();
    if (menu.parentId == 0) {
        sameLevelMenus = menuList.value;
    } else {
        for (let t in menuList.value) {
            sameLevelMenus = getSameLevelMenu(menuList.value[t], menu);
            if (sameLevelMenus.length > 0) break;

        };
    }
    for (let m in sameLevelMenus) {
        if (isUp == 1 && sameLevelMenus[m].sort < menu.sort) return false;
        if (isUp != 1 && sameLevelMenus[m].sort > menu.sort) return false;
    }
    return true;
}
/**获取树状结构同级别数据 */
function getSameLevelMenu(m: any, menu: any) {
    if (m.menuId == menu.parentId) {
        return m.children;
    }
    if (m.children) {
        for (let t in m.children) {
            let arr: any = getSameLevelMenu(m.children[t], menu);
            if (arr.length > 0) return arr;
        }
    }
    return new Array();
}
const sortMenuLoading = ref(false);
function sortMenu(menu: Menu, sortType: number) {
    sortMenuLoading.value = true;
    ApiSortMenu({ menuId: menu.menuId, sortType: sortType }).then(res => {
        if (res.code == 200) {
            reloadData();
            ElMessage.success(res.msg);
            sortMenuLoading.value = false;
        }
    })
}
function reset() {
    queryForm.value = {
    };
}
</script>

<template>
    <div class="h-standard">
        <!-- <div class="h-query">
            <div class="h-query-condition">
                <div class="h-query-item">
                    <label>菜单信息：</label>
                    <el-input v-model="queryForm.mhcx" placeholder="请输入..." :prefix-icon="Search" clearable />
                </div>
            </div>
            <div class="h-query-button">
                <div class="h-query-button-group">
                    <el-button type="primary" @click="reloadData">查询</el-button>
                    <el-button type="primary" @click="reset">重置</el-button>
                </div>
            </div>
        </div> -->
        <div class="h-operate">
            <el-button type="primary" @click="reloadData">查询</el-button>
            <el-button type="primary" @click="showAddWin(null)">新增一级菜单</el-button>
        </div>
        <el-table :data="menuList" border row-key="menuId" stripe style="width: 100%" 
            :header-cell-style="{ 'text-align': 'center' }" row-class-name="h-table-hover"
            :default-expand-all="queryForm.mhcx ? true : false">
            <el-table-column  type="index" label="序号" width="50" />
            <el-table-column prop="menuName" label="菜单名称" />
            <el-table-column align="center" prop="router" label="菜单路由" />
            <el-table-column align="center" prop="icon" label="菜单图标" />
            <el-table-column align="center" prop="statusDesc" label="状态" />
            <el-table-column align="center" prop="remark" label="备注" />
            <el-table-column v-if="!isMhcx" align="center">
                <template #header>
                    操作
                </template>
                <template #default="scope">
                    <el-button link @click="showAddWin(scope.row)" type="primary">新增</el-button>
                    <el-button link @click="showUpdWin(scope.row)" type="primary">修改</el-button>
                    <el-button v-show="scope.row.children ? false : true" link @click="delMenu(scope.row)"
                        type="primary">删除</el-button>
                    <el-button :loading="sortMenuLoading" :disabled="isUpDown(scope.row, 1)" link
                        @click="sortMenu(scope.row, 1)" type="success">上移</el-button>
                    <el-button :loading="sortMenuLoading" :disabled="isUpDown(scope.row, 2)" link
                        @click="sortMenu(scope.row, 2)" type="success">下移</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <el-dialog v-model="menuWinShow" :title="'菜单' + (isEdit ? '修改' : '新增')" width="800" :close-on-click-modal="false" draggable>
        <el-form :inline="true" :model="menuForm" label-position="right" :rules="menuFormRules" ref="menuFormRef"
            label-width="90px">
            <el-form-item label="菜单编码：" prop="menuCode">
                <el-input v-model="menuForm.menuCode" clearable :disabled="isEdit" />
            </el-form-item>
            <el-form-item label="菜单名称：" prop="menuName">
                <el-input v-model="menuForm.menuName" clearable />
            </el-form-item>
            <el-form-item label="路由：" prop="router">
                <el-input v-model="menuForm.router" clearable />
            </el-form-item>
            <el-form-item label="图标：" prop="icon">
                <el-input v-model="menuForm.icon" clearable />
            </el-form-item>
            <el-form-item label="状态：" prop="status">
                <el-select v-model="menuForm.status">
                    <el-option label="启用" value="1" />
                    <el-option label="禁用" value="0" />
                </el-select>
            </el-form-item>
            <el-form-item label="备注：" prop="remark">
                <el-input v-model="menuForm.remark" clearable />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button :loading="addMenuLoading" size="small" type="primary" @click="addMenu()">保存</el-button>
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

.el-button+.el-button {
    margin-left: 6px;
}
</style>