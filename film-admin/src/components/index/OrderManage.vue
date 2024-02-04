<template>
    <div>
        <el-card class="user-card">
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getOrderList">
                        <el-button slot="append" icon="el-icon-search" @click="getOrderList"></el-button>
                    </el-input>
                </el-col>
            </el-row>
            <el-table :data="orderList" border stripe>
                <el-table-column type="index" align="center" width="40px"></el-table-column>
                <el-table-column label="用户名" width="120" show-overflow-tooltip prop="user_name" align="center"></el-table-column>
                <el-table-column label="电影名" show-overflow-tooltip prop="movie_name" align="center"></el-table-column>
                <el-table-column label="影院名" show-overflow-tooltip prop="cinema_name" align="center"></el-table-column>
                <el-table-column label="票数" align="center" prop="num"></el-table-column>
                <el-table-column label="总价" width="100" align="center" prop="price"></el-table-column>
                <el-table-column label="取票码" align="center" prop="phone_code"></el-table-column>
                <el-table-column label="下单时间" show-overflow-tooltip prop="order_date" align="center"></el-table-column>
                <el-table-column label="操作" align="center">
                    <template v-slot="scope">
                        <el-tooltip effect="dark" content="删除订单信息"
                                    placement="top" :enterable="false">
                            <el-button type="danger"
                                       icon="el-icon-delete"
                                       circle size="mini"
                                       @click="deleteOrderById(scope.row.id)"></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="queryInfo.pageNum"
                    :page-sizes="[5, 10, 20]"
                    :page-size="queryInfo.pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </el-card>

    </div>
</template>

<script>
import {findOrder, deleteOrder} from "../../api";

export default {
        data() {
            return {
                queryInfo: {
                    query: '',
                    pageNum: 1,
                    pageSize: 5
                },
                orderList: [
                ],
                total: 0,
            }
        },
        created() {
            this.getOrderList()
        },
        methods: {
            getOrderList() {
                findOrder(this.queryInfo).then(res => {
                    if (res.data.code === 1) {
                        this.orderList = res.data.lists.records
                        this.total = res.data.lists.total
                    } else {
                        this.$message.error(res.data.msg)
                    }
                }).catch(err => {
                    console.log(err)
                })
            },
            handleSizeChange(size) {
                this.queryInfo.pageSize = size
                this.getOrderList()
            },
            handleCurrentChange(page) {
                this.queryInfo.pageNum = page
                this.getOrderList()
            },
            async deleteOrderById(id) {
                const confirmResult = await this.$confirm('是否删除此订单?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).catch(err => {
                    return err
                })
                // console.log(confirmResult)
                if (confirmResult !== 'confirm') {
                    return this.$message.info('取消删除')
                } else {
                    deleteOrder({id:id}).then(res=>{
                        if (res.data.code === 1) {
                            this.$message.success(res.data.msg)
                        }
                        else{
                            this.$message.error(res.data.msg)
                        }
                        this.getOrderList()
                    }).catch(err=>{
                        console.log(err)
                    })
                }
            }
        }
    }
</script>

<style scoped>

</style>
