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
      <span style="margin-left: 20px;font-size: 14px;color: #3f3f3f">性别：</span>
      <el-select v-model="sex" filterable clearable placeholder="请选择性别" style="margin-left: 5px; width: 120px">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>

<!--      <span style="margin-left: 20px;font-size: 14px;color: #3f3f3f">角色：</span>-->
<!--      <el-select v-model="role" filterable placeholder="请选择角色" style="margin-left: 5px; width: 130px">-->
<!--        <el-option-->
<!--            v-for="item in roles"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--        </el-option>-->
<!--      </el-select>-->

      <el-button style="margin-left: 5px" round @click="search" type="primary">查询</el-button>
      <el-button type="success" round @click="reSet">重置</el-button>
      <el-button type="success" round @click="add">新增</el-button>
    </div>

    <el-table v-loading="list_loading"
              height=550
              style="font-size: 15px"
              :data="tableData"
              :header-cell-style="{background:'#d7d7d7',color:'#564d4d'}"
              border>
      <el-table-column prop="id" label="id" sortable width="80"></el-table-column>
      <el-table-column prop="no" label="账号" sortable width="180"></el-table-column>
      <el-table-column prop="name" label="姓名" sortable width="180"></el-table-column>
      <el-table-column prop="sex" label="性别" sortable width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : 'danger'"
              disable-transitions>{{ scope.row.sex === 1 ? '男' : '女' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="age" label="年龄" sortable width="80"></el-table-column>
      <el-table-column prop="phone" label="电话" sortable width="180"></el-table-column>
      <el-table-column prop="roleId" label="角色" sortable width="140 ">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'success' : 'primary')"
              disable-transitions>{{ scope.row.roleId === 0 ? '超级管理员' : (scope.row.roleId === 1 ? '管理员' : '用户') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operation" label="操作">
        <template slot-scope="scope">
          <el-button type="success" round @click="mod(scope.row)">编辑</el-button>
          <el-button type="danger" round @click="del(scope.row.id,scope.row.name)">删除</el-button>
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


// import axios from "axios";axios

export default {

  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Main',
  data() {
    //校验年龄
    // let checkAge = (rule, value, callback) => {
    //   if (value > 150) {
    //     callback(new Error('年龄输⼊过⼤'));
    //   } else {
    //     callback();
    //   }
    // };


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
      // roles: [
      //   {
      //     value: 0,
      //     label: "1. 超级管理员"
      //   },
      //   {
      //     value: 1,
      //     label: "2. 管理员"
      //   },
      //   {
      //     value: 2,
      //     label: "3. 用户"
      //   },
      //   {
      //     value: 999,
      //     label: "4. 请选择"
      //   }
      // ],
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
          sex: this.sex,
          roleId: this.role
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
      // this.loadPost()
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
      this.form.roleId = row.roleId;
      // this.rules.password[0].required=false;
      // this.rules.checkPass.required=false;
      this.centerDialogVisible = true;

    },
    //删除
    del(id, name) {
      console.log(id)
      this.$confirm('是否删除当前用户信息？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        closeOnClickModal: false
      }).then(() => {
        //  接口 点击确定就会走then
        this.$axios.get(this.$httpUrl + '/user/delete?id=' + id)
        this.$message({
          message: '删除用户[' + name + ']成功~~~~~~~~~~~~~~~~~',
          type: 'success'
        })
        console.log('dedede', name)
        this.loadPost()

      }).catch(e => {
        // 取消就会走catch
        console.log(e)
      })
    },
    save() {
      //输入格式正确则保存
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log(this.form)
          //开始保存
          this.$axios.post(this.$httpUrl + '/user/save', this.form).then(res => res.data).then(res => {
            console.log(res.data)
            if (res.code === 200) {   //判断状态码是否200
              if (this.is_add) {
                this.$message({
                  message: '新增用户成功~~~',
                  type: 'success'
                })
              } else {
                this.$message({
                  message: '修改用户信息成功~~~',
                  type: 'success'
                })
              }

              this.loadPost();
              this.centerDialogVisible = false
            } else {
              //状态码不为200，保存失败
              this.$message({
                message: '新增用户失败！！！',
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
      this.$refs.form.resetFields();
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
    }
  },

}

</script>

<style scoped>

</style>


