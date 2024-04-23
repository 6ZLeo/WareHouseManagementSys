<!-- Dashboard.vue -->
<template>
  <!-- 整体布局容器 -->
  <div class="dashboard-container">
    <!-- 背景图片 -->
    <div class="background-image"></div>
    <!-- 主内容区域 -->
    <div class="main-content">
      <!-- 菜单栏 -->
      <div class="sidebar">
        <h1>菜单栏</h1>
        <!-- 菜单链接 -->
        <router-link to="/dashboard/myinfo" class="btn btn-primary">我的信息</router-link>
        <!-- 根据权限显示/隐藏权限链接 -->
        <router-link v-if="showPermissionsLink" to="/dashboard/permissions" class="btn btn-primary">权限管理</router-link>
        <router-link to="/dashboard/inventory" class="btn btn-primary">库存管理</router-link>
      </div>
      <!-- 主内容区域 -->
      <div class="content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      showPermissionsLink: false
    };
  },
  mounted() {
    // 在页面加载时检查用户权限并设置是否显示权限链接
    const userRole = sessionStorage.getItem("access_role"); // 角色信息存储在sessionStorage 中
    this.showPermissionsLink = userRole === 'admin';
  }
}
</script>

<style scoped>
/* 整体布局容器样式 */
.dashboard-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
  position: relative;
}

/* 背景图片样式 */
.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('https://source.unsplash.com/random/1920x1080');
  background-size: cover;
  background-position: center;
  filter: blur(5px); /* 高斯模糊效果 */
  z-index: -1; /* 将模糊背景置于容器和控件下方 */
}

/* 主内容区域样式 */
.main-content {
  display: flex;
  flex: 1;
}

/* 菜单栏样式 */
.sidebar {
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.534); /* 菜单栏背景颜色 */
  display: flex;
  flex-direction: column;
  gap: 10px; /* 菜单项垂直间距 */
  width: 15.5%;
}

/* 主内容区域样式 */
.content {
  flex: 1;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.61); /* 主内容背景颜色 */
}

/* 按钮样式 */
.btn {
  border-radius: 10px; /* 圆角矩形按钮 */
  background-color: #484b4e86;
  color: white;
  padding: 10px 20px;
  transition: all 0.3s ease;
  text-align: center;
  border: none;
}

.btn:hover {
  background-color: #7c96b14f;
}
</style>
