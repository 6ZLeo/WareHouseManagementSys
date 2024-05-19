<template>
  <div class="register-container">
    <el-card class="register-card">
      <div class="register-header">
        <img src="@/assets/logo.png" alt="Logo" class="register-logo">
        <h2 class="register-title">注册新账号</h2>
      </div>
      <el-form :model="registeruser" class="register-form">
        <el-form-item>
          <el-input
            v-model="registeruser.username"
            placeholder="请输入用户名"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="registeruser.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="register" class="register-button">注册</el-button>
        </el-form-item>
        <router-link to="/login" class="login-link">已有账号？去登录！</router-link>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import axios from '@/axios/axios';

export default {
  name: 'Register',
  data() {
    return {
      registeruser: {
        username: '',
        password: '',
        role: 'user'
      },
      error: '',
      loading: false
    };
  },
  methods: {
    async register() {
      this.loading = true; // 启用加载状态，禁用提交按钮

      try {
        const userData = {
          username: this.registeruser.username,
          password: this.registeruser.password,
          role: 'user'
        };
        const response = await axios.post('/users', userData);

        if (response.status === 200) {
          // 注册成功，跳转到登录页面
          this.$message.success('注册成功！请登录。');
          this.$router.push('/login');
        } else {
          // 注册失败，处理错误情况
          this.error = '注册失败，请重试。';
        }
      } catch (error) {
        // 请求错误，处理网络错误或超时等
        this.error = '注册过程中出现错误。';
      } finally {
        this.loading = false; // 恢复加载状态为false，启用提交按钮
      }
    }
  }
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url('@/assets/background.png') no-repeat center center;
  background-size: cover;
}

.register-card {
  width: 400px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background-color: rgba(255, 255, 255, 0.9);
  border: none;
}

.register-header {
  text-align: center;
  margin-bottom: 20px;
}

.register-logo {
  width: 100px;
  margin-bottom: 10px;
}

.register-title {
  font-size: 24px;
  color: #333;
}

.register-form {
  display: flex;
  flex-direction: column;
}

.register-button {
  width: 100%;
}

.login-link {
  display: block;
  text-align: center;
  margin-top: 10px;
  color: #409EFF;
  text-decoration: none;
}

.login-link:hover {
  color: #66b1ff;
}
</style>
