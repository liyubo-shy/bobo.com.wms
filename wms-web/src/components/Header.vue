<template>
  <div style="display: flex;line-height: 60px">
    <div style="margin-top: 10px">
      <i :class="colleIcon" style="font-size: 27px;color: #eeeeee;margin-left: 2px;cursor: pointer"
         @click="collapse"></i>
    </div>
    <div style="flex: 1 ;font-size: 18px;color: white;">
      <span style="margin-right: 650px">惟手熟尔</span>
    </div>

    <el-dropdown style="color: white" trigger="click">
      <span class="el-dropdown-link " style="cursor: pointer">
        {{ user.name }}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <i class="el-icon-user-solid" style="margin-left: 2px;font-size: 18px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logOut">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')).user
    }
  },

  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  props: {
    colleIcon: String
  },
  created() {
    // console.log('JSON', JSON.parse(sessionStorage.getItem('CurUser')))
    // const obj = JSON.parse(sessionStorage.getItem('CurUser'))
    // this.user = obj.user[0]
    //
    // console.log('user', this.user)
  },
  methods: {
    toUser() {
      this.$router.push('/Home')
    },
    logOut() {
      this.$confirm("确定退出？", "提示", {
        confirmButtonText: "确定",
        type: 'warning'
      }).then(() => {
        this.$router.push('/');
        sessionStorage.clear();

        this.$message({
          type: 'success',
          message: '已退出登录',
          duration: 700
        })
      }).catch(() => {
        console.log('cancel out')
      })


    },
    collapse() {
      this.$emit("doCollapse")
    },


  }
}
</script>

<style scoped>

</style>
