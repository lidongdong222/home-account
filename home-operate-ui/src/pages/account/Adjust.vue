<script setup lang="ts">
import { ArrowDown } from '@element-plus/icons-vue'
import {
  ApiExportUrl, Account, Subject, ApiGetSubjectList, ApiAddAccountConfirm, ApiGetAccountList, ApiAddAccount, ApiDelAccount,
  ApiUpdAccount, ApiImportWxBill, ApiImportWxBillData, ApiAddWxMatchRule, ApiGetWxMatchRule, ApiMatchSubjectByRule
} from "@/utils/api/account-api";
import { ApiGetDictByType } from "@/utils/api/sys-dict-api";
import { RepCloumn, ApigetRepColumns } from "@/utils/api/report-api";
import { ApiResource } from "@/utils/api/common/resource-api";
import Base from '@/utils/base';
import Dayjs from 'dayjs';
import { FormRules } from 'element-plus';
import { useRoute } from 'vue-router';
import MathUtil from '@/utils/mathUtil';
import StrUtil from '@/utils/strUtil';
import { v4 } from 'uuid';

const route = useRoute();
const columnList = ref<RepCloumn[]>([]);
const queryForm = ref<any>({
  pageNum: Base.defalutPageNum,
  pageSize: Base.defalutPageSize
});
const mainTableRef = ref();
const subjectList = ref<Subject[]>([]);
const accountListLoading = ref(false);
const accountList = ref([]);
const accountSum = ref<any>({ amount: 0 });
const accountTotal = ref(0);
const accountForm = ref<Account>({});
const isEdit = ref(false);
const accountWinShow = ref(false)
const accountFormRef = ref();
const dictPayType = ref<Array<any>>([]);
let cacheExportParams: any = null;
let repId = 1;

const accountFormRules: FormRules<Account> = ({
  subId: [
    { required: true, message: "科目代码不能为空！", trigger: 'change' }
  ],
  amount: [
    { validator: checkAmount, trigger: 'change' }
  ],
  accDate: [
    { required: true, message: "日期不能为空！", trigger: 'change' },
    { type: 'date', message: '日期格式不正确！' }
  ],
  digest: [
    { max: 100, message: "摘要长度不能超过100！", trigger: 'change' }
  ],
  paymentType: [
    { required: true, message: "结算方式不能为空！", trigger: 'change' }
  ]

});

function checkAmount(_rule: any, value: any, callback: any) {
  if (!(typeof value === 'number') || value <= 0) {
    callback(new Error('请输入正确的金额!'))
  } else {
    callback()
  }
}
onBeforeMount(() => {
  loadSubjects();
})
onMounted(() => {
  Object.assign(queryForm.value, route.query)
  reloadAccountData();
  loadColumns();
  loadDict();
  loadWxMatchRule();
})
watch([() => queryForm.value.pageNum, () => queryForm.value.pageSize], () => {
  reloadAccountData();
}, { immediate: false });
watch([() => route.query], () => {
  Object.assign(queryForm.value, route.query);
  reloadAccountData();
}, { immediate: false });
// 显示窗口
function showAddWin(row: any) {
  isEdit.value = row ? true : false;
  if (isEdit.value) {
    Object.assign(accountForm.value, row)
  } else {
    accountFormRef.value?.resetFields();
    accountForm.value = { paymentType: '4' };
  }
  accountWinShow.value = true
}
var addAccountLoading = ref(false);
// 新增、修改
function addAccount() {
  accountFormRef.value.validate((valid: boolean) => {
    if (valid) {
      addAccountLoading.value = true;
      if (isEdit.value) {
        ApiUpdAccount(accountForm.value).then((res: any) => {
          if (res.code == "200") {
            handlerQuerySeccess(res.msg);
          }
          addAccountLoading.value = false;
        })
      } else {
        ApiAddAccountConfirm(accountForm.value).then((res) => {
          if (res.data) {
            ElMessageBox.confirm(
              res.data,
              '操作确认',
              {
                type: 'info',
                showClose: false,
                buttonSize: 'small',
                cancelButtonText: '取消',
                confirmButtonText: '确认',
                beforeClose: (action, _instance, done) => {
                  if (action === 'confirm') {
                    done();
                  } else {
                    done();
                  }
                  addAccountLoading.value = false;
                },
              }
            ).then(() => {
              ApiAddAccount(accountForm.value).then((res: any) => {
                if (res.code == "200") {
                  handlerQuerySeccess(res.msg);
                }
                addAccountLoading.value = false;
              })
            })
          } else {
            ApiAddAccount(accountForm.value).then((res: any) => {
              if (res.code == "200") {
                handlerQuerySeccess(res.msg);
              }
              addAccountLoading.value = false;
            })
          }
        });

      }
    }
  })
}
function delAccount(account: Account) {
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
    ApiDelAccount(account.accId + "").then((res: any) => {
      if (res.code == "200") {
        handlerQuerySeccess(res.msg);
      }
    })
  })
    .catch(() => {
    })
}
function reloadAccountData() {
  accountListLoading.value = true;
  ApiGetAccountList(queryForm.value).then((res: any) => {
    if (res.code == 200) {
      cacheExportParams = queryForm.value;
      accountList.value = res.list;
      accountTotal.value = res.total;
      accountSum.value = res.data.summary
    }
  });
  accountListLoading.value = false;
}
function loadSubjects() {
  ApiGetSubjectList({ pageSize: 10000, pageNum: 0 }).then((res: any) => {
    subjectList.value = res.list
  });
}
function loadColumns() {
  ApigetRepColumns({ repId: repId }).then(res => {
    columnList.value = res.list
  });
}
function loadDict() {
  ApiGetDictByType("PAY_TYPE").then(res => {
    dictPayType.value = res.list
  });
}
var loadWxMatchRule = function () {
  return new Promise((resolve, _reject) => {
    ApiGetWxMatchRule().then(res => {
      if (res.code == 200) {
        wxMatchRuleList.value = res.list.map((m: any) => {
          m.matchSubId += '';
          return m;
        })
      }
      resolve('');
    });
  })
}


