<!-- Inventor.vue -->
 <template>
  <div class="inventory-container">
    <div class="main-content">
      <h2 class="mb-4">库存</h2>
      <div>
        <table class="table">
          <thead class="thead-light">
            <tr>
              <th>部件编号</th>
              <th>产品名称</th>
              <th>规格</th>
              <th>数量</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in inventory" :key="item.id">
              <td>{{ item.productID }}</td>
              <td>{{ item.productName }}</td>
              <td>{{ item.specification }}</td>
              <td>{{ item.StockQuantity }}</td>
            </tr>
          </tbody>
        </table>
        <div class="btn-group">
          <button type="button" button class="btn btn-primary" @click="showStockInModal">入库</button>
          <button type="button" button class="btn btn-danger" @click="showStockOutModal">出库</button>
        </div>
      </div>
    </div>
    
<!-- 入库模态框 -->
<b-modal v-model="showStockIn" title="入库" centered @ok="handleStockIn">
  <div class="modal-body">
    <form @submit.prevent="handleStockIn">
      <div class="form-group">
        <label for="productName">产品名称</label>
        <input type="text" class="form-control" id="productName" v-model="newPart.productName" placeholder="请输入产品名称" required>
      </div>
      <div class="form-group">
        <label for="specification">规格</label>
        <input type="text" class="form-control" id="specification" v-model="newPart.specification" placeholder="请输入规格" required>
      </div>
      <div class="form-group">
        <label for="StockQuantity">数量</label>
        <input type="number" class="form-control" id="StockQuantity" v-model="newPart.StockQuantity" placeholder="请输入数量" required>
      </div>
    </form>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-secondary" @click="hideModals">关闭</button>
    <button type="submit" class="btn btn-primary" @click="handleStockIn">确认入库</button>
  </div>
</b-modal>


    <!-- 出库模态框 -->
<b-modal v-model="showStockOut" title="出库">
  <div class="modal-body">
    <form @submit.prevent="handleStockOut">
      <!-- <div class="form-group">
        <label for="selectedPartNumber">部件编号</label>
        <select class="form-control" id="selectedPartNumber" v-model="selectedPart.id">
          <option v-for="part in inventory" :key="part.id" :value="part.id">{{ part.partNumber }}</option>
        </select>
      </div> -->
      <div class="form-group">
        <label for="productName">产品名称</label>
        <input type="text" class="form-control" id="productName" v-model="selectedPart.productName" readonly>
      </div>
      <div class="form-group">
        <label for="specification">规格</label>
        <input type="text" class="form-control" id="specification" v-model="selectedPart.specification" readonly>
      </div>
      <div class="form-group">
        <label for="quantity">数量</label>
        <input type="number" class="form-control" id="quantity" v-model="selectedPart.quantity">
      </div>
    </form>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-secondary" @click="hideModals">关闭</button>
    <button type="submit" class="btn btn-danger" @click="handleStockOut">确认出库</button>
  </div>
</b-modal>

        </div>

</template>

<script>
import axios from '@/axios/axios';

export default {
  name: 'Inventory',
  data() {
    return {
      inventory: [],
      showStockIn: false,
      showStockOut: false,
      newPart: { productName: '', specification: '', StockQuantity: 0 },
      selectedPart: { id: '', partNumber: '', name: '', stock: 0, productName: '', specification: '', quantity: 0 }
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
    // 出库
    async handleStockOut() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = { headers: { 'Authorization': token } };
        const response = await axios.post('/user/stock-out', this.selectedPart, config);
        if (response.data.success) {
          this.fetchInventory();
          this.hideModals();
          alert('出库成功！');
        } else {
          alert('出库失败：' + response.data.message);
        }
      } catch (error) {
        console.error('Error handling stock out:', error);
        alert('出库时发生错误：' + error.message);
      }
    },

    // 入库
    async handleStockIn() {
      try {
        const token = sessionStorage.getItem("access_token");
        const config = { headers: { 'Authorization': token } };
        const response = await axios.post('/products/stock/in', this.newPart, config);
        if (response.data.success) {
          this.fetchInventory();
          this.hideModals();
          alert('入库成功！');
        } else {
          alert('入库失败：' + response.data.message);
        }
      } catch (error) {
        console.error('Error handling stock in:', error);
        alert('入库时发生错误：' + error.message);
      }
    },


    showStockInModal() {
      this.showStockIn = true;
      console.log("showStockIn 的值为:", this.showStockIn);
      console.log("点击入库按钮调用showStockInModal成功！");
    },


    showStockOutModal() {
      this.showStockOut = true;
      console.log("showStockOut 的值为:", this.showStockIn);
      console.log("点击入库按钮调用showStockOutModal成功！");
    },


    hideModals() {
      this.showStockIn = false;
      this.showStockOut = false;
    }
  },
  mounted() {
    this.fetchInventory();
  }
};
</script>

<style scoped>

</style>


  