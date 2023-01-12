<template>
  <div id="app">
    <el-breadcrumb separator="/" style="font-size: medium">
      <el-breadcrumb-item :to="{ path: '/Analysis' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>报表</el-breadcrumb-item>
    </el-breadcrumb>
    <e-charts class="inOrOutAnalysis" :options="inOrOutOption"></e-charts>
    <div class="bu">
      <el-button icon="el-icon-refresh" @click="getDate()" type="primary" style="float: right;"></el-button>
    </div>
    <e-charts class="goodsCountAnalysis" :options="goodsCountOption"></e-charts>
    <e-charts class="storageAnalysis" :options="storageOption"></e-charts>
  </div>
</template>

<script>

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Analysis',
  data() {

    return {
      inDate: [],
      outDate: [],
      goodsCountDate:[],
      storageDate:[],
    }
  },
  methods: {
    getDate() {
      //出入库月报
      this.$axios.get(this.$httpUrl + '/record/recordInAnalysis').then(res => {
        this.inDate = res.data;
      })
      this.$axios.get(this.$httpUrl + '/record/recordOutAnalysis').then(res => {
        this.outDate = res.data;
      })

      //物品库存
      this.$axios.get(this.$httpUrl + '/goods/analysis').then(res=>{
        this.goodsCountDate = res.data;
      })

      //仓库库存
      this.$axios.get(this.$httpUrl + '/storage/analysis').then(res=>{
        this.storageDate = res.data;
      })
    }
  },
  computed: {
    inOrOutOption() {
      return {
        // Make gradient line here
        visualMap: [
          {
            show: false,
            type: 'continuous',
            seriesIndex: 0,
            min: 0,
            max: 1000
          },
          {
            show: false,
            type: 'continuous',
            seriesIndex: 1,
            dimension: 1,
            min: 0,
            max: 1000
          }
        ],
        title: [
          {
            bottom: '50%',
            left: 'center',
            text: '入库月报'
          },
          {
            bottom: '1%',
            left: 'center',
            text: '出库月报'
          }
        ],
        tooltip: {
          trigger: 'axis'
        },
        xAxis: [
          {
            data: this.inDate.map(d => d.date)
          },
          {
            data: this.outDate.map(d => d.date),
            gridIndex: 1
          }
        ],
        yAxis: [
          {},
          {
            gridIndex: 1
          }
        ],
        grid: [
          {
            bottom: '60%'
          },
          {
            top: '60%'
          }
        ],
        series: [
          {
            type: 'bar',
            showSymbol: false,
            data: this.inDate.map(d => d.count)
          },
          {
            type: 'bar',
            showSymbol: false,
            data: this.outDate.map(d => d.count),
            xAxisIndex: 1,
            yAxisIndex: 1
          }
        ]
      }
    },
    goodsCountOption(){
      return{
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '1%',
          left: 'center'
        },
        title:{
          bottom: '1%',
          left: 'center',
          text: '物品分类库存'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 15,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 15,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.goodsCountDate
          }
        ]
      }
    },
    storageOption(){
      return{
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '1%',
          left: 'center'
        },
        title:{
          bottom: '1%',
          left: 'center',
          text: '仓库在库数'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 15,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 15,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.storageDate
          }
        ]
      }
    }
  },
  created() {
    this.getDate()
  }
}
</script>

<style lang="css" scoped>
.inOrOutAnalysis {
  height: 600px;
  width: 800px;
  float: left;
}
.goodsCountAnalysis{
  float: left;
  width: 500px;
  height: 280px;
}
.storageAnalysis{
  float: left;
  width: 500px;
  height: 280px;
}
.bu{
  float: right;
  margin-right: 50px;
}
</style>