function handlerQuerySeccess(msg: string) {
  accountWinShow.value = false
  reloadAccountData()
  ElMessage({
    message: msg,
    type: 'success',
  })
}

function accountFormSubCodeChange(subId: any) {
  for (let i = 0; i < subjectList.value?.length; i++) {
    if (subjectList.value[i].subId == subId) {
      accountForm.value.subName = subjectList.value[i].subName;
      accountForm.value.subType = subjectList.value[i].subType;
      return;
    }
  }
}
function accountFormAccDateChange(date: any) {
  accountForm.value.accPeriod = date ? Dayjs(date).format("YYYYMM") : '';
}
function reset() {
  queryForm.value = {
    pageNum: Base.defalutPageNum,
    pageSize: Base.defalutPageSize,
  };
}



import type { TableColumnCtx } from 'element-plus/es/components/table/src/table-column/defaults'

interface SummaryMethodProps<T = Account> {
  columns: TableColumnCtx<T>[]
  data: T[]
}
const getSummaries = (param: SummaryMethodProps) => {
  const { columns } = param
  const sums: string[] = []
  columns.forEach((column, index) => {
    if (index === 0) {
      sums[index] = '合计'
      return
    }
    if (["amount"].includes(column.property)) {
      sums[index] = accountSum.value.amount.toFixed(2);
    } else {
      sums[index] = '';
    }

  })

  return sums
}

