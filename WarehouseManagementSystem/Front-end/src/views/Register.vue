
  

<!-- 第三次 

<template>
    <div class="container">
      <h1>Register</h1>
      <form @submit.prevent="register">
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>
      <router-link to="/login">Already have an account? Login here</router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Register',
    data() {
      return {
        name: '',
        email: '',
        password: ''
      };
    },
    methods: {
      register() {
        // Call register action from store
        // Here you'll need to dispatch an action to register the user
        // Replace this with actual registration functionality
        // Upon successful registration, you may want to automatically log the user in
        // For the sake of simplicity, I'm redirecting to the login page
        this.$router.push('/login');
      }
    }
  }
  </script>
  


  <script>
  export default {
    name: 'Register'
  }
  </script>
  -->

  
<!-- 第一版post -->

  <!-- <template>  
    <div class="container">  
      <h1>Register</h1>  
      <form @submit.prevent="register">  
        <div class="form-group">
    <label for="name">Name</label>
    <input type="text" class="form-control" id="name" v-model="name" placeholder="Enter name">
  </div>
  <div class="form-group">
    <label for="email">Email address</label>
    <input type="email" class="form-control" id="email" v-model="email" placeholder="Enter email">
  </div>
  <div class="form-group">
    <label for="password">Password</label>
    <input type="password" class="form-control" id="password" v-model="password" placeholder="Password">
  </div>
        <button type="submit" class="btn btn-primary" :disabled="loading">Submit</button>  
        <p v-if="error" class="error">{{ error }}</p>  
      </form>  
      <router-link to="/login">Already have an account? Login here</router-link>  
    </div>  
  </template>  
    
  <script>  
  import axios from 'axios';  
    
  export default {  
    name: 'Register',  
    data() {  
      return {  
        name: '',  
        email: '',  
        password: '',  
        loading: false,  
        error: ''  
      };  
    },  
    methods: {  
      async register() {  
        this.error = ''; // 清除之前的错误信息  
        this.loading = true; // 设置加载状态为true，禁用提交按钮  
        try {  
          const response = await axios.post('/api/register', { // 发送POST请求到注册API端点  
            name: this.name,  
            email: this.email,  
            password: this.password  
          });  
          if (response.status === 200) {  
            // 注册成功，处理成功逻辑，比如显示成功信息或者自动登录  
            this.$router.push('/login'); // 假设注册后跳转到登录页面  
            this.$message.success('Registration successful! Please log in.'); // 使用UI库显示成功信息（可选）  
          } else {  
            // 注册失败，处理错误情况  
            this.error = 'Registration failed, please try again.';  
          }  
        } catch (error) {  
          // 请求错误，比如网络错误或超时等  
          this.error = 'An error occurred while registering.';  
        } finally {  
          this.loading = false; // 恢复加载状态为false，启用提交按钮  
        }  
      }  
    }  
  };  
  </script>  
    
  <style>  
  /* 样式 */  
  </style> -->

  
<!-- Register.vue -->
  <template>
    <div class="container">
      <h1>Register</h1>
      <form @submit.prevent="register">
        <div class="form-group">
          <label for="name">Name</label>
          <input type="text" class="form-control" id="name" v-model="name" placeholder="Enter name">
        </div>
        <div class="form-group">
          <label for="email">Email address</label>
          <input type="email" class="form-control" id="email" v-model="email" placeholder="Enter email">
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" class="form-control" id="password" v-model="password" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-primary" :disabled="loading">Submit</button>
        <p v-if="error" class="error">{{ error }}</p>
      </form>
      <router-link to="/login">Already have an account? Login here</router-link>
    </div>
  </template>
  
  <script>
  // import axios from 'axios';
  import axios from '@/axios/axios'; // 这里使用 @ 符号代表项目根目录，路径可能需要根据实际情况进行调整

  export default {
    name: 'Register',
    data() {
      return {
        name: '',
        email: '',
        password: '',
        loading: false,
        error: ''
      };
    },
    methods: {
      async register() {
        this.error = ''; // 清除之前的错误信息
        this.loading = true; // 设置加载状态为true，禁用提交按钮
        try {
          const response = await axios.post('/api/register', { // 发送POST请求到注册API端点
            Username: this.name,  // 与后端表字段对应
            Password: this.password, // 与后端表字段对应
            Role: 'user' // 默认设置为'user'，可以根据需求修改
          });
          if (response.status === 200) {
            // 注册成功，跳转到登录页面
            this.$router.push('/login');
            // 可以添加一条成功提示信息
            // this.$message.success('Registration successful! Please log in.');
          } else {
            // 注册失败，处理错误情况
            this.error = 'Registration failed, please try again.';
          }
        } catch (error) {
          // 请求错误，处理网络错误或超时等
          this.error = 'An error occurred while registering.';
        } finally {
          this.loading = false; // 恢复加载状态为false，启用提交按钮
        }
      }
    }
  };
  </script>
  
  <style>
  /* 样式 */
  </style>
  