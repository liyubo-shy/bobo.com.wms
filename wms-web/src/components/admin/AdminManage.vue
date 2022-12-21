<template>
  <div>
    <div style="margin-bottom: 5px">
      <span style="margin-left: 5px;font-size: 14px;color: #3f3f3f">姓名：</span>
      <el-input placeholder="请输入名字"
                v-model="name"
                suffix-icon="el-icon-search"
                style="width: 160px"
                @keyup.enter.native="search">
      </el-input>
      <span style="margin-left: 15px;font-size: 14px;color: #3f3f3f">账号：</span>
      <el-input placeholder="请输入账号"
                v-model="no"
                suffix-icon="el-icon-search"
                style="width: 160px"
                @keyup.enter.native="search">
      </el-input>
      <span style="margin-left: 20px;font-size: 14px;color: #3f3f3f">性别：</span>
      <el-select v-model="sex" filterable clearable placeholder="请选择性别" style="margin-left: 5px; width: 120px">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" icon="el-icon-refresh-right" @click="reSet"
                 title="重置"></el-button>
      <el-button style="margin-left: 5px" icon="el-icon-search" @click="search" type="primary" title="查询"></el-button>

      <span style="margin-left:340px">操作：</span>
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
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="exportData">确 定</el-button>
        </span>
      </el-dialog>
    </div>

    <el-table v-loading="list_loading"
              id="adminable"
              :stripe="true"
              @selection-change="handleSelectionChange"
              height=550
              style="font-size: 15px"
              :data="tableData"
              :header-cell-style="{background:'#d7d7d7',color:'#564d4d'}"
              border>
      <el-table-column type="selection"></el-table-column>
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column v-if="false" prop="id" label="id" sortable width="100"></el-table-column>
      <el-table-column prop="no" label="账号" sortable width="130"></el-table-column>
      <el-table-column prop="name" label="姓名" sortable width="130"></el-table-column>
      <el-table-column prop="sex" label="性别" sortable width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : (scope.row.sex ===0 ? 'danger' : 'success')"
              disable-transitions>{{ scope.row.sex === 1 ? '男' : (scope.row.sex === 0 ? '女' : '鲲') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="age" label="年龄" sortable width="80"></el-table-column>
      <el-table-column prop="phone" label="电话" sortable width="160"></el-table-column>
      <el-table-column prop="roleId" label="角色" sortable width="120 ">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'success' : 'primary')"
              disable-transitions>{{ scope.row.roleId === 0 ? '超级管理员' : (scope.row.roleId === 1 ? '管理员' : '用户') }}
          </el-tag>
        </template>
      </el-table-column>
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
        <el-form-item label="姓名" prop="name">
          <el-col :span="15">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="账号" prop="no"

        >
          <el-col :span="15">
            <el-input v-model="form.no"
                      :disabled="!is_add"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item v-if="is_add" label="密码" prop="password">
          <el-col :span="15">
            <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item v-if="is_add" label="确认密码" prop="checkPass">
          <el-col :span="15">
            <el-input type="password" v-model="form.checkPass" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="15">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="15">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
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


import {handleExportNum} from "@/options/ExportExcel";

export default {

  // eslint-disable-next-line vue/multi-word-component-names
  name: 'AdminManage',
  data() {

    //校验账号是否已存在
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get(this.$httpUrl + "/user/findByNo?no=" + this.form.no).then(res => res.data).then(res => {
        if (res.code !== 200) {
          callback()
        } else {
          callback(new Error('账号已经存在'));
        }
      })
    };

    //校验密码
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.form.checkPass !== '') {
          this.$refs.form.validateField('checkPass');
        }
        callback();
      }
    };
    //校验两次密码是否一致
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
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
      sex: '',
      role: '',
      no: '',

      //下拉框
      sexs: [
        {
          value: 0,
          label: "1. 女",
        },
        {
          value: 1,
          label: "2. 男"
        }
      ],
      //导出excel
      dialogVisible: false, //导出选择框
      value: 0, //滑条数值
      checked: false,  //是否选择全部数据导出
      exportStep: 0,  //滑条步长

      centerDialogVisible: false,
      form: {
        name: '',
        no: '',
        password: '',
        checkPass: '',
        age: '',
        phone: '',
        sex: '',
        roleId: '1'
      },

      //多选
      ids: [],    // 选中数组
      single: true,   // 非单个禁用
      multiple: true,   // 非多个禁用

      //表单输入规则
      rules: {
        name: [
          {required: true, message: '请输入名字', trigger: 'blur'},
        ],
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
          {validator: checkDuplicate, trigger: "blur"}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
          {validator: validatePass2, trigger: 'blur'}
        ],
        age: [
          {required: true, message: '请输⼊年龄', trigger: 'blur'},
          // {min: 0, max: 150, message: '年龄必须在 1-150 之间', trigger: 'blur'},
          // {pattern: /^([1-9][0-9]*){1,3}$/, message: '年龄必须为正整数字', trigger: "blur"},
          // {validator: checkAge, trigger: 'blur'}
        ],
        phone: [
          {required: true, message: "⼿机号不能为空", trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输⼊正确的⼿机号码", trigger: "blur"}
        ],
        sex: [
          {required: true, message: "请选择性别", trigger: "blur"}
        ]
      },
    }
  },
  beforeMount() {
    // this.loadGet();
    this.loadPost();
  },
  methods: {

    //导出excel
    handle() {    //处理步长为十等分
      this.dialogVisible = true
      this.exportStep = handleExportNum(this.total, this.exportStep)

      console.log(this.exportStep, 'stepppppp')

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
      this.$axios.post(this.$httpUrl + '/user/listPage1', {
        param: {
          name: this.name,
          sex: this.sex,
          no: this.no,
          roleId: 1
        },
        pageNum: this.pageNum,
        pageSize: exportNum
      }).then(res => {
        // console.log(res, '====')
        if (res.data.code === 200) {
          const columns = res.data.data
          const columnsData = []
          console.log('data.data::::::::::', res.data.data)
          columns.forEach((row) => {
            const sums = {
              no: row.no,
              name: row.name,
              sex: row.sex === 0 ? '女' : '男',
              age: row.age,
              phone: row.phone,
              role: row.roleId === 0 ? '超级管理员' : row.roleId === 1 ? '管理员' : '用户',
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
          '账号',
          '姓名',
          '性别',
          '年龄',
          '手机号码',
          '角色',
          '创建时间',
          '更新时间'
        ]

        const filterVal = [
          'no',
          'name',
          'sex',
          'age',
          'phone',
          'role',
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
          filename: `管理员列表(${time})`
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


    loadGet() {
      this.$axios.get(this.$httpUrl + '/user/list').then(res => res.data).then(res => {
        console.log(res)
      })
    },
    //点击表单的取消按钮，清除验证结果

    loadPost() {

      this.list_loading = true
      this.$axios.post(this.$httpUrl + '/user/listPage1', {
        param: {
          name: this.name,
          no: this.no,
          sex: this.sex,
          roleId: 1
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
      this.sex = '';
      this.no = '';
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
      this.form.no = row.no;
      this.form.name = row.name;
      this.form.password = '';
      this.form.age = row.age;
      this.form.phone = row.phone;
      this.form.sex = row.sex;
      this.form.roleId = 1
      // this.rules.password[0].required=false;
      // this.rules.checkPass.required=false;
      this.centerDialogVisible = true;

    },
    //删除
    del(id, name) {

      console.log('del:', name)
      this.$axios.get(this.$httpUrl + '/user/delete?id=' + id)
      this.$message({
        message: '删除用户[' + name + ']成功~~~~~~~~~~~~~~~~~',
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
          if (this.is_add) {
            this.$axios.post(this.$httpUrl + '/user/save', this.form)
            this.$message({
              message: '新增用户成功~~~',
              type: 'success'
            })
          } else {
            this.$axios.post(this.$httpUrl + '/user/update', this.form)
            this.$message({
              message: '修改用户成功~~~',
              type: 'success'
            })
          }

          this.loadPost();
          this.centerDialogVisible = false

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
    }
    ,
    resetForm() {
      this.form.id = '';
      this.form.roleId = 1;
      this.form.name = '';
      this.form.password = '';
      this.form.sex = '';
      this.form.age = '';
      this.form.phone = '';
      this.form.no = '';
    }
    ,
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.pageNum = 1;
      this.loadPost()
    }
    ,
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost()
    },

    //多选
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      console.log('多选id:', selection.map(item => item.id))
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
      this.$axios.post(this.$httpUrl + '/user/deleteByNoMul', this.ids)
      this.$message.success('批量删除成功!')
      //等待500ms后台删除完再刷新页面
      setTimeout(() => this.loadPost(), 500)

    },
  },

}

</script>

<style scoped>

</style>