//微信账单导入功能
import { Delete, RefreshLeft } from '@element-plus/icons-vue'
import { genFileId } from 'element-plus';
import type { UploadInstance, UploadProps, UploadRawFile } from 'element-plus';
import BASE from '@/utils/base';
const importTemplateLoading = ref(false);
const fileList = ref();
const upload = ref<UploadInstance>()
const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)
}
const wxBillWinShow = ref(false);
const wxBillList = ref<any>([]);
const wxBillListShow = computed(() => {
  let wxBillListShowList = [];
  wxBillListShowList = wxBillList.value.filter((b: any) => {
    let flag = !b.isDelete;
    if (wxImpTag.value.isDelete) flag = b.isDelete;
    if (wxImpTag.value.isIncome) flag = flag && b.subType == "收入";
    if (wxImpTag.value.isExpenses) flag = flag && b.subType == "支出";
    return flag;
  })
  const prop = cacheWxBillListSort.value.prop;
  wxBillListShowList = wxBillListShowList.sort(function (a: any, b: any) {
    let d = 0;
    if (!StrUtil.isEmpty(cacheWxBillListSort.value.order)) {
      if (a[prop] > b[prop]) d = cacheWxBillListSort.value.order == 'ascending' ? 1 : -1;
      if (a[prop] == b[prop]) d = 0;
      if (a[prop] < b[prop]) d = cacheWxBillListSort.value.order == 'ascending' ? -1 : 1;
    }
    return d;
  });
  return wxBillListShowList;
})
const cacheWxBillListSort: any = ref<any>({});
const wxBillListSort = function ({ column, prop, order }: { column: any; prop: string; order: string }) {
  cacheWxBillListSort.value = { column, prop, order };
}
function wxBillImport() {
  wxBillWinShow.value = true
}
function importTemplate() {
  importTemplateLoading.value = true;
  ApiImportWxBill(fileList.value[0].raw, {}).then(res => {
    if (res.code == 200) {
      upload.value!.clearFiles()
      wxBillList.value = res.list;
    }
    importTemplateLoading.value = false;
  });
}
function removeWxBill(row: any) {
  for (const key in wxBillList.value) {
    if (wxBillList.value[key].wbId == row.wbId) {
      wxBillList.value[key].isDelete = true;
      break;
    }
  };
}
function revertWxBill(row: any) {
  for (const key in wxBillList.value) {
    if (wxBillList.value[key].wbId == row.wbId) {
      wxBillList.value[key].isDelete = false;
      break;
    }
  };
}

var wxBillImportDataLoading = ref(false);
function wxBillImportData() {
  for (const i in wxBillListShow.value) {
    if (wxBillListShow.value[i].subId == null) {
      ElMessage.error("第" + (parseInt(i) + 1) + "行未输入科目信息！");
      return;
    }
  }
  wxBillImportDataLoading.value = true;
  ApiImportWxBillData({ wxBillList: wxBillListShow.value }).then(res => {
    if (res.code == 200) {
      ElMessage.success("导入成功！");
      wxBillWinShow.value = false
    }
    wxBillImportDataLoading.value = false;
  });
}

const resourceExportLoading = ref(false);
function exportData(exportType: string) {
  resourceExportLoading.value = true;
  Object.assign(cacheExportParams, { exportType: exportType, repId: repId })
  ApiResource(cacheExportParams, ApiExportUrl).then(() => resourceExportLoading.value = false);
}
const wxImpTag = ref({
  isDelete: false,
  isIncome: false,
  isExpenses: false
})
const wxImpStatIncome = computed(() => {
  let income = 0.00;
  wxBillListShow.value.filter((b: any) => b.subType == "收入").forEach((b: any) => {
    income = MathUtil.add(income, parseFloat(b.amount.replace('¥', '')));
  })
  return income.toFixed(2);
})
const wxImpStatExpenses = computed(() => {
  let expenses = 0.00;
  wxBillListShow.value.filter((b: any) => b.subType == "支出").forEach((b: any) => {
    expenses = MathUtil.add(expenses, parseFloat(b.amount.replace('¥', '')));
  })
  return expenses.toFixed(2);
})
const wxMatchRuleWin = ref(false);
const wxMatchRuleList = ref<any>([]);
const addMatchRule = function () {
  wxMatchRuleList.value.push({
    wbmsrId: v4()
  });
}
//使用我的匹配规则快速匹配科目
const matchSubjectByRule = function () {
  if (wxBillList.value.length < 1) {
    ElMessage.info('未导入账单！');
    return;
  }
  ApiMatchSubjectByRule({ wxBillList: wxBillList.value }).then(res => {
    if (res.code == 200) {
      for (const m in wxBillList.value) {
        for (const n in res.list) {
          if (wxBillList.value[m].wbId == res.list[n].wbId) {
            wxBillList.value[m].subId = res.list[n].subId
            break;
          }
        }
      }
      ElMessage.success('匹配完成！');
    }
  })
}
const openWxMatchRuleWin = async function () {
  await loadWxMatchRule();
  wxMatchRuleWin.value = true;
}

