<template>
  <div class="sidebar">
    <div class="sidebar-content">
      <!-- Logo 或标题 -->
      <div class="sidebar-header">
        <img src="@/assets/logo.png" alt="Logo" class="sidebar-logo">
      </div>
      <div>
        <h1 class="sidebar-title">仓库管理系统</h1>
      </div>
      <!-- 菜单项 -->
      <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        :collapse="isCollapsed"
        background-color="#f0f2f5"
        text-color="#303133"
        active-text-color="#409eff"
      >
        <el-menu-item index="1" @click="handleMenuClick('/dashboard/myinfo')">
          <i class="el-icon-user"></i>
          <span slot="title" style="font-size: 20px;">我的信息</span>
        </el-menu-item>
        <el-menu-item index="5" @click="handleMenuClick('/dashboard/auto')">
          <i class="el-icon-connection"></i>
          <span slot="title" style="font-size: 20px;">自动化仓库</span>
        </el-menu-item>
        <el-menu-item index="3" @click="handleMenuClick('/dashboard/inventory')">
          <i class="el-icon-suitcase"></i>
          <span slot="title" style="font-size: 20px;">库存管理</span>
        </el-menu-item>
        <el-menu-item v-if="showPermissionsLink" index="2" @click="handleMenuClick('/dashboard/permissions')">
          <i class="el-icon-lock"></i>
          <span slot="title" style="font-size: 20px;">权限管理</span>
        </el-menu-item>
        <el-menu-item index="4" @click="handleMenuClick('/dashboard/notification')">
          <i class="el-icon-bell" ></i>
          <span slot="title" style="font-size: 20px;">通知中心</span>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 主内容区域 -->
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showPermissionsLink: false, // 根据权限显示/隐藏权限链接
      isCollapsed: false // 侧边栏是否折叠
    };
  },
  methods: {
    handleMenuClick(route) {
      if (this.$route.path !== route) {
        this.$router.push(route);
      }
    },
  },
  watch: {
    $route(to) {
      this.activeMenu = to.path;
    }
  },

  mounted() {
    //只有管理员才能看到权限管理菜单项
    const userRole = sessionStorage.getItem("access_role");
    this.showPermissionsLink = userRole === 'admin';
  }
};
</script>

<style scoped>
.sidebar {
  display: flex;
  position: fixed;
  height: 100vh;
}

.sidebar-content {
  width: 14vw;
  background-color: #f0f2f5;
  color: #303133;
}

.sidebar-header {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 10px;
  padding: 40px;
}

.sidebar-logo {
  width: 150px;
  height: 50px;
  margin-left: 10px;
}

.sidebar-title {
  margin-left: 1vw;
  font-size: 32px;
}

.el-menu-vertical-demo {
  border: none;
}

.el-menu-vertical-demo .el-menu-item,
.el-menu-vertical-demo .el-submenu__title {
  line-height: 9vh;
  margin-bottom: 15px; /* 菜单项之间的间距 */
}

.el-menu-vertical-demo .el-menu-item:hover,
.el-menu-vertical-demo .el-submenu.is-active .el-submenu__title {
  background-color: #409eff;
}

.el-menu-vertical-demo .el-icon {
  margin-right: 12px;
}

.el-menu-vertical-demo .el-menu-item.is-active {
  background-color: #fff;
}

.content {
  flex: 1;
  padding: 20px;
}
</style>

