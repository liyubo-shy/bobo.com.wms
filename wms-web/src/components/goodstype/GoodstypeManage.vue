<template>
  <div>
    <div style="height: 40px">
      <el-breadcrumb separator="/" style="font-size: medium">
        <el-breadcrumb-item :to="{ path: '/Analysis' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>物品</el-breadcrumb-item>
        <el-breadcrumb-item>物品类型</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-bottom: 5px">
      <span style="margin-left: 5px;font-size: 14px;color: #3f3f3f">物品类型：</span>
      <el-input placeholder="请输入物品类型"
                v-model="name"
                suffix-icon="el-icon-search"
                style="width: 160px"
                @keyup.enter.native="search">
      </el-input>

      <el-button style="margin-left: 5px" type="primary" icon="el-icon-refresh-right" @click="reSet"
                 title="重置"></el-button>
      <el-button style="margin-left: 5px" icon="el-icon-search" @click="search" type="primary" title="查询"></el-button>


      <span style="margin-left:716px">操作：</span>
      <el-button type="primary" icon="el-icon-plus" @click="add" title="新增"></el-button>
      <el-button type="primary" @click="handle" icon="el-icon-upload2" title="导出"></el-button>
      <el-button size="small"
                 type="danger"
                 @click="handleDelete()"
                 class="btnItem"
                 style="margin-left:10px;"
                 icon="el-icon-delete"
                 :disabled="multiple"
      >批量删除
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
      <el-table-column type="selection"></el-table-column>
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column prop="id" label="id" v-if="false" sortable width="100"></el-table-column>
      <el-table-column prop="name" label="物品类型" sortable width="200"></el-table-column>
      <el-table-column prop="remark" label="备注" sortable width="400"></el-table-column>
      <el-table-column prop="createDate" label="创建时间" sortable width="180"></el-table-column>
      <el-table-column prop="updateDate" label="更新时间" sortable width="180"></el-table-column>
      <el-table-column prop="operation" label="操作" width="130" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" @click="mod(scope.row)" title="编辑"></el-button>
          <el-popconfirm
              confirm-button-text='删除'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确认删除该数据吗？"
              @confirm="del(scope.row.id,scope.row.name)"
              style="margin-left: 8px"
          >
            <el-button type="danger" icon="el-icon-delete" slot="reference" title="删除"></el-button>
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
        <!--        <el-form-item label="id" prop="id">-->
        <!--          <el-col :span="15">-->
        <!--            <el-input v-model="form.id"></el-input>-->
        <!--          </el-col>-->
        <!--        </el-form-item>-->
        <el-form-item label="物品类型" prop="name">
          <el-col :span="15">
            <el-input v-model="form.name"></el-input>
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
  name: 'Goodstype',
  data() {


    //校验物品类型是否已存在
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get(this.$httpUrl + "/goodstype/findByName?name=" + this.form.name).then(res => res.data).then(res => {
        if (res.code !== 200) {
          callback()
        } else {
          callback(new Error('类型已经存在'));
        }
      })
    };

    return {
      is_add: true,
      title_name: '',
      list_loading: false,
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      name: '',

      dialogVisible: false, //导出选择框
      value: 0, //滑条数值
      checked: false,  //是否选择全部数据导出
      exportStep: 0,  //滑条步长


      centerDialogVisible: false,
      form: {
        id: '',
        name: '',
        remark: ''
      },

      //多选
      ids: [],    // 选中数组
      single: true,   // 非单个禁用
      multiple: true,   // 非多个禁用

      //表单输入规则
      rules: {
        name: [
          {required: true, message: '请输入物品类型', trigger: 'blur'},
          {validator: checkDuplicate, trigger: "blur"}
        ],

      },
    }
  },
  beforeMount() {

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
      this.$axios.post(this.$httpUrl + '/goodstype/listPage1', {
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
      console.log(JSON.parse(sessionStorage.getItem('CurUser')).token)
      this.list_loading = true
      this.$axios.post(this.$httpUrl + '/goodstype/listPage1', {
        param: {
          name: this.name
        },
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then(res => res.data).then(res => {
        console.log(res.data)
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

      this.centerDialogVisible = true;

    },
    //删除
    del(id, name) {

      //  接口 点击确定就会走then
      this.$axios.get(this.$httpUrl + '/goodstype/delete?id=' + id)
      this.$message({
        message: '删除物品类型[' + name + ']成功~~~~~~~~~~~~~~~~~',
        type: 'success'
      })
      console.log('dedede', name)
      this.loadPost()

    },
    save() {
      //输入格式正确则保存
      this.$refs.form.validate((valid) => {
        if (valid) {
          //开始保存
          this.$axios.post(this.$httpUrl + '/goodstype/save', this.form).then(res => res.data).then(res => {
            console.log(res.data)
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
      this.$axios.post(this.$httpUrl + '/goodstype/deleteByNoMul', this.ids)
      this.$message.success('批量删除成功!')
      //等待500ms后台删除完再刷新页面
      setTimeout(()=>this.loadPost(),500)

    },
  },

}

</script>

<style scoped>

</style>


