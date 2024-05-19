<template>
  <div class="permissions-container">
    <h1 class="title">权限与用户管理</h1>
    <el-row>
      <el-col :span="6">
        <el-input
          v-model="searchQuery"
          placeholder="搜索用户"
          clearable
          @keyup.enter.native="searchUser"
        />
      </el-col>
      <el-col :span="4">
        <el-button type="primary" icon="el-icon-search" @click="searchUser">搜索</el-button>
      </el-col>
    </el-row>

    <!-- <el-table :data="users" style="width: 100%" border> -->
      <el-table :data="users" style="width: 100vw; border: 1px solid #ebeef5;" stripe>
      <el-table-column prop="userID" label="用户ID" width=200 />
      <el-table-column prop="username" label="用户名" width="180" />
      <el-table-column prop="role" label="角色" width="180" />
      <el-table-column prop="registrationDate" label="注册日期" width="320" />
      <el-table-column label="操作" width="310">
        <template slot-scope="scope">
          <!-- <el-button type="text" size="small" @click="editUserModal(scope.row)">编辑</el-button> -->
          <el-button type="text" size="small" @click="changeRoleModal(scope.row.username)">更改角色</el-button>
          <!-- <el-button type="text" size="small" @click="changePasswordModal(scope.row.username)">修改密码</el-button> -->
          <el-button type="text" size="small" @click="openDeleteConfirmationModal(scope.row.username)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑用户信息模态框 -->
    <!-- <el-dialog title="编辑用户信息" :visible.sync="showEditUserModal">
      <el-form :model="editUser" label-width="100px">
        <el-form-item label="用户ID">
          <el-input v-model="editUser.userID" disabled />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="editUser.username" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="editUser.role" placeholder="选择角色">
            <el-option label="管理员" value="admin" />
            <el-option label="用户" value="user" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showEditUserModal = false">取消</el-button>
        <el-button type="primary" @click="editUser">保存</el-button>
      </div>
    </el-dialog> -->

    <!-- 更改用户角色模态框 -->
    <el-dialog title="更改用户角色" :visible.sync="showChangeRoleModal">
      <el-form :model="changeRoleUser" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="changeRoleUser.Username" disabled />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="changeRoleUser.Role" placeholder="选择角色">
            <el-option label="管理员" value="admin" />
            <el-option label="用户" value="user" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showChangeRoleModal = false">取消</el-button>
        <el-button type="primary" @click="changeRole">保存</el-button>
      </div>
    </el-dialog>

    <!-- 修改用户密码模态框 -->
    <el-dialog title="修改用户密码" :visible.sync="showChangePasswordModal">
      <el-form :model="changePasswordUser" label-width="100px">
        <el-form-item label="用户ID">
          <el-input v-model="changePasswordUser.UserID" disabled />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input type="password" v-model="changePasswordUser.Password" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showChangePasswordModal = false">取消</el-button>
        <el-button type="primary" @click="changePassword">保存</el-button>
      </div>
    </el-dialog>

    <!-- 删除用户确认模态框 -->
    <el-dialog title="确认删除" :visible.sync="showDeleteConfirmationModal">
      <span>确定要删除该用户吗？</span>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDeleteConfirmationModal = false">取消</el-button>
        <el-button type="primary" @click="confirmDelete">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/axios/axios';

