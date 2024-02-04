<template>
    <div>
        <el-row :gutter="20" class="mgb20">
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{padding: '0px'}">
                    <div class="grid-content grid-con-1">
                        <div class="grid-cont-right">
                            <div class="grid-num">{{ userCount }}</div>
                            <div>用户总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{padding: '0px'}">
                    <div class="grid-content grid-con-2">
                        <div class="grid-cont-right">
                            <div class="grid-num">{{ cinemaCount }}</div>
                            <div>影院总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{padding: '0px'}">
                    <div class="grid-content grid-con-3">
                        <div class="grid-cont-right">
                            <div class="grid-num">{{ movieCount }}</div>
                            <div>影片总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{padding: '0px'}">
                    <div class="grid-content grid-con-4">
                        <div class="grid-cont-right">
                            <div class="grid-num">{{ orderCount }}</div>
                            <div>订单数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <h3 style="margin-bottom: 20px">用户性别比例</h3>
                <div style="background-color: white">
                    <ve-pie :data="userSex" :theme="options"></ve-pie>
                </div>
            </el-col>
            <el-col :span="12">
                <h3 style="margin-bottom: 20px">电影类型分布</h3>
                <div style="background-color: white">
                    <ve-histogram :data="movieStyle" :theme="options2"></ve-histogram>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import {getUserList, getMovieList, getCinemaList, getOrderList} from "../../api";

export default {
    name: "Database",
    data() {
        return {
            userCount: 0,
            cinemaCount: 0,
            movieCount: 0,
            orderCount: 0,
            user: [],
            userSex: {
                columns: ['性别', '总数'],
                rows: [
                    {'性别': '男', '总数': 0},
                    {'性别': '女', '总数': 0},
                    {'性别': '保密', '总数': 0}
                ]
            },
            movieStyle: {
                columns: ['类型', '总数'],
                rows: [
                    {'类型': '动漫', '总数': 0},
                    {'类型': '科幻', '总数': 0},
                    {'类型': '喜剧', '总数': 0},
                    {'类型': '言情', '总数': 0},
                    {'类型': '剧情', '总数': 0},
                    {'类型': '动作', '总数': 0},
                    {'类型': '青春', '总数': 0},
                    {'类型': '悬疑恐怖', '总数': 0},
                    {'类型': '其他', '总数': 0}
                ]
            },
            options: {
                color: ['#87CEFA', '#FFC0CB','#0eefe0']
            },
            options2: {
                color: ['#FD8A61'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: { // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                }
            }
        }
    },
    methods: {
        getUser() {
            getUserList().then(res => {
                if (res.data.code === 1){
                    this.userCount = res.data.userList.length
                    this.userSex.rows[0]['总数'] = this.setSex('男', res.data.userList)
                    this.userSex.rows[1]['总数'] = this.setSex('女', res.data.userList)
                    this.userSex.rows[2]['总数'] = this.userCount - this.userSex.rows[0]['总数'] - this.userSex.rows[1]['总数']
                }
            }).catch(err => {
                console.log(err)
            })
        },
        getMovie() {
            getMovieList().then(res => {
                if (res.data.code === 1){
                    this.movieCount = res.data.movieList.length
                    for (let item of res.data.movieList) {
                        this.getStyle(item.type)
                    }
                }
            }).catch(err => {
                console.log(err)
            })
        },
        getCinema() {
            getCinemaList().then(res => {
                if (res.data.code === 1){
                    this.cinemaCount = res.data.cinemaList.length
                }
            }).catch(err => {
                console.log(err)
            })
        },
        getOrder() {
            getOrderList().then(res => {
                if (res.data.code === 1){
                    this.orderCount = res.data.orderList.length
                }
            }).catch(err => {
                console.log(err)
            })
        },
        setSex (sex, arr) {
            let count = 0
            for (let item of arr) {
                if (sex === item.sex) {
                    count++
                }
            }
            return count
        },
        getStyle (val) {
            for (let item of this.movieStyle.rows) {
                if (val.includes(item['类型'])) {
                    item['总数']++
                }
            }
        }
    },
    mounted() {
        this.getUser()
        this.getCinema()
        this.getMovie()
        this.getOrder()
    }
}
</script>

<style scoped>
.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}
</style>