//保存微信账单科目匹配规则
const saveWxMatchRuleLoading = ref(false);
const saveWxMatchRule = function () {
  if (wxMatchRuleList.value.length == 0) {
    ElMessage.error('列表为空！');
    return;
  }
  for (const key in wxMatchRuleList.value) {
    let t = wxMatchRuleList.value[key];
    if (StrUtil.isEmpty(t.subType) || StrUtil.isEmpty(t.wxDataItem) || StrUtil.isEmpty(t.matchType) || StrUtil.isEmpty(t.matchContent) || StrUtil.isEmpty(t.matchSubId)) {
      ElMessage.error('必填项不能为空！')
      return;
    }
  }
  saveWxMatchRuleLoading.value = true
  ApiAddWxMatchRule({ wxMatchRuleList: wxMatchRuleList.value }).then((res) => {
    saveWxMatchRuleLoading.value = false;
    ElMessage.success(res.msg);
  });
}
const delWxMatchRule = function (row: any) {
  ElMessageBox.confirm(
    '（设置后删除生效！）请再次确认是否删除？',
    '操作确认',
    {
      type: 'warning',
      showClose: false,
      buttonSize: 'small',
      cancelButtonText: '取消',
      confirmButtonText: '确认',
      beforeClose: (action, _instance, done) => {
        if (action === 'confirm') {
          done();
        } else {
          done();
        }
      },
    }
  ).then(() => {
    for (const key in wxMatchRuleList.value) {
      if (wxMatchRuleList.value[key].wbmsrId == row.wbmsrId) {
        wxMatchRuleList.value.splice(key, 1);
        return;
      }
    }
  }).catch(() => {
  })

}

</script>


