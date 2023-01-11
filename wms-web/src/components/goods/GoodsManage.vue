<template>
  <div>
    <div style="height: 40px">
      <el-breadcrumb separator="/" style="font-size: medium">
        <el-breadcrumb-item :to="{ path: '/Analysis' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>物品</el-breadcrumb-item>
        <el-breadcrumb-item>物品管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-bottom: 5px">
      <span style="margin-left: 5px;font-size: 14px;color: #3f3f3f">物品：</span>
      <el-input placeholder="请输入物品名"
                v-model="name"
                suffix-icon="el-icon-search"
                style="width: 160px"
                @keyup.enter.native="search">
      </el-input>

      <span style="margin-left: 15px">仓库：</span>
      <el-select v-model="storage" clearable placeholder="请选择仓库">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>

      <span style="margin-left: 15px;">类型：</span>
      <el-select v-model="goodstype" clearable placeholder="请选择类型">
        <el-option
            v-for="item in goodsTypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <span style="margin-left: 15px">状态：</span>
      <el-select v-model="is_dis" filterable clearable placeholder="状态" style="margin-left: 5px; width: 120px">
        <el-option
            v-for="item in is_diss"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>

      <el-button style="margin-left: 5px" type="primary" icon="el-icon-refresh-right" @click="reSet"
                 title="重置"></el-button>
      <el-button style="margin-left: 5px" icon="el-icon-search" @click="search" type="primary" title="查询"></el-button>
      <span style="margin-left:30px">操作：</span>
      <el-button type="primary" icon="el-icon-plus" @click="add" title="新增"></el-button>
      <el-button type="primary" @click="handle" icon="el-icon-upload2" title="导出"></el-button>
      <el-button size="small"
                 type="danger"
                 @click="handleDelete()"
                 class="btnItem"
                 style="margin-left:10px;"
                 icon="el-icon-delete"
                 :disabled="multiple"
      >批量冻结
      </el-button>
      <el-dialog
          :before-close="handleClose"
          title="请选择导出条数"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="40%">
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

      <el-dialog
          :before-close="closeGoodsIn"
          title="入库"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible_goodsIn"
          width="55%"
      center>
        <el-form :inline="true" ref="form" :model="InOutform" :rules="rules_goodsIn" label-width="140px">

          <el-form-item label="物品名:" prop="name" >
            <el-input v-model="InOutform.name" disabled></el-input>
          </el-form-item>
          <el-form-item label="分类:" prop="goodstype">
            <el-select disabled clearable v-model="InOutform.goodstype" placeholder="请选择分类">
              <el-option
                  v-for="item in goodsTypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="当前库存:" prop="count">
            <el-input v-model="InOutform.count" disabled></el-input>
          </el-form-item>
          <el-form-item label="入库数量:" prop="inCount">
            <el-input-number size="medium" v-model="inCount" :min="1" :max="99999999"></el-input-number>
          </el-form-item>
          <el-form-item label="操作人:" prop="userId">
            <el-select filterable clearable v-model="InOutform.userId" placeholder="请选择操作人" disabled>
              <el-option
                  v-for="item in userData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                  :no="item.no">
                <span style="float: left">{{ item.name }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.no }}</span>
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="管理人:" prop="adminId">
            <el-select filterable clearable v-model="InOutform.adminId" placeholder="请选择管理人">
              <el-option
                  v-for="item in adminData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                  :no="item.no">
                <span style="float: left">{{ item.name }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.no }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入库备注:" prop="remark">
            <el-input type="textarea" v-model="InOutform.remark" style="width: 600px;"></el-input>
          </el-form-item>


        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="closeGoodsIn">取 消</el-button>
    <el-button type="primary" @click="saveIn">确 定</el-button>
  </span>
      </el-dialog>

      <el-dialog
          :before-close="closeGoodsOut"
          title="出库"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible_goodsOut"
          width="55%"
      center>
        <el-form :inline="true" ref="form" :model="InOutform" :rules="rules_goodsIn" label-width="140px">

          <el-form-item label="物品名:" prop="name" >
            <el-input v-model="InOutform.name" disabled></el-input>
          </el-form-item>
          <el-form-item label="分类:" prop="goodstype">
            <el-select disabled clearable v-model="InOutform.goodstype" placeholder="请选择分类">
              <el-option
                  v-for="item in goodsTypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="当前库存:" prop="count">
            <el-input v-model="InOutform.count" disabled></el-input>
          </el-form-item>
          <el-form-item label="出库数量:" prop="outCount">
            <el-input-number size="medium" v-model="outCount" :min="1" :max="InOutform.count"></el-input-number>
          </el-form-item>

          <el-form-item label="操作人:" prop="userId">
            <el-input v-model="InOutform.userId" disabled>{{}}</el-input>
          </el-form-item>