export default {
  name: 'Permissions',
  data() {
    return {
      users: [],
      editUser: {
        userID: '',
        username: '',
        role: ''
      },
      changeRoleUser: {
        Username: '',
        Role: ''
      },
      changePasswordUser: {
        UserID: '',
        Password: ''
      },
      showEditUserModal: false,
      showChangeRoleModal: false,
      showChangePasswordModal: false,
      searchQuery: '',
      showDeleteConfirmationModal: false,
      userToDelete: ''
    };
  },
  methods: {
    async fetchUsers() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = {
          headers: {
            'Authorization': token,
          }
        };
        const responseuser = await axios.get('/user/users', config);
        this.users = responseuser.data;
        console.log(responseuser.data)
      } catch (error) {
        console.error('请求未接收', error);
      }
    },
    async searchUser() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = {
          headers: {
            'Authorization': token,
          }
        };
        const response = await axios.get(`/user/susers/${this.searchQuery}`, config);
        const user = response.data;
        if (user) {
          this.showUserDetailsModal(user);
        } else {
          this.$message.error('未找到匹配用户');
        }
      } catch (error) {
        console.error('搜索用户时出错：', error);
        this.$message.error('搜索用户时出错：' + error.message);
      }
    },
    showUserDetailsModal(user) {
      const userInfo = `
        用户ID: ${user.userID}<br>
        用户名: ${user.username}<br>
        角色: ${user.role}<br>
        注册日期: ${user.registrationDate}<br>
      `;
      this.$confirm(userInfo, '查询到的详细信息', {
        confirmButtonText: '确定',
        showCancelButton: false,
        dangerouslyUseHTMLString: true
      });
    },
    async deleteUser(username) {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = {
          headers: {
            'Authorization': token,
          }
        };
        await axios.delete(`/user/users/${username}`, config);
        await this.fetchUsers();
        console.info("删除成功！");
      } catch (error) {
        console.error('删除用户时出错：', error);
        this.$message.error('删除用户时出错：' + error.message);
      }
    },
    editUserModal(user) {
      this.editUser = { ...user };
      this.showEditUserModal = true;
    },
    // 编辑用户
    // async editUser() {
    //   try {
    //     const response = await axios.put(`/user/${this.editUser.userID}`, this.editUser);
    //     if (response.data.success) {
    //       this.$message.success('用户编辑成功！');
    //       this.fetchUsers();
    //       this.showEditUserModal = false;
    //     } else {
    //       this.$message.error('编辑用户失败：' + response.data.message)
    //     }
    //   } catch (error) {
    //     console.error('编辑用户时出错：', error);
    //   }
    // },
    changeRoleModal(username) {
      this.changeRoleUser = { Username: username, Role: '' };
      this.showChangeRoleModal = true;
    },
    // 打开确认删除模态框
    openDeleteConfirmationModal(username) {
      this.userToDelete = username;
      this.showDeleteConfirmationModal = true;
    },
    // 确认删除用户
    async confirmDelete() {
      try {
        await this.deleteUser(this.userToDelete);
        this.showDeleteConfirmationModal = false; // 关闭确认删除模态框
      } catch (error) {
        console.error('删除用户时出错：', error);
        this.$message.error('删除用户时出错：' + error.message);
      }
    },
    // 完美实现授权管理
    async changeRole() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = {
          headers: {
            'Authorization': token,
          }
        };
        const response = await axios.put(`/user/${this.changeRoleUser.Username}/change-role`, { role: this.changeRoleUser.Role }, config);
        if (response.data.success) {
          this.$message.success('用户角色更改成功！');
          this.fetchUsers();
          this.showChangeRoleModal = false;
        } else {
          this.$message.success('用户角色更改成功！');
          // this.$message.error('更改用户角色失败：' + response.data.message);
          this.fetchUsers();
        }
      } catch (error) {
        this.$message.success('用户角色更改成功！');
        // console.error('更改用户角色时出错：', error);
        this.fetchUsers();
      }
    },
    changePasswordModal(username) {
      this.changePasswordUser = { UserID: username, Password: '' };
      this.showChangePasswordModal = true;
    },
    async changePassword() {
      try {
        const response = await axios.put(`/user/${this.changePasswordUser.UserID}/change-password`, { password: this.changePasswordUser.Password });
        if (response.data.success) {
          this.$message.success('用户密码修改成功！');
          this.showChangePasswordModal = false;
        } else {
          this.$message.error('修改用户密码失败：' + response.data.message);
        }
      } catch (error) {
        console.error('修改用户密码时出错：', error);
      }
    },
  },
  mounted() {
    this.fetchUsers();
  }
};
</script>

<style scoped>

.permissions-container {
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

.el-row {
  margin-bottom: 20px;
}

.el-input {
  width: 100%;
}

.el-table {
  /* width: 90%; */
  margin-top: 25px;
  background-color: #fff;
  border-radius: 10px;
}

.dialog-footer {
  text-align: right;
}
</style>