<template>
  <div class="h-standard">
    <div class="h-query">
      <el-row>
        <el-col :span="6">
          <div class="h-query-item">
            <label>所属期间</label>
            <el-date-picker v-model="queryForm.startPeriod" type="month" placeholder="开始期间" style="width: 100px;"
              value-format="YYYYMM" format="YYYYMM" />
            至
            <el-date-picker v-model="queryForm.endPeriod" type="month" placeholder="结束期间" style="width: 100px;" ,
              value-format="YYYYMM" format="YYYYMM" />
          </div>
          <div class="h-query-item">
            <label>业务发生日期</label>
            <el-date-picker v-model="queryForm.startAccDate" type="date" placeholder="开始日期" style="width: 100px;"
              value-format="YYYY-MM-DD" format="YYYY-MM-DD" />
            至
            <el-date-picker v-model="queryForm.endAccDate" type="date" placeholder="结束日期" style="width: 100px;" ,
              value-format="YYYY-MM-DD" format="YYYY-MM-DD" />
          </div>
        </el-col>
        <el-col :span="6">
          <div class="h-query-item">
            <label>科目分类</label>
            <el-select v-model="queryForm.subType" clearable>
              <el-option label="收入" value="1" />
              <el-option label="支出" value="2" />
            </el-select>
          </div>
          <div class="h-query-item">
            <label>结算方式</label>
            <el-select v-model="queryForm.paymentType" clearable>
              <template v-for="(item, _index) in dictPayType" :key="_index">
                <el-option :label="item.dictValue" :value="item.dictCode" />
              </template>
            </el-select>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="h-query-item">
            <label>科目代码</label>
            <el-input v-model="queryForm.subCode" placeholder="请输入..." clearable />
          </div>
          <div class="h-query-item">
            <label>科目名称</label>
            <el-input v-model="queryForm.subName" placeholder="请输入..." clearable />
          </div>

        </el-col>
        <el-col :span="6">
          <div class="h-query-item">
            <label>摘要说明</label>
            <el-input v-model="queryForm.digest" placeholder="请输入..." clearable />
          </div>
          <div class="h-query-item">
            <label>创建人</label>
            <el-input v-model="queryForm.createUser" placeholder="请输入..." clearable />
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <div class="h-query-item">
            <label>金额</label>
            <el-input-number v-model="queryForm.startAmount" placeholder="0.00" :min="0" :max="10000000000"
              style="width: 100px;" :precision="2" controls-position="right" />
            至
            <el-input-number v-model="queryForm.endAmount" placeholder="0.00" :min="0" :max="10000000000"
              style="width: 100px;" :precision="2" controls-position="right" />
          </div>
        </el-col>
        <el-col :span="12">
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
            <el-button type="primary" @click="reloadAccountData">查询</el-button>
            <el-button type="primary" @click="reset">重置</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="h-operate">
      <el-button type="primary" @click="showAddWin(null)">新增</el-button>
      <el-button type="primary" @click="wxBillImport">微信账单导入</el-button>
      <el-dropdown class="h-export-btn">
        <el-button type="primary">
          导出<el-icon class="el-icon--right"><arrow-down /></el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item :disabled="accountTotal > BASE.excelMaxSupportSize" @click="exportData('EXCEL')">
              EXCEL
            </el-dropdown-item>
            <el-dropdown-item @click="exportData('CSV')">
              CSV
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <el-table ref="mainTableRef" :data="accountList" border row-key="accId" stripe row-class-name="h-table-hover"
      v-loading="accountListLoading" show-summary :summary-method="getSummaries"
      :header-cell-style="{ 'text-align': 'center' }">
      <el-table-column fixed="right" align="center" width="100">
        <template #header>
          操作
        </template>
        <template #default="scope">
          <el-button link @click="showAddWin(scope.row)" type="primary">编辑</el-button>
          <el-button link @click="delAccount(scope.row)" type="primary">删除</el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" type="index" label="序号" width="45"
        :index="(index: number) => (queryForm.pageNum - 1) * queryForm.pageSize + index + 1" />
      <template v-for="item in columnList" :key="item.colId">
        <el-table-column :align="item.colDataType == 'number' ? 'right' : 'center'" :prop="item.colProp"
          :label="item.colName" :width="item.width ? item.width : ''" show-overflow-tooltip>
          <template v-if="item.colDataType == 'number' && item.isNumberFormat == '1'" #default="scope">
            {{ scope.row[item.colProp!].toFixed(2) }}
          </template>
        </el-table-column>
      </template>
    </el-table>
    <div class="h-page">
      <el-pagination :current-page="queryForm.pageNum" :page-size="queryForm.pageSize"
        :page-sizes="Base.defaultPageSizeList" :background="true" layout="->,prev, pager, next,total, sizes,"
        :total="accountTotal" @size-change="(size: number) => queryForm.pageSize = size"
        @current-change="(size: number) => queryForm.pageNum = size" />
    </div>
  </div>
  <el-dialog v-model="accountWinShow" :title="'账单' + (isEdit ? '修改' : '新增')" width="800" draggable
    :close-on-click-modal="false">
    <el-form :inline="true" :model="accountForm" label-position="left" :rules="accountFormRules" ref="accountFormRef"
      label-width="100px">
      <el-form-item label="所属期间" prop="accPeriod">
        <el-input v-model="accountForm.accPeriod" disabled placeholder="请输入所属期间" />
      </el-form-item>
      <el-form-item label="科目分类" prop="subType">
        <el-select v-model="accountForm.subType" disabled placeholder="请输如科目分类">
          <el-option label="收入" value="1" />
          <el-option label="支出" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="业务发生日期" prop="accDate">
        <el-date-picker v-model="accountForm.accDate" type="date" placeholder="请选择日期" , value-format="YYYY-MM-DD"
          :disabled-date="(date: any) => date > new Date()" @change="accountFormAccDateChange" />
      </el-form-item>

      <el-form-item label="科目代码" prop="subId">
        <el-select v-model="accountForm.subId" clearable filterable @change="accountFormSubCodeChange">
          <el-option v-for="item in subjectList" :key="item.subCode" :label="item.subCode + ' ' + item.subName"
            :value="item.subId + ''" />
        </el-select>
      </el-form-item>
      <el-form-item label="金额(元)" prop="amount">
        <el-input-number v-model="accountForm.amount" placeholder="0.00" :min="0" :max="100000" :precision="2"
          controls-position="right" />
      </el-form-item>
      <el-form-item label="科目名称" prop="subName">
        <el-input v-model="accountForm.subName" disabled />
      </el-form-item>
      <el-form-item label="结算方式" prop="paymentType">
        <el-select v-model="accountForm.paymentType" clearable>
          <template v-for="(item, _index) in dictPayType" :key="_index">
            <el-option v-show="item.status == 1" :label="item.dictValue" :value="item.dictCode" />
          </template>
        </el-select>
      </el-form-item>
      <el-form-item label="摘要" prop="digest">
        <el-input v-model="accountForm.digest" clearable />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button :loading="addAccountLoading" size="small" type="primary" @click="addAccount()">保存</el-button>
      </div>
    </template>
  </el-dialog>
  <el-dialog v-model="wxBillWinShow" title="微信账单导入" width="1200" height="800" draggable :close-on-click-modal="false">
    <div class="wx-imp-top">
      <el-upload ref="upload" v-model:file-list="fileList" class="import-upload" :auto-upload="false"
        :on-exceed="handleExceed" :limit="1">
        <template #trigger>
          <el-button type="primary">选择文件</el-button>
        </template>
        <el-button :loading="importTemplateLoading" v-show="fileList != null && fileList.length > 0" class="import-btn"
          @click="importTemplate" type="primary">解析文件</el-button>
      </el-upload>
      <div class="wx-imp-top-stat">
        <el-text type="primary" size="default">收入：{{ wxImpStatIncome }} 元</el-text>
        <el-text type="danger" size="default"> 支出：{{ wxImpStatExpenses }} 元</el-text>
      </div>
    </div>
    <el-table :data="wxBillListShow" border row-key="wxId" stripe :fit="true" height="50vh"
      row-class-name="h-table-hover" @sort-change="wxBillListSort" :header-cell-style="{ 'text-align': 'center' }">
      <el-table-column align="center" type="index" label="序号" width="50" />
      <el-table-column align="center" type="index" label="操作" width="50">
        <template #default="scope">
          <el-button v-show="!wxImpTag.isDelete" type="primary" link :icon="Delete" @click="removeWxBill(scope.row)" />
          <el-button v-show="wxImpTag.isDelete" type="primary" link :icon="RefreshLeft"
            @click="revertWxBill(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column align="center" prop="subId" width="150" show-overflow-tooltip>
        <template #header>
          <span class="require">*</span>科目信息
        </template>
        <template #default="scope">
          <el-select v-model="scope.row.subId" clearable filterable style="width: 100%;">
            <el-option v-for="item in subjectList" :key="item.subCode" :label="item.subCode + ' ' + item.subName"
              :value="item.subId + ''" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="digest" label="摘要" width="150" show-overflow-tooltip>
        <template #header>
          摘要
        </template>
        <template #default="scope">
          <el-tooltip class="box-item" effect="dark" :disabled="scope.row.digest == null" :content="scope.row.digest"
            placement="top">
            <el-input v-model="scope.row.digest" clearable style="width: 100%;" />
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="tranDate" label="交易时间" width="100" show-overflow-tooltip sortable />
      <el-table-column align="center" prop="tranType" label="交易类型" width="100" show-overflow-tooltip sortable />
      <el-table-column align="center" prop="merchant" label="交易对方" width="150" show-overflow-tooltip sortable />
      <el-table-column align="center" prop="goods" label="商品" show-overflow-tooltip sortable />
      <el-table-column align="center" prop="subType" label="收/支" width="80" sortable></el-table-column>
      <el-table-column align="right" prop="amount" label="金额" width="100" sortable></el-table-column>
      <el-table-column align="center" prop="payType" label="支付方式" width="150" show-overflow-tooltip sortable />
      <el-table-column align="center" prop="wxStatus" label="当前状态" width="100" show-overflow-tooltip sortable />
      <el-table-column align="center" prop="tranOrder" label="交易单号" width="100" show-overflow-tooltip />
      <el-table-column align="center" prop="merchantOrder" label="商户单号" width="100" show-overflow-tooltip />
      <el-table-column align="center" prop="remark" label="备注" width="100" show-overflow-tooltip />
    </el-table>
    <template #footer>
      <div class="wx-imp-bottom">
        <div>
          <el-check-tag :checked="wxImpTag.isDelete" @click="wxImpTag.isDelete = !(wxImpTag.isDelete)"
            type='danger'>已删除</el-check-tag>
          <el-check-tag :checked="wxImpTag.isIncome" @click="wxImpTag.isIncome = !(wxImpTag.isIncome)">收入</el-check-tag>
          <el-check-tag :checked="wxImpTag.isExpenses"
            @click="wxImpTag.isExpenses = !(wxImpTag.isExpenses)">支出</el-check-tag>
        </div>
        <div>
          <el-button type="primary" @click="openWxMatchRuleWin">我的科目匹配规则</el-button>
          <el-button type="primary" @click="matchSubjectByRule">使用【我的科目匹配规则】快速匹配科目</el-button>
          <el-button :loading="wxBillImportDataLoading" @click="wxBillImportData" type="primary">确认导入</el-button>
        </div>
      </div>
    </template>
  </el-dialog>
  <el-dialog v-model="wxMatchRuleWin" title="我的科目匹配规则" width="1200" height="800" draggable
    :close-on-click-modal="false">
    <div class="wx-sub-rule-tip">
      <ol>
        <li>科目匹配规则建议您在非常精确的匹配内容上使用。</li>
      </ol>
    </div>
    <div class="wx-sub-rule-top">
      <el-button @click="addMatchRule" type="primary">新增</el-button>
    </div>
    <el-table :data="wxMatchRuleList" border row-key="wxId" stripe :fit="true" height="50vh"
      row-class-name="h-table-hover" :header-cell-style="{ 'text-align': 'center' }">
      <el-table-column align="center" type="index" label="序号" width="50" />
      <el-table-column align="center" type="op" label="操作" width="50">
        <template #default="scope">
          <el-button type="primary" link :icon="Delete" @click="delWxMatchRule(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column align="center" prop="subType" sortable>
        <template #header>
          <span class="require">*</span>收/支
        </template>
        <template #default="scope">
          <el-select v-model="scope.row.subType" clearable filterable style="width: 100%;">
            <el-option value="收入" label="收入"></el-option>
            <el-option value="支出" label="支出"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="wxDataItem" label="微信账单数据项" sortable>
        <template #header>
          <span class="require">*</span>微信账单数据项
        </template>
        <template #default="scope">
          <el-select v-model="scope.row.wxDataItem" clearable filterable style="width: 100%;">
            <el-option value="merchant" label="交易对方"></el-option>
            <el-option value="goods" label="商品"></el-option>
            <el-option value="tranType" label="交易类型"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="matchType">
        <template #header>
          <span class="require">*</span>匹配类型
        </template>
        <template #default="scope">
          <el-select v-model="scope.row.matchType" clearable filterable style="width: 100%;">
            <el-option value="1" label="包含"></el-option>
            <el-option value="2" label="等于"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="matchContent" show-overflow-tooltip>
        <template #header>
          <span class="require">*</span>匹配内容
        </template>
        <template #default="scope">
          <el-input v-model="scope.row.matchContent" clearable style="width: 100%;" />
        </template>
      </el-table-column>
      <el-table-column align="center" prop="matchSubId" show-overflow-tooltip sortable>
        <template #header>
          <span class="require">*</span>匹配科目
        </template>
        <template #default="scope">
          <el-select v-model="scope.row.matchSubId" clearable filterable style="width: 100%;">
            <el-option v-for="item in subjectList" :key="item.subCode" :label="item.subCode + ' ' + item.subName"
              :value="item.subId + ''" />
          </el-select>
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <div>
        <div>
          <el-button :loading="saveWxMatchRuleLoading" @click="saveWxMatchRule" type="primary">设置</el-button>
        </div>
      </div>
    </template>
  </el-dialog>
</template>



<style scoped lang="scss">
.p-query {
  .el-input {
    --el-input-width: 220px;
  }

  .el-input-number {
    width: 100px;
  }
}

.el-input-number {
  width: 220px;
}

.el-select {
  --el-select-width: 220px;
}

.el-input {
  --el-input-width: 220px;
}

.dialog-footer {
  text-align: right;
}

.el-button+.el-button {
  margin-left: 6px;
}

.el-pagination {
  display: inline-block;
}

.import-btn {
  margin-left: 20px;
  position: relative;
  top: -1px;
}

.h-export-btn {
  margin-left: 6px;
}

.require {
  color: red;
}

.wx-imp-top {
  display: flex;
  justify-content: space-between;

  .wx-imp-top-stat {
    .el-text {
      font-weight: bolder;
      margin-left: 20px;
    }
  }
}

.wx-imp-bottom {
  display: flex;
  justify-content: space-between;

  .el-check-tag {
    margin-right: 6px;
    font-size: x-small;
  }
}

.wx-sub-rule-top {
  margin-bottom: 6px;
  text-align: right;
}

.wx-sub-rule-tip {
  ol {
    margin-left: 20px;
    font-style: italic;
    font-size: small;
    color: rgb(247, 63, 63);
  }
}
</style>