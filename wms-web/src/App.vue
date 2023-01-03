<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  },
  data(){
    return{
      user : JSON.parse(sessionStorage.getItem('CurUser')),
    }
  },
  watch:{
    '$store.state.menu':{
      handler(val,old){
        // console.log('old',old)
        // console.log('user',this.user)
        // console.log('user.no',this.user.user.no)
        if (!old && this.user && this.user.user.no){

          this.$store.commit("setRouter",val)
        }
      },
      immediate:true
    }
  }
}
</script>

<style>
#app {
  height: 100%;
}
</style>
