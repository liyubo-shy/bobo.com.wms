<template>
  <el-container style="height: 100vh; border: 1px solid #eee">
    <el-aside :width="aside_width" style="background-color: #495155;margin-left: -1px ">
      <div style="height: 60px;background-color: #495155;">
        <img src="../img/logo.png" style="height: 40px;width: 40px;margin-left: 10px;margin-top: 10px">
      </div>
      <Aside :isCollapseResult="isCollapse"></Aside>
    </el-aside>

    <el-container style="height: 100%;">
      <el-header style="text-align: right; font-size: 12px;height: 60px;">
        <Header @doCollapse="chageCollapse" :colleIcon="colleIcon"></Header>
      </el-header>

      <el-main style="height: 100%;">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Index",
  // eslint-disable-next-line vue/no-unused-components
  components: {Aside, Header},
  data() {
    return {
      isCollapse: false,
      aside_width:"200px",
      colleIcon:"el-icon-s-fold"
    }
  },
  methods: {
    chageCollapse() {
      this.isCollapse= !this.isCollapse
      if (this.isCollapse){
        this.aside_width = "64px"
        this.colleIcon = "el-icon-s-unfold"
      }
      else {
        this.aside_width="200px"
        this.colleIcon="el-icon-s-fold"
      }
    }

  }

}
</script>

<style scoped>
.el-header {
  background-color: #495155;
  color: #333;
  /*line-height: 60px;*/
}

.el-main {
  padding: 5px;
}

.el-aside {
  color: #333;
}
</style>
