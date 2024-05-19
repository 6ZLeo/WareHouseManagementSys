<template>
    <div class="automation-management">
      <el-row>
        <el-col :span="24">
          <h2 class="page-title">自动化仓库</h2>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card shadow="hover">
            <h3>自动货架系统(空空空)</h3>
            <el-table :data="shelfSystemStatus" style="width: 100%">
              <el-table-column prop="shelfId" label="货架ID" />
              <el-table-column prop="status" label="状态" />
              <el-table-column prop="lastUpdated" label="最后更新" />
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover">
            <h3>自动搬运小车系统(空空空)</h3>
            <el-table :data="robotSystemStatus" style="width: 100%">
              <el-table-column prop="robotId" label="小车ID" />
              <el-table-column prop="status" label="状态" />
              <el-table-column prop="lastUpdated" label="最后更新" />
            </el-table>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="controls">
        <el-col :span="24">
          <el-card shadow="hover">
            <h3>出库/入库控制管理(空空空)</h3>
            <el-form :model="form" label-width="120px">
              <el-form-item label="客户入库单">
                <el-input v-model="form.customerOrder" placeholder="输入客户入库单号"></el-input>
              </el-form-item>
              <el-form-item label="物流到货">
                <el-select v-model="form.logistics" placeholder="选择物流到货状态">
                  <el-option label="已到货" value="arrived"></el-option>
                  <el-option label="未到货" value="not_arrived"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="库管控制">
                <el-button type="primary" @click="startStorageProcess">开始入库（空实现）</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="shelf-visualization">
        <el-col :span="18">
          <el-card shadow="hover">
            <h3>货架状态（空实现）</h3>
            <el-col :span="6">
          <el-card shadow="hover">
            <h5>选择货架</h5>
            <el-slider v-model="selectedShelf" :min="1" :max="3" :step="1" show-stops></el-slider>
          </el-card>
        </el-col>
            <div class="shelf-grid">
              <div v-for="(slot, index) in currentShelfSlots" :key="index" :class="['shelf-slot', slot.status]">
                <span>{{ slot.id }}</span>
              </div>
            </div>
          </el-card>
        </el-col>
        <!-- <el-col :span="6">
          <el-card shadow="hover">
            <h3>选择货架</h3>
            <el-slider v-model="selectedShelf" :min="1" :max="3" :step="1" show-stops></el-slider>
          </el-card>
        </el-col> -->
      </el-row>
    </div>
  </template>

  <script>
  export default {
    name: 'AutomationManagement',
    data() {
      return {
        shelfSystemStatus: [],
        robotSystemStatus: [],
        selectedShelf: 1,
        shelves: {
          1: [
            { id: 'A1', status: 'empty' },
            { id: 'A2', status: 'filled' },
            { id: 'A3', status: 'empty' },
            { id: 'A4', status: 'filled' },
            { id: 'B1', status: 'empty' },
            { id: 'B2', status: 'filled' },
            { id: 'B3', status: 'empty' },
            { id: 'B4', status: 'filled' },

          ],
          2: [
            { id: 'A1', status: 'filled' },
            { id: 'A2', status: 'empty' },
            { id: 'A3', status: 'filled' },
            { id: 'A4', status: 'empty' },
            { id: 'B1', status: 'filled' },
            { id: 'B2', status: 'empty' },
            { id: 'B3', status: 'filled' },
            { id: 'B4', status: 'empty' },

          ],
          3: [
            { id: 'A1', status: 'empty' },
            { id: 'A2', status: 'empty' },
            { id: 'A3', status: 'filled' },
            { id: 'A4', status: 'filled' },
            { id: 'B1', status: 'empty' },
            { id: 'B2', status: 'empty' },
            { id: 'B3', status: 'filled' },
            { id: 'B4', status: 'filled' },

          ]
        },
        form: {
          customerOrder: '',
          logistics: ''
        }
      };
    },
    computed: {
      currentShelfSlots() {
        return this.shelves[this.selectedShelf];
      }
    },
    created() {
      this.fetchShelfSystemStatus();
      this.fetchRobotSystemStatus();
    },
    methods: {
      fetchShelfSystemStatus() {
        // 模拟
        this.shelfSystemStatus = [
          { shelfId: 'S001', status: '空闲', lastUpdated: '2024-05-19 10:00' },
          { shelfId: 'S002', status: '工作中', lastUpdated: '2024-05-19 10:05' },
        ];
      },
      fetchRobotSystemStatus() {
        // 模拟
        this.robotSystemStatus = [
          { robotId: 'R001', status: '空闲', lastUpdated: '2024-05-19 10:00' },
          { robotId: 'R002', status: '运输中', lastUpdated: '2024-05-19 10:05' },
        ];
      },
      startStorageProcess() {

        console.log('入库过程开始', this.form);

      }
    }
  };
  </script>

  <style scoped>
  .automation-management {
    margin: 3%;
    width: 78vw;
    overflow-y: auto;
    height: 95vh;
    padding: 20px;
  }
  .automation-management::-webkit-scrollbar{
    display: none;
  };

  .page-title {
    text-align: center;
    margin-bottom: 20px;
  }

  .controls {
    margin-top: 20px;
  }

  .shelf-visualization {
    margin-top: 20px;
  }

  .el-card {
    margin-bottom: 20px;
  }

  .shelf-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-template-rows: repeat(4, 1fr);
    gap: 10px;
    background-color: #f5f5f5;
    padding: 20px;
    border-radius: 10px;
  }

  .shelf-slot {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 80px;
    border: 2px solid #ebeef5;
    border-radius: 4px;
    font-size: 16px;
    font-weight: bold;
  }

  .shelf-slot.empty {
    background-color: #dff0d8;
  }

  .shelf-slot.filled {
    background-color: #f2dede;
  }

  </style>
