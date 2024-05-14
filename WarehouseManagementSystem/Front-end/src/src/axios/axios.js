import axios from 'axios';

// 创建一个 axios 实例
const instance = axios.create({
  baseURL: 'http://localhost:8081/api', // 设置后端服务的接口地址
  timeout: 10000, // 设置请求超时时间
});

export default instance; // 导出实例
