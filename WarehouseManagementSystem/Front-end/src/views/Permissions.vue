
 <template>
  <div class="scroll-container">
  <div class="permissions">
    <!-- 用户列表 -->
    <h2>用户管理</h2>
    <div class="search-container">
  <input type="text" class="form-control" v-model="searchQuery" placeholder="搜索用户名">
</div>
    <div class="card">
      <div class="card-body">
        
        <h5 class="card-title">用户列表</h5>
      

        <table class="table">
          <thead>
            <tr>
              <th>User ID</th>
              <th>用户名</th>
              <th>角色</th>
              <th>注册日期</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.UserID">
              <td>{{ user.userID }}</td>
              <td>{{ user.username }}</td>
              <td>{{ user.role }}</td>
              <td>{{ user.registrationDate }}</td>
              <td>
                <button class="btn text-primary" @click="deleteUser(user.username)">删除</button>
                <button class="btn text-primary" @click="editUserModal(user)">编辑</button>
                <button class="btn text-primary" @click="changeRoleModal(user.UserID)">更改授权</button>
                <button class="btn text-primary" @click="changePasswordModal(user.UserID)">修改密码</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 编辑用户模态框 -->
    <b-modal v-model="showEditUserModal" title="编辑用户" @ok="editUser">
      <div class="form-group">
        <label for="editUsername">用户名</label>
        <input type="text" class="form-control" id="editUsername" v-model="editUser.Username" disabled>
      </div>
      <div class="form-group">
        <label for="editRole">角色</label>
        <select class="form-control" id="editRole" v-model="editUser.Role">
          <option value="user">普通用户</option>
          <option value="admin">管理员</option>
        </select>
      </div>
    </b-modal>

    <!-- 更改角色模态框 -->
    <b-modal v-model="showChangeRoleModal" title="更改用户角色" @ok="changeRole">
      <div class="form-group">
        <label for="changeRole">角色</label>
        <select class="form-control" id="changeRole" v-model="changeRoleUser.Role">
          <option value="user">普通用户</option>
          <option value="admin">管理员</option>
        </select>
      </div>
    </b-modal>

    <!-- 修改密码模态框 -->
    <b-modal v-model="showChangePasswordModal" title="修改密码" @ok="changePassword">
      <div class="form-group">
        <label for="newPassword">新密码</label>
        <input type="password" class="form-control" id="newPassword" v-model="changePasswordUser.Password">
      </div>
    </b-modal>
  </div>
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
      UserID: '',
      Username: '',
      Role: ''
    },
    changeRoleUser: {
      UserID: '',
      Role: ''
    },
    changePasswordUser: {
      UserID: '',
      Password: ''
    },
    showEditUserModal: false,
    showChangeRoleModal: false,
    showChangePasswordModal: false,
    searchQuery: ''
  };
},

  methods: {
    //获取用户列表信息（优先。基本功能）
    async fetchUsers() {
        try {
            const token = sessionStorage.getItem("access_token");
            const config = {
                headers: {
                    'Authorization' : token,
                }
            };
            const responseuser = await axios.get('/user/users', config);//获取用户信息，以users[]数组形式
            this.users = responseuser.data;
        } catch (error) {
            console.error('请求未接收', error);
        }
      },

    async searchUser() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = {
          headers: {
            'Authorization' : token,
          }
        };
        const response = await axios.get('/user/susers/${this.searchQuery}', config);
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
  this.$bModal.msgBoxOk(`<p>User ID: ${user.userID}</p><p>用户名: ${user.username}</p><p>角色: ${user.role}</p><p>注册日期: ${user.registrationDate}</p>`, {
    title: '用户详细信息',
    size: 'md',
    buttonSize: 'md',
    okVariant: 'primary',
    headerClass: 'p-2 border-bottom-0',
    footerClass: 'p-2 border-top-0',
    centered: true
  });
},

 

    // 删除用户
      async deleteUser(username) {
          try {
              const token = sessionStorage.getItem("access_token");
              const config = {
                  headers: {
                      'Authorization' : token,
                  }
              };
              const response = await axios.delete(`user/users/${username}`,config); // 使用模板字符串传递用户名
              await this.fetchUsers();  // 重新获取用户列表
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
    //编辑管理用户
    async editUser() {
      try {
        const response = await axios.put(`/api/user/${this.editUser.UserID}`, this.editUser);
        if (response.data.success) {
          this.$message.success('用户编辑成功！');
          this.fetchUsers();
          this.showEditUserModal = false;
        } else {
          this.$message.error('编辑用户失败：' + response.data.message)
        }
      } catch (error) {
        console.error('编辑用户时出错：', error);
      }
    },
    changeRoleModal(userId) {
      this.changeRoleUser = { UserID: userId, Role: '' };
      this.showChangeRoleModal = true;
    },
    //授权管理
    async changeRole() {
      try {
        const response = await axios.put(`/api/user/${this.changeRoleUser.UserID}/change-role`, { role: this.changeRoleUser.Role });
        if (response.data.success) {
          this.$message.success('用户角色更改成功！');
          this.fetchUsers();
          this.showChangeRoleModal = false;
        } else {
          this.$message.error('更改用户角色失败：' + response.data.message);
        }
      } catch (error) {
        console.error('更改用户角色时出错：', error);
      }
    },
    changePasswordModal(userId) {
      this.changePasswordUser = { UserID: userId, Password: '' };
      this.showChangePasswordModal = true;
    },
    //修改密码（当用户忘记密码时，admin可以直接修改）
    async changePassword() {
      try {
        const response = await axios.put(`/api/user/${this.changePasswordUser.UserID}/change-password`, { password: this.changePasswordUser.Password });
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
    showEditUserModal() {
      this.showEditUserModal = true;
      this.showChangeRoleModal = false;
      this.showChangePasswordModal = false;
    },
    showChangeRoleModal() {
      this.showEditUserModal = false;
      this.showChangeRoleModal = true;
      this.showChangePasswordModal = false;
    },
    showChangePasswordModal() {
      this.showEditUserModal = false;
      this.showChangeRoleModal = false;
      this.showChangePasswordModal = true;
    }
  },
  mounted() {
    this.fetchUsers();
  }
};
</script>

<style scoped>

.scroll-container {
  /* 固定高度并启用垂直滚动 */
  height: 100vh; /* 设置适当的高度 */
  overflow-y: auto; /* 启用垂直滚动 */
}

.card-body {
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.534); /* 背景颜色 */
  border-radius: 10px; /* 圆角矩形 */
}

.card {
  margin-top: 10%;
  padding: 30px;
  border-radius: 10px; /* 圆角矩形 */
  margin-bottom: 20px;
  border: none;
}

.card-body {
  padding: 20px;
}


/* .card{
  color: white;
  font-size: 2em;
  margin-bottom: 30px;
  border: none;
} */

.btn {
  border: none; /* 去除按钮边框 */
  padding: 10px 20px;
  transition: all 0.3s ease;
}

.btn.text-primary {
  background-color: rgba(255, 255, 255, 0.534);/* 去除按钮背景色 */
  color: #007bff; /* 蓝色文字 */
}

.btn.text-primary:hover {
  background-color: transparent; /* 去除按钮背景色 */
  color: #0056b3; /* 鼠标悬停时蓝色文字 */
}

.text-danger {
  color: #dc3545; /* 错误文本颜色 */
}
</style>


