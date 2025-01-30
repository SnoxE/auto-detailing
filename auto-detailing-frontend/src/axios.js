// import axios from 'axios'

// axios.defaults.baseURL = 'http://localhost:8080/'
// axios.interceptors.request.use(function (config) {
//   const token = localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token')) : ''
//   config.headers.Authorization = token ? `Bearer ${token}` : ''
//   return config
// })

import axios from "axios";

const DEFAULT_BASE_URL = "http://localhost:8080/";
const ALTERNATE_BASE_URL = "http://localhost:8081/";

const port8081endpoints = ["api/users/register", "api/users/email", "api/users/user", "api/token"];

axios.interceptors.request.use((config) => {
  if (port8081endpoints.some((endpoint) => config.url.includes(endpoint))) {
    config.baseURL = ALTERNATE_BASE_URL;
  } else {
    config.baseURL = DEFAULT_BASE_URL;
  }

  const token = localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token')) : ''
  config.headers.Authorization = token ? `Bearer ${token}` : ''

  return config;
}, (error) => {
  return Promise.reject(error);
});

export default axios;