<!--          <el-form-item label="操作人:" prop="userId">-->
<!--            <el-select filterable clearable v-model="InOutform.userId" placeholder="请选择操作人" disabled>-->
<!--              <el-option-->
<!--                  v-for="item in userData"-->
<!--                  :key="item.id"-->
<!--                  :label="item.name"-->
<!--                  :value="item.id"-->
<!--              :no="item.no">-->
<!--                <span style="float: left">{{ item.name }}</span>-->
<!--                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.no }}</span>-->
<!--              </el-option>-->
<!--            </el-select>-->
<!--          </el-form-item>-->

          <el-form-item label="管理人:" prop="adminId">
            <el-select filterable clearable v-model="InOutform.adminId" placeholder="请选择管理人">
              <el-option
                  v-for="item in adminData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
              :no="item.no">
                <span style="float: left">{{ item.name }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.no }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入库备注:" prop="remark">
            <el-input type="textarea" v-model="InOutform.remark" style="width: 600px;"></el-input>
          </el-form-item>


        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="closeGoodsOut">取 消</el-button>
    <el-button type="primary" @click="saveOut">确 定</el-button>
  </span>
      </el-dialog>

    </div>

    <el-table v-loading="list_loading"
              :row-style="{height: '40px'}"
              :cell-style="{padding: '3px'}"
              height=510
              :stripe="true"
              @selection-change="handleSelectionChange"
              style="font-size: 14px"
              :data="tableData"
              :header-cell-style="{background:'#F1F1FA',color:'#564d4d'}"
              border>
      <el-table-column type="selection" :selectable="selectEnable"></el-table-column>
      <el-table-column fixed="left" type="index" label="序号" width="60"></el-table-column>
      <el-table-column prop="id" label="id" v-if="false" sortable width="100"></el-table-column>
      <el-table-column fixed="left" prop="name" label="物品名" sortable width="130"></el-table-column>
      <el-table-column prop="goodstype" label="物品类型" :formatter="formatGoodsType" sortable
                       width="130"></el-table-column>
      <el-table-column prop="storage" label="仓库" :formatter="formatStorage" sortable width="280"></el-table-column>
      <el-table-column prop="count" label="库存" sortable width="80"></el-table-column>
      <el-table-column prop="remark" label="物品备注" sortable width="300"></el-table-column>
      <el-table-column prop="isDisabled" label="状态" sortable width="100">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.isDisabled === 1 ? 'danger' :'success'"
              disable-transitions>{{ scope.row.isDisabled === 1 ? '冻结' : '正常' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createDate" label="创建时间" sortable width="180"></el-table-column>
      <el-table-column prop="updateDate" label="更新时间" sortable width="180"></el-table-column>
      <el-table-column prop="operation" label="操作" width="180" fixed="right">
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" :disabled="scope.row.isDisabled===1" @click="goodsIn(scope.row)">
            入库
          </el-link>
          <span style="color: #d3dce6"> | </span>
          <el-link type="primary" :underline="false" :disabled="scope.row.isDisabled===1" @click="goodsOut(scope.row)">出库</el-link>
          <span style="color: #d3dce6"> | </span>
          <el-link type="primary" :underline="false" @click="mod(scope.row)" :disabled="scope.row.isDisabled===1">编辑
          </el-link>
          <span style="color: #d3dce6"> |</span>
          <el-popconfirm
              confirm-button-text='冻结'
              cancel-button-text='取消'
              icon="el-icon-warning-outline"
              icon-color="red"
              title="确认冻结该物品吗？"
              @confirm="del(scope.row.id,scope.row.name)"
              style="margin-left: 8px"
          >
            <el-link style="font-weight: bold" type="danger" slot="reference" :underline="false"
                     :disabled="scope.row.isDisabled===1">冻结
            </el-link>
          </el-popconfirm>
        </template>
      </el-table-column>
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
            <el-input v-model="form.name" :disabled="!is_add"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="15">
            <el-input v-model="form.count" :disabled="!is_add"></el-input>
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
  name: 'Goods',
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
      is_add: true,
      title_name: '',
      list_loading: false,
      tableData: [],
      pageNum: 1,
      pageSize: 20,
      total: 0,
      name: '',
      storage: '',
      goodstype: '',
      is_dis: '',
      inCount: 0,
      outCount:0,

      dialogVisible: false, //导出选择框
      dialogVisible_goodsIn: false,  //入库dialog
      dialogVisible_goodsOut: false,  //入库dialog
      goodsId:0,  //出入库物品的id
      value: 0, //滑条数值
      checked: false,  //是否选择全部数据导出
      exportStep: 0,  //滑条步长


      centerDialogVisible: false,
      form: {
        id: '',
        name: '',
        remark: '',
        storage: '',
        goodstype: '',
        count: '',

      },
      //出入库表单
      InOutform: {
        id: '',
        name: '',
        remark: '',
        storage: '',
        goodstype: '',
        count: '',
        userId:'',
        adminId:''

      },

      is_diss: [
        {
          value: 0,
          label: "0.正常"
        },
        {
          value: 1,
          label: "1.冻结"
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
      rules_goodsIn: {
        remark: [
          {required: true, message: '请输入入库备注', trigger: 'blur'}
        ],
        userId:[
          {required:true,message:'请选择操作人',trigger:'blur'}
        ],
        adminId:[
          {required:true,message:'请选择管理人',trigger:'blur'}
        ]
      },

      //仓库数据
      storageData: [],
      options: [],

      //物品类型数据
      goodsTypeData: [],
      //出入库用户选择
      userData:[],
      adminData:[]
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
      this.$axios.post(this.$httpUrl + '/goods/listPage1', {
        param: {
          name: this.name,
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
              name: row.name,
              remark: row.remark,
              createDate: row.createDate,
              updateDate: row.updateDate
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
          '物品类型',
          '备注',
          '创建时间',
          '更新时间'
        ]

        const filterVal = [
          'name',
          'remark',
          'createDate',
          'updateDate'
        ]
        const data = this.formatJson(filterVal, tableData)
        const date = new Date()
        const y = date.getFullYear()
        let m = date.getMonth() + 1
        let d = date.getDate()
        let h = date.getHours()
        let mm = date.getMinutes()
        let s = date.getSeconds()
        const time = y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: `物品类型列表(${time})`
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
      this.$axios.post(this.$httpUrl + '/goods/listPage1', {
        param: {
          name: this.name,
          storage: this.storage,
          goodstype: this.goodstype,
          isDisabled: this.is_dis
        },
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then(res => res.data).then(res => {
        if (res.code === 200) {   //判断状态码是否200
          //结果集的数据传入tableData
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
      this.is_add = true
      this.$nextTick(() => {
        this.resetForm();
      })
    },
    //点击编辑按钮，从表格拿到数据传给form
    mod(row) {
      this.title_name = '编辑';
      this.is_add = false
      this.form.id = row.id;
      this.form.name = row.name;
      this.form.remark = row.remark;
      this.form.storage = row.storage;
      this.form.goodstype = row.goodstype;
      this.form.count = row.count;
      // this.rules.password[0].required=false;
      // this.rules.checkPass.required=false;
      this.centerDialogVisible = true;

    },
    //删除
    del(id, name) {
      //  接口 点击确定就会走then
      this.$axios.post(this.$httpUrl + '/goods/updateIsDisabled', {id: id})
      this.$message({
        message: '冻结物品[' + name + ']成功~~~~~~~~~~~~~~~~~',
        type: 'success'
      })
      this.loadPost()

    },
    save() {
      //输入格式正确则保存
      this.$refs.form.validate((valid) => {
        if (valid) {
          //开始保存
          this.$axios.post(this.$httpUrl + '/goods/save', this.form).then(res => res.data).then(res => {
            if (res.code === 200) {   //判断状态码是否200
              if (this.is_add) {
                this.$message({
                  message: '新增物品类型成功~~~',
                  type: 'success'
                })
              } else {
                this.$message({
                  message: '修改物品类型成功~~~',
                  type: 'success'
                })
              }

              this.loadPost();
              this.centerDialogVisible = false
            } else {
              //状态码不为200，保存失败
              this.$message({
                message: '新增物品类型失败！！！',
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
    },
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.pageNum = 1;
      this.loadPost()
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
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
      this.$confirm("是否确认冻结选中的数据项?", "警告", {
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
      for (let i = 0; i < this.ids.length; i++) {
        this.$axios.post(this.$httpUrl + '/goods/updateIsDisabled', {id: this.ids[i]})
      }
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
    },
    selectEnable(row) {
      return row.isDisabled !== 1;
    },
    goodsIn(row) {
      this.goodsId = row.id;
      this.dialogVisible_goodsIn = true;
      this.InOutform.name = row.name;
      this.InOutform.count = row.count;
      this.InOutform.goodstype = row.goodstype;
      this.InOutform.storage = row.storage;
      this.InOutform.userId = JSON.parse(sessionStorage.getItem("CurUser")).user.name
      this.loadUser();
    },
    goodsOut(row) {
      this.goodsId = row.id;
      this.dialogVisible_goodsOut = true;
      this.InOutform.name = row.name;
      this.InOutform.count = row.count;
      this.InOutform.goodstype = row.goodstype;
      this.InOutform.storage = row.storage;
      this.InOutform.userId = JSON.parse(sessionStorage.getItem("CurUser")).user.name
      this.loadUser();
    },
    closeGoodsIn() {
      this.inCount = 1;
      this.InOutform.remark = '';
      this.InOutform.adminId = '';
      this.InOutform.userId = '';
      this.dialogVisible_goodsIn = false
    },
    closeGoodsOut(){
      this.outCount = 1;
      this.InOutform.remark= '';
      this.InOutform.adminId = '';
      this.InOutform.userId = '';
      this.dialogVisible_goodsOut = false;
    },
    loadUser() {
      this.$axios.get(this.$httpUrl + '/user/listUser').then(res => {
        this.userData = res.data;
      })

      this.$axios.get(this.$httpUrl + '/user/listAdmin').then(res => {
        this.adminData = res.data;
      })
    },
    saveIn() {
      //输入格式正确则保存
      this.$refs.form.validate((valid) => {
        if (valid) {
          //开始保存
          console.log('user:')
          this.$axios.post(this.$httpUrl + '/record/saveIn', {
            goods:this.goodsId,
            goodstype:this.InOutform.goodstype,
            storage:this.InOutform.storage,
            userId:JSON.parse(sessionStorage.getItem("CurUser")).user.id,
            adminId:this.InOutform.adminId,
            count:this.inCount,
            remark:this.InOutform.remark
          }).then(res => res.data).then(res => {
            if (res.code === 200) {   //判断状态码是否200
                this.$message({
                  message: '入库成功~~~',
                  type: 'success'
                })
              this.loadPost();
              this.dialogVisible_goodsIn = false
            } else {
              //状态码不为200，保存失败
              this.$message({
                message: '入库失败！！！',
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
      this.closeGoodsIn();
    },

    saveOut() {
      //输入格式正确则保存
      this.$refs.form.validate((valid) => {
        if (valid) {
          //开始保存
          console.log('user:')
          this.$axios.post(this.$httpUrl + '/record/saveOut', {
            goods:this.goodsId,
            goodstype:this.InOutform.goodstype,
            storage:this.InOutform.storage,
            userId:JSON.parse(sessionStorage.getItem("CurUser")).user.id,
            adminId:this.InOutform.adminId,
            count:this.outCount,
            remark:this.InOutform.remark
          }).then(res => res.data).then(res => {
            if (res.code === 200) {   //判断状态码是否200
              this.$message({
                message: '出库成功~~~',
                type: 'success'
              })
              this.loadPost();
              this.dialogVisible_goodsOut = false
            } else {
              //状态码不为200，保存失败
              this.$message({
                message: '出库失败！！！',
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
      this.closeGoodsOut();
    },
  },

}

</script>

<style scoped>

</style>


