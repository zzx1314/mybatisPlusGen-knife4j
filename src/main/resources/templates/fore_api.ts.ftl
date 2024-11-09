import { http } from "@/utils/http";

type Result = {
  code: number;
  msg: string;
  data?: Array<any>;
};

type ResultPage = {
  code: number;
  msg: string;
  data?: {
    records: Array<any>;
    total: number;
  };
};

const ${entity?uncap_first}Urls = {
  page: `/api/${table.name?split("_")[0]}/${table.entityPath}/page`,
  save: "/api/${table.name?split("_")[0]}/${table.entityPath}/save",
  delete: `/api/${table.name?split("_")[0]}/${table.entityPath}/`,
  update: "/api/${table.name?split("_")[0]}/${table.entityPath}/update"
};

// ${table.comment!}分页
export const ${entity?uncap_first}Page = (query?: object) => {
  return http.axiosGetRequest<ResultPage>(${entity?uncap_first}Urls.page, query);
};
// ${table.comment!}保存
export const ${entity?uncap_first}Save = (param?: object) => {
  return http.axiosPostRequest<Result>(${entity?uncap_first}Urls.save, param);
};
// ${table.comment!}修改
export const ${entity?uncap_first}Update = (param?: object) => {
  return http.axiosPut<Result>(${entity?uncap_first}Urls.update, param);
};
// ${table.comment!}删除
export const ${entity?uncap_first}Delete = (param?: object) => {
   return http.axiosDelete<Result>(${entity?uncap_first}Urls.delete + param);
};