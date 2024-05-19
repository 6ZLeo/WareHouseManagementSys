<template>
  <div class="inventory-container">
    <h1 class="title">库存管理</h1>
    <el-row>
      <el-col :span="6">
        <el-input
          v-model="searchQuery"
          placeholder="搜索产品"
          clearable
          @keyup.enter.native="searchProduct"
        />
      </el-col>
      <el-col :span="4">
        <el-button type="primary" icon="el-icon-search" @click="searchProduct">搜索</el-button>
      </el-col>
    </el-row>

    <el-table :data="inventory" style="width: 100vw; border: 1px solid #ebeef5;" stripe>
      <el-table-column prop="productID" label="产品编号" width="200" />
      <el-table-column prop="productName" label="产品名称" width="180" />
      <el-table-column prop="specification" label="规格" width="180" />
      <el-table-column prop="stockQuantity" label="库存数量" width="320" />
      <el-table-column label="操作" width="310">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showStockInModal(scope.row)">入库</el-button>
          <el-button type="text" size="small" @click="showStockOutModal(scope.row)">出库</el-button>
          <el-button type="text" size="small" @click="openDeleteConfirmationModal(scope.row.productID)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="入库" :visible.sync="showStockInModalVisible">
      <el-form :model="stockInData" label-width="100px">
        <el-form-item label="产品名称">
          <el-input v-model="stockInData.productName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="规格">
          <el-input v-model="stockInData.specification" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="数量">
          <el-input type="number" v-model="stockInData.quantity" placeholder="请输入数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showStockInModalVisible = false">取消</el-button>
        <el-button type="primary" @click="handleStockIn">确认入库</el-button>
      </div>
    </el-dialog>

    <el-dialog title="出库" :visible.sync="showStockOutModalVisible">
      <el-form :model="stockOutData" label-width="100px">
        <el-form-item label="产品名称">
          <el-input v-model="stockOutData.productName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="规格">
          <el-input v-model="stockOutData.specification" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="数量">
          <el-input type="number" v-model="stockOutData.quantity" placeholder="请输入数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showStockOutModalVisible = false">取消</el-button>
        <el-button type="primary" @click="handleStockOut">确认出库</el-button>
      </div>
    </el-dialog>

    <el-dialog title="确认删除" :visible.sync="showDeleteConfirmationModalVisible">
      <span>确定要删除该产品吗？</span>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDeleteConfirmationModalVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmDelete">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/axios/axios';

export default {
  name: 'Inventory',
  data() {
    return {
      inventory: [],
      searchQuery: '',
      stockInData: {
        productName: '',
        specification: '',
        quantity: 0
      },
      stockOutData: {
        productName: '',
        specification: '',
        quantity: 0
      },
      showStockInModalVisible: false,
      showStockOutModalVisible: false,
      showDeleteConfirmationModalVisible: false,
      productToDelete: ''
    };
  },
  methods: {
    async fetchInventory() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = { headers: { 'Authorization': token } };
        const response = await axios.get('/products/all', config);
        this.inventory = response.data;
      } catch (error) {
        console.error('Error fetching inventory:', error);
      }
    },
    async searchProduct() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = { headers: { 'Authorization': token } };
        const response = await axios.get(`/products/search/${this.searchQuery}`, config);
        this.inventory = response.data;
      } catch (error) {
        console.error('Error searching product:', error);
      }
    },
    showStockInModal(row) {
      this.stockInData = { ...row };
      this.showStockInModalVisible = true;
    },
    showStockOutModal(row) {
      this.stockOutData = { ...row };
      this.showStockOutModalVisible = true;
    },
    async handleStockIn() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = { headers: { 'Authorization': token } };
        await axios.post('/products/stock/in', this.stockInData, config);
        this.fetchInventory();
        this.showStockInModalVisible = false;
      } catch (error) {
        console.error('Error handling stock in:', error);
      }
    },
    async handleStockOut() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = { headers: { 'Authorization': token } };
        await axios.post('/products/stock/out', this.stockOutData, config);
        this.fetchInventory();
        this.showStockOutModalVisible = false;
      } catch (error) {
        console.error('Error handling stock out:', error);
      }
    },
    openDeleteConfirmationModal(productID) {
      this.productToDelete = productID;
      this.showDeleteConfirmationModalVisible = true;
    },
    async confirmDelete() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = { headers: { 'Authorization': token } };
        await axios.delete(`/products/${this.productToDelete}`, config);
        this.fetchInventory();
        this.showDeleteConfirmationModalVisible = false;
      } catch (error) {
        console.error('Error deleting product:', error);
      }
    }
  },
  mounted() {
    this.fetchInventory();
  }
};
</script>

<style scoped>
.inventory-container {
  margin-left: 12%;
  margin-top: 5%;
  /* margin-top: 5%; */
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
  margin-top: 25px;
  background-color: #fff;
  border-radius: 10px;
}

.dialog-footer {
  text-align: right;
}
</style>
