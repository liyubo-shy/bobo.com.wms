<template>
  <div class="loginBody">
    <div class="loginDiv">
      <div class="login-content">
        <h1 class="login-title">我亦无他</h1>
        <el-form :model="loginForm" label-width="100px"
                 :rules="rules" ref="loginForm">
          <el-form-item label="账号" prop="no">
            <el-input style="width: 200px" type="text" v-model="loginForm.no"
                      autocomplete="off" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input style="width: 200px" type="password" v-model="loginForm.password" clearable
                      show-password autocomplete="off" size="small" @keyup.enter.native="confirm"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 200px;font-size: 15px" type="primary" @click="confirm" :disabled="confirm_disabled">Login</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      confirm_disabled: false,
      loginForm: {
        no: '',
        password: ''
      },
      rules: {
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输密码', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    confirm() {
      this.confirm_disabled = true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) { //valid成功为true，失败为false
          //去后台验证用户名密码
          this.$axios.post(this.$httpUrl + '/user/login', this.loginForm).then(res => res.data).then(res => {
            if (res.code === 200) {
              //存储
              // console.log('res.data:',res.data)
              // console.log("res.data.user:",res.data.name)
              sessionStorage.setItem("CurUser", JSON.stringify(res.data))
              // console.log("session:"+sessionStorage.getItem("CurUser"))
              // console.log(res.data.menu, '-------')
              this.$store.commit("setMenu", res.data.menu)
              //跳转到主页
              this.$notify.success({
                title: res.data.user.name+'你好！',
                message: '登录成功',
                duration: 700
              });
              this.$router.replace('/Analysis');
              // console.log(JSON.parse(sessionStorage.getItem("CurUser")).name)
            } else {
              this.confirm_disabled = false;
              this.$notify.error({
                title: '错误',
                message: '账号或密码错误！'
              });

              return false;
            }
          });
        } else {
          this.confirm_disabled = false;
          console.log('校验失败');
          return false;
        }
      })
      ;

    }
  }
}
</script>

<style scoped>
.loginBody {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url("../img/98d4f85387c1002deb12895cc57097e2.jpg");
  background-size: cover;
  background-position: center;
}

.loginDiv {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 330px;
  border-radius: 5%;
  background-color: #DCDFE6;
  background-size: cover;
  background-position: center;

}

.login-title {
  margin: 20px 0;
  text-align: center;
}

.login-content {
  width: 400px;
  height: 250px;
  position: absolute;
  top: 25px;
  left: 25px;
}
</style>
