<!-- Myinfo.vue -->
<template>
  <div class="scroll-container">
  <!-- 个人信息页面容器 -->
  <div class="my-info">
    <h2>个人信息</h2>
    <!-- 基本信息卡片 -->
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">基本信息</h5>
        <p class="card-text">姓名：{{ userInfo.username }}</p>
        <p class="card-text">角色：{{ userInfo.role }}</p>
        <!-- <p class="card-text">注册日期：{{ userInfo.registrationDate }}</p> -->
      </div>
    </div>
    <!-- 修改密码卡片 -->
    <div class="card mt-4">
      <div class="card-body">
        <h5 class="card-title">修改密码</h5>
        <!-- 修改密码表单 -->
        <form @submit.prevent="changePassword">
          <div class="form-group">
            <label for="currentPassword">当前密码</label>
            <input type="password" class="form-control" id="currentPassword" v-model="currentPassword">
          </div>
          <div class="form-group">
            <label for="newPassword">新密码</label>
            <input type="password" class="form-control" id="newPassword" v-model="newPassword">
          </div>
          <div class="form-group">
            <label for="confirmPassword">确认新密码</label>
            <input type="password" class="form-control" id="confirmPassword" v-model="confirmPassword">
          </div>
          <!-- 确认修改按钮 -->
          <button type="submit" class="btn btn-primary" @click="loading">确认修改</button>
        </form>
        <!-- 错误提示 -->
        <p v-if="error" class="text-danger mt-2">{{ error }}</p>
      </div>
    </div>
    <!-- 退出登录按钮 -->
    <div class="mt-4">
      <button class="btn btn-danger" @click="logout">退出登录</button>
    </div>
  </div>
</div>
</template>

<script>
// import axios from 'axios';
import axios from '@/axios/axios';

export default {
  name: 'MyInfo',
  data() {
    return {
      userInfo: {}, // 用户信息
      currentPassword: '', // 当前密码
      newPassword: '', // 新密码
      confirmPassword: '', // 确认新密码
      loading: false, // 加载状态
      error: '' // 错误信息
    };
  },
  mounted() {
    this.fetchUserInfo();
  },
  methods: {
    // 获取用户信息
    async fetchUserInfo() {
      try {
        // 从SessionStorage中获取用户名和角色
        const username = sessionStorage.getItem("login_username");
        const role = sessionStorage.getItem("login_role");

        // 创建一个新的userInfo对象，将获取的数据赋值给它
        this.userInfo = {
          username: username,
          role: role
        };
      } catch (error) {
        console.error('Error fetching user info:', error);
      }
    },

    // 退出登录
    async logout() {
      // 清除sessionStorage中的用户信息
      sessionStorage.removeItem("login_username");
      sessionStorage.removeItem("login_role");
      sessionStorage.removeItem("access_token");

      // 跳转到登录界面
      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>

.scroll-container {
  /* 固定高度并启用垂直滚动 */
  height: 100vh; /* 设置适当的高度 */
  overflow-y: auto; /* 启用垂直滚动 */
}

.my-info {
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.534); /* 背景颜色 */
  border-radius: 10px; /* 圆角矩形 */

}

.card {
  padding: 30px;
  background-color: rgba(255, 255, 255, 0.185); /* 卡片背景颜色 */
  border-radius: 10px; /* 圆角矩形 */
  margin-bottom: 20px;
}

.card-body {
  padding: 20px;
}

.btn {
  border-radius: 20px; /* 圆角矩形按钮 */
  padding: 10px 20px;
  transition: all 0.3s ease;
}

.btn-primary {
  background-color: #007bff; /* 主要按钮背景色 */
  color: white;
}

.btn-primary:hover {
  background-color: #0056b3; /* 鼠标悬停时按钮背景色 */
}

.btn-danger {
  background-color: #dc3545; /* 危险按钮背景色 */
  color: white;
}

.btn-danger:hover {
  background-color: #bd2130; /* 鼠标悬停时按钮背景色 */
}

.text-danger {
  color: #dc3545; /* 错误文本颜色 */
}
</style>

  