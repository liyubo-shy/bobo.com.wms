/*
 * @Descripttion:
 * @version:
 * @Author:
 * @Date: 2022-03-06 11:23:28
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-03-14 13:50:38
 */
const state = {
    isShow: false, // 默认不显示
    tableList: [], // 表格数据
    infoData: null, // 表格当前行数据
    cityCode: '110099', //城市的行政编码
    resourceTab: "fileM",//当前选中的tab资源管理
    kindCode: '', // 种别代码
    foodOptions: '', // 风味列表
};

// getters计算属性
const getters = {
    getDemoValue: state => state.cityCode
};

// actions异步请求处理
const actions = {};

// mutations主要用来操作state
const mutations = {
    setIsShow (state, isShow) { // 布尔值 true
        state.isShow = isShow;
    },
    CHANGE_KINDCODE (state, kindCode) { // 字符串 ''
        state.kindCode = kindCode;
    },
    setSelectedData (state, data) { // 数组 []
        state.tableList = data;
    },
    setInfoData (state, infoData) { // null
        state.infoData = infoData;
    },
    setCityCode (state, cityCode) {  // 固定值
        state.cityCode = cityCode
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}
