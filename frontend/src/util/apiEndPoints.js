export const BASE_URL = "https://money-manager-w098.onrender.com/api/v1.0";
const CLOUDINARY_CLOUD_NAME = "deoaf8qmx";

export const API_ENDPOINTS = {
  LOGIN: "/login",
  REGISTER: "/register",
  GET_USER_INFO: "/profile",

  //Category
  GET_ALL_CATEGORIES: "/categories",
  ADD_CATEGORY: "/categories",
  UPDATE_CATEGORY: (categoryId) => `categories/${categoryId}`,
  CATEGORY_BY_TYPE: (type) => `/categories/${type}`,

  //Income
  GET_ALL_INCOMES: "/incomes/all",
  ADD_INCOME: "/incomes",
  DELETE_INCOME: (incomeId) => `/incomes/${incomeId}`,
  INCOME_EXCEL_DOWNLOAD: "/incomes/excel/download",
  INCOME_EMAIL_SEND: "/incomes/excel/email",

  //Expense
  GET_ALL_EXPENSE: "/expenses/all",
  ADD_EXPENSE: "/expenses",
  DELETE_EXPENSE: (expenseId) => `/expenses/${expenseId}`,
  EXPENSE_EXCEL_DOWNLOAD: "/expenses/excel/download",
  EXPENSE_EMAIL_SEND: "/expenses/excel/email",

  //Filter
  APPLY_FILTERS: "/filter",

  //Dashboard
  DASHBOARD_DATA: "/dashboard",
  UPLOAD_IMAGE: `https://api.cloudinary.com/v1_1/${CLOUDINARY_CLOUD_NAME}/image/upload`,
};
