import { ApiGetDictDtlList, DictDtl, ApiAddDictDtl, ApiDelDictDtl, ApiUpdDictDtl } from "@/utils/api/sys-dict-api";
import { FormRules } from 'element-plus';


export const dictType = ref("");
export const dictDtlList = ref([])
export const dictDtlForm = ref(new DictDtl());
export const isDictDtlEdit = ref(false);
export const dictDtlWinShow = ref(false)
export const dictDtlFormRef = ref();
export const dictDtlFormRules = ref<FormRules<DictDtl>>({
    dictCode: [
        { required: true, message: "字典代码不能为空！", trigger: 'blur' },
        { max: 30, message: "长度不能超过30！", trigger: 'blur' }
    ],
    dictValue: [
        { required: true, message: "字典描述不能为空！", trigger: 'blur' },
        { max: 30, message: "长度不能超过30！", trigger: 'blur' }
    ],
    status: [
        { required: true, message: "状态不能为空！", trigger: 'blur' },
    ]

});
// 显示窗口
export function showAddDictDtlWin(_dict: DictDtl | undefined | null) {
    isDictDtlEdit.value = false;
    dictDtlFormRef.value?.resetFields();
    dictDtlForm.value = { status: "1" };
    dictDtlWinShow.value = true
}


// 新增、修改字典
export function addDictDtl() {
    dictDtlFormRef.value.validate((valid: boolean) => {
        if (valid) {
            if (isDictDtlEdit.value) {
                ApiUpdDictDtl(dictDtlForm.value).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    } else {
                        ElMessage.error(res.msg)
                    }
                })
            } else {
                ApiAddDictDtl(Object.assign(dictDtlForm.value, { dictType: dictType })).then((res: any) => {
                    if (res.code == "200") {
                        handlerQuerySeccess(res.msg);
                    } else {
                        ElMessage.error(res.msg)
                    }
                })
            }
        }
    })
}
export function showUpdDictDtlWin(dictDtl: DictDtl) {
    isDictDtlEdit.value = true;
    dictDtlFormRef.value?.clearValidate()
    Object.assign(dictDtlForm.value, dictDtl);
    dictDtlWinShow.value = true
}
export function delDictDtl(dictDtl: DictDtl) {
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
            }
        }
    ).then(() => {
        ApiDelDictDtl(dictDtl.dictId + '').then((res: any) => {
            if (res.code == "200") {
                handlerQuerySeccess(res.msg);
            }
            myInstance.confirmButtonLoading = false
        })
    }).catch(() => {
    })
}
export function reloadDictDtlData() {
    ApiGetDictDtlList(dictType.value).then(res => {
        dictDtlList.value = res.list;
    });
}
``
export function handlerQuerySeccess(msg: string) {
    dictDtlWinShow.value = false
    reloadDictDtlData()
    ElMessage({
        message: msg,
        type: 'success',
    })
}
