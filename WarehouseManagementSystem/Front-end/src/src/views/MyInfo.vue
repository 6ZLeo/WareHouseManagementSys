<template>
  <div class="setting-container">
    <h1 class="setting-title">个人信息</h1>
    <br>
    <div class="user-info">
      <div class="user-avatar">
        <img :src="userAvatar" alt="Avatar">
      </div>
      <div class="user-details">
        <div class="user-detail">
          <label>用户名：</label>
          <span>{{ userInfo.username }}</span>
        </div>
        <div class="user-detail">
          <label>角色：</label>
          <span>{{ userInfo.role }}</span>
        </div>
      </div>
    </div>
    <br>
    <div class="password-form">
      <h1 class="setting-title">修改密码</h1>
      <label>当前密码：</label>
      <el-input v-model="currentPassword" type="password" placeholder="请输入当前密码"></el-input>
      <label>新密码：</label>
      <el-input v-model="newPassword" type="password" placeholder="请输入新密码"></el-input>
      <label>确认新密码：</label>
      <el-input v-model="confirmPassword" type="password" placeholder="请确认新密码"></el-input>
      <div class="button-container">
        <el-button type="primary" @click="updatePassword" :loading="loading">{{ loading ? '保存中...' : '保存' }}</el-button>
        <span class="setting-error">{{ error }}</span>
      </div>
    </div>
    <div class="logout-button">
      <el-button type="danger" @click="logout">退出登录</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Setting',
  data() {
    return {
      userInfo: {
        username: '',
        role: ''
      },
      userAvatar: '', // 用户头像 URL
      currentPassword: '', // 当前密码
      newPassword: '', // 新密码
      confirmPassword: '', // 确认新密码
      loading: false, // 加载状态
      error: '' // 错误信息
    };
  },
  mounted() {
    this.fetchUserInfo();
    this.fetchUserAvatar();
  },
  methods: {
    async fetchUserInfo() {
      try {
        // 模拟从 SessionStorage 中获取用户名和角色
        const username = sessionStorage.getItem("login_username");
        const role = sessionStorage.getItem("login_role");
        this.userInfo = { username, role };
      } catch (error) {
        console.error('Error fetching user info:', error);
      }
    },
    async fetchUserAvatar() {
      try {
        // 模拟从 SessionStorage 中获取用户头像 URL
        const userAvatar = sessionStorage.getItem("user_avatar");
        this.userAvatar = userAvatar ? userAvatar : ''; // 如果用户头像 URL 存在则赋值，否则为空字符串
      } catch (error) {
        console.error('Error fetching user avatar:', error);
      }
    },
    async updatePassword() {
      try {
        // 检查新密码和确认密码是否一致
        if (this.newPassword !== this.confirmPassword) {
          this.error = '新密码和确认密码不匹配';
          return;
        }
        this.loading = true;
        // 发送更新密码请求
        const token = sessionStorage.getItem("access_token");
        const config = {
          headers: {
            'Authorization' : token,
          }
        };
        const response = await axios.post('/user/update-password', {
          currentPassword: this.currentPassword,
          newPassword: this.newPassword
        }, config);
        if (response.data.code === 200) {
          this.error = '';
          this.$message.success('密码更新成功');
          this.currentPassword = '';
          this.newPassword = '';
          this.confirmPassword = '';
        } else {
          this.error = response.data.message;
        }
      } catch (error) {
        console.error('Error updating password:', error);
        this.error = '更新密码失败';
      } finally {
        this.loading = false;
      }
    },
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
.setting-container {
  width: 65vw;
  margin-left: 12%;
  margin-top: 3%;
  padding: 50px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.setting-title {
  font-size: 32px;
  margin-bottom: 30px;
  text-align: center;
  color: #333;
}

.user-info {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 30px;
}

.user-avatar img {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 30px;
  border: 2px solid #eee;
}

.user-details {
  text-align: left;
}

.user-detail {
  font-size: 18px;
  margin-bottom: 10px;
}

.password-form {
  text-align: left;
  max-width: 600px;
  margin: auto;
}

.password-form label {
  display: block;
  font-size: 16px;
  margin-bottom: 10px;
  color: #555;
}

.el-input {
  width: 100%;
  margin-bottom: 20px;
}

.button-container {
  text-align: center;
  margin-top: 20px;
}

.setting-error {
  color: red;
  display: block;
  margin-top: 10px;
}

.logout-button {
  text-align: center;
  margin-top: 30px;
}
</style>
