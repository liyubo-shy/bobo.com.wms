<template>

  <div>
    <template>
      <el-button type="primary" @click="backToMain" style="font-size: 13px">
          <i class="el-icon-back" ></i>
          <span> 返回首页</span>
      </el-button>

      <!--      <div style="margin: 0 auto">-->
      <!--        <img :src="imgSrc2" alt="哈哈" style="height: 350px;width: 100%">-->
      <!--      </div>-->
      <template>
        <el-carousel :interval="1500" type="card" height="350px">
          <el-carousel-item v-for="item in carouselList" :key="item">
            <el-image
                :src="item"
                fit="fill"></el-image>
          </el-carousel-item>
        </el-carousel>
      </template>

      <el-descriptions class="margin-top" title="个人中心" :column="1" :size="size" border>
        <template slot="extra">
          <el-button type="primary" size="small">操作</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            头像
          </template>
          <div style="width:100px; height:100px; border-radius:100%; overflow:hidden;">
            <img :src="imgSrc1" alt="哈哈" style="width: 100px;height: 100px">
          </div>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户名
          </template>
          {{ user.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            账号
          </template>
          {{ user.no }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            手机号
          </template>
          {{ user.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-sugar"></i>
            年龄
          </template>
          {{ user.age }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-lollipop"></i>
            性别
          </template>
          <el-tag
              :type="user.sex === 1 ? 'primary' : 'danger'"
              disable-transitions>{{ user.sex === 1 ? '男' : '女' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-help"></i>
            角色
          </template>
          <el-tag
              :type="user.roleId === 0 ? 'danger' : (user.roleId === 1 ? 'success' : 'primary')"
              disable-transitions>{{ user.roleId === 0 ? '超级管理员' : (user.roleId === 1 ? '管理员' : '用户') }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
    </template>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Home",
  data() {

    return {
      user: JSON.parse(sessionStorage.getItem("CurUser")).user,

      size: 'medium',
      imgSrc1: require('@/img/meimei.jpg'),
      imgSrc2: require('@/img/638c7e4f9a967a6cec89beefba94525d.png'),

      carouselList: [
        require("@/img/2.jpg"),
        require("../img/3.jpg"),
        require("../img/ccb1fb3aa504fc6177cd5bb78ef43cef.png"),
        require("../img/b257744af8a41ec6908d8de1a4d3f6c0.jpg"),
        require("../img/98d4f85387c1002deb12895cc57097e2.jpg")],
    }
  }, methods: {
    backToMain(){
      this.$router.push('/Main')
    }
  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

</style>
