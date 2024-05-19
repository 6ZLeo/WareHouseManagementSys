<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-header">
        <img src="@/assets/logo.png" alt="Logo" class="login-logo">
        <h2 class="login-title">仓库管理系统</h2>
      </div>
      <el-form :model="loginForm" class="login-form">
        <el-form-item>
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" class="login-button">登录</el-button>
        </el-form-item>
        <div class="login-links">
          <el-link @click="showPermissionsLink = !showPermissionsLink">
            系统介绍
          </el-link>
          <el-link @click="showDevelopersInfo">开发者信息</el-link>
        </div>
        <el-form-item v-if="showPermissionsLink">
          <el-link @click="showSystemInfo">点击查看系统介绍</el-link>
        </el-form-item>
        <router-link v-if="!showPermissionsLink" to="/register" class="register-link">
          还没有账号？去注册！
        </router-link>
      </el-form>
    </el-card>

    <el-dialog
      title="开发者信息"
      :visible.sync="developersDialogVisible"
      width="30%"
    >
      <el-table :data="developers" style="width: 100%">
        <el-table-column prop="name" label="开发者"></el-table-column>
        <el-table-column
          label="链接"
          width="180"
          scopedSlots="{ scope }"
        >
          <template v-slot:scope>
            <el-link :href="scope.row.link" target="_blank">{{ scope.row.link }}</el-link>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="developersDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/axios/axios';

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      showPermissionsLink: false,
      developersDialogVisible: false,
      developers: [
        { id: 1, name: 'ZLeo', link: 'https://6zleo.github.io/zleo.github.io/' },
        { id: 2, name: '我是开发者', link: 'https://developer2.com' },
        { id: 3, name: '我是开发者', link: 'https://developer3.com' },
        { id: 4, name: '我是开发者', link: 'https://developer4.com' },
        { id: 5, name: '我是开发者', link: 'https://developer5.com' }
      ]
    };
  },
  methods: {
    showDevelopersInfo() {
      this.developersDialogVisible = true;
    },
    showSystemInfo() {
      // 显示系统介绍模态框的逻辑...
    },
    async login() {
      try {
        const response = await axios.post('/user/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        });
        if (response.data.code === 200) {
          sessionStorage.setItem("login_username", this.loginForm.username);
          sessionStorage.setItem("login_role", response.data.data.role);
          sessionStorage.setItem("access_token", response.data.data.token);
          sessionStorage.setItem("access_role", response.data.data.role);
          this.$router.push('/dashboard/myinfo');
          axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.data.token}`;
        } else {
          console.error('Login failed:', response.data.message);
        }
      } catch (error) {
        console.error('An error occurred while logging in:', error);
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url('@/assets/background.png') no-repeat center center;
  background-size: cover;
}

.login-card {
  width: 400px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background-color: rgba(255, 255, 255, 0.9);
  /* border-radius: 10px; */
  border: none;
}

.login-header {
  text-align: center;
  margin-bottom: 20px;
}

.login-logo {
  width: 100px;
  margin-bottom: 10px;
}

.login-title {
  font-size: 24px;
  color: #333;
}

.login-form {
  display: flex;
  flex-direction: column;
}

.login-button {
  width: 100%;
}

.login-links {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.register-link {
  display: block;
  text-align: center;
  margin-top: 10px;
  color: #409EFF;
  text-decoration: none;
}

.register-link:hover {
  color: #66b1ff;
}

.dialog-footer {
  text-align: right;
}
</style>
