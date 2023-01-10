<template>
  <el-menu
      background-color="#495155"
      text-color="#FFFFFF"
      active-text-color="#FFDD66"
      style="height: 650px"
      :collapse-transition="false"
      :collapse="isCollapseResult"
      router
  >
    <el-menu-item index="/Analysis">
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>
    <el-submenu index="1" v-if=adminOrUserShow>
      <template slot="title">
        <i class="el-icon-s-custom"></i>
        <span>账号管理</span>
      </template>
      <el-menu-item index="/Admin" v-if=adminShow class="admin">
        <i class="el-icon-s-custom"></i>
        <span slot="title">管理员管理</span>
      </el-menu-item>
      <el-menu-item index="/User" class="user">
        <i class="el-icon-user"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
    </el-submenu>

    <el-submenu index="2" v-if=goodsTypeOrGoodsShow>
      <template slot="title">
        <i class="el-icon-s-goods"></i>
        <span>物品</span>
      </template>
      <el-menu-item index="/Goodstype" v-if=goodsShow class="goodstype">
        <i class="el-icon-menu" ></i>
        <span slot="title">物品类型</span>
      </el-menu-item>
      <el-menu-item index="/Goods" class="goods">
        <i class="el-icon-s-management"></i>
        <span slot="title">物品管理</span>
      </el-menu-item>
    </el-submenu>

    <el-menu-item index="/Storage" v-if=storageShow>
      <i class="el-icon-office-building"></i>
      <span slot="title">仓库管理</span>
    </el-menu-item>

    <el-menu-item index="/Record" v-if=recordShow>
      <i class="el-icon-s-order"></i>
      <span slot="title">出入库履历</span>
    </el-menu-item>

<!--    <el-menu-item :index="'/'+ item.menuclick" v-for="(item,i) in menu" :key="i">-->
<!--      <i :class="item.menuicon"></i>-->
<!--      <span slot="title">{{ item.menuname }}</span>-->
<!--    </el-menu-item>-->

  </el-menu>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Aside",
  data() {
    return {
      // isCollapse:false
      adminOrUserShow: false,
      adminShow: false,
      storageShow: false,
      goodsTypeOrGoodsShow: false,
      goodsShow: false,
      recordShow: false
    }
  },
  beforeMount() {
    for (const i in this.menu) {

      switch (this.menu[i].menuclick) {
        case 'User':
          this.adminOrUserShow = true;
          break
        case 'Admin':
          this.adminShow = true;
          break
        case 'Storage':
          this.storageShow = true
          break
        case 'Goods':
          this.goodsTypeOrGoodsShow = true;
          break
        case 'Goodstype' :
          this.goodsShow = true
          break
        case 'Record':
          this.recordShow = true
          break
      }
    }
  },
  computed: {
    "menu": {
      get() {
        return this.$store.state.menu
      }
    }
  },
  props: {
    isCollapseResult: Boolean
  }

}
</script>

<style scoped>

</style>
