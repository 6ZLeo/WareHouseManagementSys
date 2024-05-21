<template>
    <div class="notification-container">
      <h2>通知中心</h2>
      <!-- 使用表格来显示通知 -->
      <table class="notification-table">
        <thead>
          <tr>
            <th>通知内容</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <!-- 使用v-for循环渲染通知 -->
          <tr v-for="(notification, index) in notifications" :key="index">
            <td>{{ notification.message }}</td>
            <td>
              <button class="btn text-primary" @click="removeNotification(index)">知道啦</button>
              <button class="btn text-primary" @click="goToInventory">去入库</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import axios from '@/axios/axios';
  export default {
    name: 'Notification',
    data() {
      return {
        notifications: []
      };
    },
    created() {
      // 在组件创建时检查库存并生成通知
      this.checkInventory();
      this.showPermissionsLink = userRole === 'admin';
    },
    methods: {
      async checkInventory() {
        try {
          const token = sessionStorage.getItem("access_token");
          const config = { headers: { 'Authorization': token } };
          const response = await axios.get('/products/all', config);
          const inventory = response.data;
          
          // 检查库存，如果低于阈值则生成通知
          inventory.forEach(product => {
            const leaststock = 1700;
            if (product.stockQuantity < leaststock) {
              const notification = {
                message: `部件${product.productName}库存低于阈值${leaststock}`,
                productId: product.productID
              };
              this.notifications.push(notification);
            }
          });
          console.log(inventory);
          console.log(this.notifications);
        } catch (error) {
          console.error('Error checking inventory:', error);
        }
      },
      removeNotification(index) {
        this.notifications.splice(index, 1);
      },
      goToInventory() {
        
        this.$router.push('/inventory-management');
      }
    }
  };
  </script>
  
  <style scoped>
  .notification-container {
    margin-top: 20px;
  }
  .notification-table {
    width: 100%;
    border-collapse: collapse;
  }
  .notification-table th,
  .notification-table td {
    border: 1px solid #ccc;
    padding: 8px;
  }
  .notification-table th {
    background-color: #f2f2f2;
  }
  .notification-table tbody tr:nth-child(even) {
    background-color: #f2f2f2;
  }
  
  /* 将按钮样式应用于通知操作按钮 */
  .notification-table .btn {
    border: none; /* 去除按钮边框 */
    padding: 5px 10px; /* 调整按钮内边距 */
    transition: all 0.3s ease; /* 添加过渡效果 */
  }
  
  .notification-table .btn.text-primary {
    background-color: rgba(255, 255, 255, 0.534); /* 设置背景颜色 */
    color: #007bff; /* 设置文字颜色 */
  }
  
  .notification-table .btn.text-primary:hover {
    background-color: transparent; /* 鼠标悬停时去除背景颜色 */
    color: #0056b3; /* 设置悬停时文字颜色 */
  }
  </style>
  