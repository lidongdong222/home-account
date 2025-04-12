import request from "@/utils/request";
export interface ReportParam {
  repId?: number,
  total?: number,
  requestUrl?: String,
  requestParam?: String
}
export interface ReportHisParam {
  menuId: String,
  pageSize: Number,
  pageNum: Number
}

export function ApiResourceHisList(repParam: ReportHisParam): Promise<any> {
  return request.post("/common/resource/getImportHisList", repParam);
}
/**
 * 导出报表模板
 */
export function ApiResourceReportTemplate(repParam: ReportParam) {
  return resourceExport(repParam, "/report/exportTemplate");
}
/**
 * 导出资源文件
 */
export function ApiResource(reqParam: any, resourceUrl: string) {
  return resourceExport(reqParam, resourceUrl);
}
function resourceExport(repParam: any, url: string) {
  return request.post(url, repParam, { responseType: 'arraybuffer' }).then((res: any) => {
    const fileData = res.data;
    const fileName = decodeURI(
      res.headers["content-disposition"].split(";")[1].split("=")[1]
    );
    const fileType = res.headers["content-type"]
    if(fileType.includes("application/json")){
      const decoder = new TextDecoder('UTF-8');
      const result = JSON.parse(decoder.decode(res.data));
      if(result.code=="500"){
        ElMessage.error(result.msg);
      }
    }else{
      const blob = new Blob([fileData], { type: fileType });
      const downloadUrl = window.URL.createObjectURL(blob);
      const downloadLink = document.createElement("a");
      downloadLink.href = downloadUrl;
      downloadLink.download = fileName;
  
      document.body.appendChild(downloadLink);
      downloadLink.click();
      document.body.removeChild(downloadLink);
      window.URL.revokeObjectURL(downloadUrl);
    }
  });
}
export function ApiResourceImport(file: any, repParam: any): Promise<any> {
  const formData = new FormData();
  formData.append("file", file);
  for (const key in repParam) {
    formData.append(key, repParam[key]);
  }
  return request({
    url: repParam['importApiUrl'],
    method: "post",
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
}

