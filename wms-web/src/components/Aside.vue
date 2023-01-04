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
    <el-menu-item :index="'/'+ item.menuclick" v-for="(item,i) in menu" :key="i" >
      <i :class="item.menuicon"></i>
      <span slot="title">{{ item.menuname }}</span>
    </el-menu-item>

  </el-menu>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Aside",
  data() {
    return {
      // isCollapse:false

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
