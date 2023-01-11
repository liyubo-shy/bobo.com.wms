<template>
  <div id="app">
    <el-breadcrumb separator="/" style="font-size: medium">
      <el-breadcrumb-item :to="{ path: '/Analysis' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>报表</el-breadcrumb-item>
    </el-breadcrumb>
    <e-charts class="inOrOutAnalysis" :options="option"></e-charts>
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


    }
  },
  methods: {
    getDate() {
      this.$axios.get(this.$httpUrl + '/record/recordInAnalysis').then(res => {
        this.inDate = res.data;
      })
      this.$axios.get(this.$httpUrl + '/record/recordOutAnalysis').then(res => {
        this.outDate = res.data;
      })
    }
  },
  computed: {
    option() {
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
            left: 'center',
            text: '入库月报'
          },
          {
            top: '55%',
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
  width: 1000px;
}
</style>
