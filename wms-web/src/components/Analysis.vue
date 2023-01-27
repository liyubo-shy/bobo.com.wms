<template>
  <div id="app">
    <div>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="物流信息统计" name="first">
          <div>
            <e-charts class="inOrOutAnalysis" ref="chart" :options="inOrOutOption"></e-charts>
            <div class="bu">
              <el-button icon="el-icon-refresh" @click="getDate()" type="primary" style="float: right;"></el-button>
            </div>
            <e-charts class="goodsCountAnalysis" :options="goodsCountOption"></e-charts>
            <e-charts class="storageAnalysis" :options="storageOption"></e-charts>
          </div>
        </el-tab-pane>
        <el-tab-pane label="用户信息统计" name="second">
          <div class="bu">
            <el-button icon="el-icon-refresh" @click="getDate()" type="primary" style="float: right;"></el-button>
          </div>
          <div style="width: 600px;height: 50px;clear: left"></div>
          <div style=" width: 1320px; height:500px">
            <e-charts class="userInfoAnalysis" :options="userInfoOption"></e-charts>
            <e-charts class="userAgeAnalysis" :options="userAgeOption"></e-charts>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
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
      goodsCountDate: [],
      storageDate: [],
      femaleCountDate: [],
      maleCountDate: [],
      userAgeDate: [],

      activeName: 'first',

      myChart:null
    }
  },
  mounted(){
    this.myChart = this.$echarts.init(this.$refs.chart);
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
      this.$axios.get(this.$httpUrl + '/goods/analysis').then(res => {
        this.goodsCountDate = res.data;
      })

      //仓库库存
      this.$axios.get(this.$httpUrl + '/storage/analysis').then(res => {
        this.storageDate = res.data;
      })

      //用户信息
      this.$axios.get(this.$httpUrl + '/user/analysisMale').then(res => {
        this.maleCountDate = res.data
      })
      this.$axios.get(this.$httpUrl + '/user/analysisFemale').then(res => {
        this.femaleCountDate = res.data
      })

      //用户年龄
      this.$axios.get(this.$httpUrl + '/user/userAgeAnalysis').then(res => {
        this.userAgeDate = res.data
      })

    },
    handleClick(tab, event) {
      console.log(tab, event);
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
        ],
      };
    },

    goodsCountOption() {
      return {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '1%',
          left: 'center'
        },
        title: {
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
    storageOption() {
      return {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '1%',
          left: 'center'
        },
        title: {
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
    },
    userInfoOption() {
      return {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // Use axis to trigger tooltip
            type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          containLabel: true
        },
        title: {
          bottom: '1%',
          left: 'center',
          text: '角色性别分布'
        },
        xAxis: {
          type: 'category',
          data: ['超级管理员', '管理员', '用户']
        },
        yAxis: {
          type: 'value',
          name: '人数'
        },
        series: [
          {
            name: '女',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.femaleCountDate
          },
          {
            name: '男',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.maleCountDate
          }
        ]
      }
    },
    userAgeOption() {
      return {
        visualMap: [
          {
            show: false,
            type: 'continuous',
            seriesIndex: 0,
            min: 0,
            max: 6
          },
        ],
        title: [
          {
            bottom: '1',
            left: 'center',
            text: '用户年龄分布'
          },

        ],
        tooltip: {
          trigger: 'axis'
        },
        xAxis:
          {
            name:'岁',
            data: this.userAgeDate.map(d => d.age)
          },


        yAxis: [
          {},

        ],
        grid: [
          {
            bottom: '15%'
          },

        ],
        series: [
          {
            type: 'bar',
            showSymbol: false,
            data: this.userAgeDate.map(d => d.count)
          },

        ]
      }
    },
  },

  created() {
    this.getDate()
  }
}
</script>

<style lang="css" scoped>
.inOrOutAnalysis {
  height: 580px;
  width: 800px;
  float: left;
}

.goodsCountAnalysis {
  float: left;
  width: 500px;
  height: 280px;
}

.storageAnalysis {
  float: left;
  width: 500px;
  height: 280px;
}

.userInfoAnalysis {
  /*border: #430400 1px solid;*/
  float: left;
  width: 660px;

}

.userAgeAnalysis {
  float: left;
  margin-left: 50px;
  width: 600px;
  height: 400px;
  /*width: 650px;*/

}

.bu {
  float: right;
  margin-right: 50px;
}

* {
  /*border: #42b983 1px solid;*/
}
</style>

