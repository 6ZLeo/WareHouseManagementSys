<template>
  <div class="notification-container">
    <h1 class="title">通知中心</h1>
    <el-table :data="notifications" style="width: 100vw; border: 1px solid #ebeef5;" stripe>
      <el-table-column prop="message" label="通知内容" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="removeNotification(scope.$index)">知道啦</el-button>
          <el-button type="text" size="small" @click="goToInventoryPage">去入库</el-button>
        </template>
      </el-table-column>
    </el-table>
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
    this.checkInventory();
  },
  methods: {
    async checkInventory() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = { headers: { 'Authorization': token } };
        const response = await axios.get('/products/all', config);
        const inventory = response.data;

        const leastStock = 1700;
        inventory.forEach(product => {
          if (product.stockQuantity < leastStock) {
            const notification = {
              message: `部件${product.productName}库存低于阈值${leastStock}`,
              productId: product.productID
            };
            this.notifications.push(notification);
          }
        });
      } catch (error) {
        console.error('Error checking inventory:', error);
      }
    },
    removeNotification(index) {
      this.notifications.splice(index, 1);
    },
    goToInventoryPage() {
      // 跳转到 Inventory.vue 页面，或者你需要跳转到的目标页面
      this.$router.push('/dashboard/inventory');
    }
  }
};
</script>

<style scoped>
.notification-container {
  margin:auto;
  margin-top: 5%;
  margin-left: 12%;
  width: 65vw;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
}

.title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  margin-top: 2%;
}

.el-table {
  margin-top: 25px;
  background-color: #fff;
  border-radius: 10px;
}

.dialog-footer {
  text-align: right;
}
</style>
