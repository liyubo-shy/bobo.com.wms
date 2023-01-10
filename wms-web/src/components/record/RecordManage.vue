<template>
  <div>
    <div style="height: 30px ;margin: 0 auto ">
      <el-breadcrumb separator="/" style="font-size: medium">
        <el-breadcrumb-item :to="{ path: '/Analysis' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>出入库履历</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-bottom: 5px">
      <span style="margin-left: 5px;font-size: 14px;color: #3f3f3f">物品：</span>
      <el-input placeholder="请输入物品名"
                v-model="goods"
                suffix-icon="el-icon-search"
                style="width: 160px"
                @keyup.enter.native="search"
                clearable>
      </el-input>

      <span style="margin-left: 15px">仓库：</span>
      <el-select v-model="storage" clearable placeholder="请选择仓库">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.name">
        </el-option>
      </el-select>

      <span style="margin-left: 15px">物品类型：</span>
      <el-select v-model="goodstype" clearable placeholder="请选择类型">
        <el-option
            v-for="item in goodsTypeData"
            :key="item.id"
            :label="item.name"
            :value="item.name">
        </el-option>
      </el-select>

      <span style="margin-left: 15px">创建时间：</span>
      <el-date-picker
          size="mini"
          v-model="selectDate"
          type="datetimerange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"

          :default-time="['00:00:00', '23:59:59']">
      </el-date-picker>


      <div style="width: 1317px;height: 8px"></div>


      <span style="margin-left: 5px;font-size: 14px;color: #3f3f3f">操作人：</span>
      <el-input placeholder="请输入操作人姓名"
                v-model="userId"
                suffix-icon="el-icon-search"
                style="width: 160px"
                @keyup.enter.native="search"
                clearable>
      </el-input>
      <span style="margin-left: 15px;font-size: 14px;color: #3f3f3f">管理人：</span>
      <el-input placeholder="请输入管理人姓名"
                v-model="adminId"
                suffix-icon="el-icon-search"
                style="width: 160px"
                @keyup.enter.native="search"
                clearable>
      </el-input>

      <span style="margin-left: 15px;font-size: 14px;color: #3f3f3f">类型：</span>
      <el-select v-model="inOrOut" filterable clearable placeholder="出库/入库" style="margin-left: 5px; width: 120px">
        <el-option
            v-for="item in inOrOuts"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>

      <el-button style="margin-left: 5px" type="primary" icon="el-icon-refresh-right" @click="reSet"
                 title="重置"></el-button>
      <el-button style="margin-left: 5px" icon="el-icon-search" @click="search" type="primary" title="查询"></el-button>
      <span style="margin-left:179px">操作：</span>
      <el-button type="primary" icon="el-icon-plus" @click="add" title="新增" :disabled="true"></el-button>
      <el-button type="primary" @click="handle" icon="el-icon-upload2" title="导出"></el-button>

      <el-dialog
          :before-close="handleClose"
          title="请选择导出条数"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="40%"
          center>
        <span>共{{ this.total }}条数据</span>
        <div class="block">
          <el-slider
              input-size="mini"
              v-model="value"
              show-input
              :step=this.exportStep
              :max=this.total
              show-stops
          >
          </el-slider>
          <el-checkbox v-model="checked">选择全部结果（{{ this.total }}）条</el-checkbox>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false;value = 0">取 消</el-button>
          <el-button type="primary" @click="exportData">确 定</el-button>
        </span>
      </el-dialog>

    </div>


    <el-table v-loading="list_loading"
              :row-style="{height: '40px'}"
              :cell-style="{padding: '3px'}"
              height=490
              :stripe="true"
              @selection-change="handleSelectionChange"
              style="font-size: 14px"
              :data="tableData"
              :header-cell-style="{background:'#F1F1FA',color:'#564d4d'}"
              border>
      <el-table-column type="selection"></el-table-column>
      <el-table-column fixed="left" type="index" label="序号" width="60"></el-table-column>
      <el-table-column prop="id" label="id" v-if="false" sortable width="100"></el-table-column>
      <el-table-column fixed="left" prop="goods" label="物品名" sortable width="130"></el-table-column>
      <el-table-column prop="goodstype" label="物品类型" sortable width="130"></el-table-column>
      <el-table-column prop="storage" label="所在仓库" sortable width="130"></el-table-column>
      <el-table-column prop="userId" label="操作人" sortable width="130"></el-table-column>
      <el-table-column prop="adminId" label="管理人" sortable width="130"></el-table-column>
      <el-table-column prop="count" label="出库/入库" sortable width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.count > 0 ? 'success' :'danger'"
              disable-transitions>{{ scope.row.count > 0 ? '入库' : '出库' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="count" label="出入库数量" sortable width="120"></el-table-column>
      <el-table-column prop="remark" label="出入库备注" sortable width="300"></el-table-column>
      <el-table-column prop="createDate" fixed="right" label="创建时间" sortable width="180"></el-table-column>
      <template slot="empty">
        <el-empty :image-size="180"></el-empty>
      </template>

    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[10,20,50, 100, 200]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <el-dialog
        :title="title_name"
        :visible.sync="centerDialogVisible"
        :close-on-click-modal="false"
        width="30%"
        center
        :before-close="closeDialog"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库" prop="storage">
          <el-select clearable v-model="form.storage" placeholder="请选择仓库">
            <el-option
                v-for="item in storageData"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分类" prop="goodstype">
          <el-select clearable v-model="form.goodstype" placeholder="请选择分类">
            <el-option
                v-for="item in goodsTypeData"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="物品名" prop="name">
          <el-col :span="15">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="15">
            <el-input v-model="form.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="15">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-col>
        </el-form-item>


      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="closeDialog">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>


// import axios from "axios";axios

import {handleExportNum} from "@/options/ExportExcel";

export default {

  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Record',
  data() {


    //校验物品类型是否已存在
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get(this.$httpUrl + "/goods/findByName?name=" + this.form.name).then(res => res.data).then(res => {
        if (res.code !== 200) {
          callback()
        } else {
          callback(new Error('物品已经存在'));
        }
      })
    };

    return {
      title_name: '',
      list_loading: false,
      tableData: [],
      pageNum: 1,
      pageSize: 20,
      total: 0,
      name: '',
      storage: '',
      goodstype: '',
      goods: '',
      userId: '',
      adminId: '',
      inOrOut: '',

      dialogVisible: false, //导出选择框
      value: 0, //滑条数值
      checked: false,  //是否选择全部数据导出
      exportStep: 0,  //滑条步长

      //时间选择最近日期
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      selectDate: '',
      startDate: '',
      endDate: '',


      centerDialogVisible: false,
      form: {
        id: '',
        name: '',
        remark: '',
        storage: '',
        goodstype: '',
        count: ''

      },

      inOrOuts: [
        {
          value: 0,
          label: "出库"
        },
        {
          value: 1,
          label: "入库"
        }
      ],

      //多选
      ids: [],    // 选中数组
      single: true,   // 非单个禁用
      multiple: true,   // 非多个禁用

      //表单输入规则
      rules: {
        name: [
          {required: true, message: '请输入物品名', trigger: 'blur'},
          {validator: checkDuplicate, trigger: "blur"}
        ],
        storage: [
          {required: true, message: '请选择仓库', trigger: 'blur'}
        ],
        goodstype: [
          {required: true, message: '请选择类型', trigger: 'blur'}
        ],
        count: [
          {required: true, message: '请填写数量', trigger: 'blur'},
          {pattern: /^[1-9]\d*$/, message: "请输⼊正整数", trigger: "blur"}
        ]

      },

      //仓库数据
      storageData: [],
      options: [],

      //物品类型数据
      goodsTypeData: []
    }
  },
  beforeMount() {
    this.loadStorage();
    this.loadGoodsType();
    this.loadPost();
  },
  methods: {

    //导出
    handle() {    //处理步长为十等分
      this.dialogVisible = true
      this.exportStep = handleExportNum(this.total, this.exportStep)

    },
    handleClose() {    //关闭窗口后清除value
      this.value = 0;
      this.dialogVisible = false
    },


    exportData() {
      let exportNum = 0
      if (this.checked) {
        exportNum = this.total
      } else {
        exportNum = this.value
      }
      this.dialogVisible = false
      this.$message.warning('正在导出数据,请稍等~')
      if (this.selectDate === null) {
        this.selectDate = ''
      }
      this.$axios.post(this.$httpUrl + '/record/exportRecord', {
        param: {
          goods: this.goods,
          adminId: this.adminId,
          userId: this.userId,
          goodstype: this.goodstype,
          storage: this.storage,
          inOrOut: this.inOrOut,
          startDate: this.selectDate[0],
          endDate: this.selectDate[1]
        },
        pageNum: this.pageNum,
        pageSize: exportNum
      }).then(res => {
        // console.log(res, '====')
        if (res.data.code === 200) {
          const columns = res.data.data
          const columnsData = []
          columns.forEach((row) => {
            const sums = {
              name: row.goods,
              goodstype: row.goodstype,
              storage: row.storage,
              userId: row.userId,
              adminId: row.adminId,
              inOrOut: row.inOrOut,
              count: row.count,
              remark: row.remark,
              create_date: row.createDate

            }
            columnsData.push(sums)
          })
          this.handleDownload(columnsData)
        } else {
          this.$message.error(res.msgs)
        }
      })
    },
    handleDownload(tableData) {
      import('@/options/ExportExcel').then((excel) => {
        const tHeader = [
          '物品名',
          '物品类型',
          '所在仓库',
          '操作人',
          '管理人',
          '出库/入库',
          '出入库数量',
          '备注',
          '创建时间'
        ]

        const filterVal = [
          'name',
          'goodstype',
          'storage',
          'userId',
          'adminId',
          'inOrOut',
          'count',
          'remark',
          'create_date'
        ]
        const data = this.formatJson(filterVal, tableData)
        const date = new Date()
        const y = date.getFullYear()
        let m = date.getMonth() + 1
        let d = date.getDate()
        let h = date.getHours()
        let mm = date.getMinutes()
        let s = date.getSeconds()
        const time = y + '-' + m + '-' + d + ' ' + h + '时' + mm + '分' + s + '秒'
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: `出入库履历(${time})`
        })
        this.$message.success('导出成功')
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
          filterVal.map((j) => {
            const temp_keys = j.split('.')
            if (temp_keys.length > 1) {
              let temp_result = v
              for (const m of temp_keys) {
                temp_result = temp_result[m] ? temp_result[m] : ''
                if (temp_result === '') break
              }
              return temp_result
            } else {
              return v[j]
            }
          })
      )
    },

    //点击表单的取消按钮，清除验证结果

    loadPost() {
      this.list_loading = true

      this.$axios.post(this.$httpUrl + '/record/listPage2', {
        param: {
          goods: this.goods,
          adminId: this.adminId,
          userId: this.userId,
          goodstype: this.goodstype,
          storage: this.storage,
          inOrOut: this.inOrOut,
          startDate: this.startDate,
          endDate: this.endDate
        },
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then(res => res.data).then(res => {
        console.log(res.data)
        if (res.code === 200) {   //判断状态码是否200
          //结果集的数据传入tableData
          console.log("popopopop", res.data)
          this.tableData = res.data
          this.total = res.total
        } else {
          alert("查询失败" + res.code)
        }
        this.list_loading = false
        // this.tableData=res.data
        // console.log(res)
      }).catch(e => {
        console.log(e)
        this.list_loading = false
      })
    },

    //查询
    search() {
      console.log('时间：', this.selectDate)
      for (let i = 0; i < this.selectDate.length; i++) {
        let y = this.selectDate[i].getFullYear();
        let m = (this.selectDate[i].getMonth() + 1) >= 10 ? (this.selectDate[i].getMonth() + 1) : "0" + (this.selectDate[i].getMonth() + 1);
        let d = (this.selectDate[i].getDate()) >= 10 ? (this.selectDate[i].getDate()) : "0" + (this.selectDate[i].getDate());
        let h = (this.selectDate[i].getHours()) >= 10 ? (this.selectDate[i].getHours()) : "0" + (this.selectDate[i].getHours());
        let mm = (this.selectDate[i].getMinutes()) >= 10 ? (this.selectDate[i].getMinutes()) : "0" + (this.selectDate[i].getMinutes());
        let s = (this.selectDate[i].getSeconds()) >= 10 ? (this.selectDate[i].getSeconds()) : "0" + (this.selectDate[i].getSeconds());
        let time = y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s
        console.log('新时间', time)
        i === 0 ? this.startDate = time : this.endDate = time
      }
      console.log('start',this.startDate)
      console.log('end',this.endDate)
      //每次查询后显示第一页
      this.pageNum = 1;
      this.loadPost()
    },

    //重置参数
    reSet() {
      this.name = '';
      this.storage = '';
      this.goodstype = '';
      this.loadPost()
    },

    //新增
    add() {
      this.title_name = '新增';
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
      })
    },
    save() {
      //输入格式正确则保存
      this.$refs.form.validate((valid) => {
        if (valid) {
          //开始保存
          this.$axios.post(this.$httpUrl + '/record/save', this.form).then(res => res.data).then(res => {
            console.log(res.data)
            if (res.code === 200) {   //判断状态码是否200
              this.$message({
                message: '新增出入库记录成功~~~',
                type: 'success'
              })

              this.loadPost();
              this.centerDialogVisible = false
            } else {
              //状态码不为200，保存失败
              this.$message({
                message: '新增出入库记录失败！！！',
                type: 'error'
              });
            }
          })
        } else {
          this.$message({
            message: "请规范填写信息",
            type: 'error'
          });
          return false;
        }
      });


    },
    closeDialog() {
      this.centerDialogVisible = false
      this.$refs.form.clearValidate()
    },
    resetForm() {
      this.form.id = '';  //清空id
      this.form.name = '';   //清空name
      this.form.remark = '';  //清空remark
      this.form.goodstype = '';
      this.form.storage = '';
      this.form.count = '';
      console.log('id', this.form.id)
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.pageNum = 1;
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost()
    },

    //多选
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;

    },
    handleDelete() {
      this.$confirm("是否确认删除选中的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
          .then(() => {
                this.deleteMultiple();
              }
          )
          .then(() => {
            this.loadPost()
          })
          .catch(() => {
          });

    },
    deleteMultiple() {
      console.log(this.ids)
      this.$axios.post(this.$httpUrl + '/record/deleteByNoMul', this.ids)
      this.$message.success('批量删除成功!')
      //等待500ms后台删除完再刷新页面
      setTimeout(() => this.loadPost(), 500)

    },
    loadStorage() {
      this.$axios.get(this.$httpUrl + '/storage/list').then(res => {
        this.storageData = res.data;
        this.options = this.storageData.find(item => {
          return item.name
        })
        console.log('ressssssssss', this.storageData)
        console.log('options', this.options)
      })
    }, loadGoodsType() {
      this.$axios.get(this.$httpUrl + '/goodstype/list').then(res => {
        this.goodsTypeData = res.data;
      })
    },
    formatStorage(row) {

      //加载仓库名到表格
      let storageTemp = this.storageData.find(item => {
        return item.id === row.storage
      })
      return storageTemp && storageTemp.name

    },

    formatGoodsType(row) {

      //加载物品类型到表格
      let goodsTypeTemp = this.goodsTypeData.find(item => {
        return item.id === row.goodstype
      })
      return goodsTypeTemp && goodsTypeTemp.name
    }
  },

}

</script>

<style scoped>

</style>


