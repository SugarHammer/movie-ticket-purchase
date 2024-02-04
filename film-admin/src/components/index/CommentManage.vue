<template>
    <div>
        <el-card class="user-card">
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getCommentList">
                        <el-button slot="append" icon="el-icon-search" @click="getCommentList"></el-button>
                    </el-input>
                </el-col>
            </el-row>
            <el-table :data="commentList" border stripe>
                <el-table-column type="index" align="center" width="40px"></el-table-column>
                <el-table-column label="用户名" width="120" show-overflow-tooltip prop="user_name" align="center"></el-table-column>
                <el-table-column label="电影名" show-overflow-tooltip prop="movie_name" align="center"></el-table-column>
                <el-table-column label="评论时间" show-overflow-tooltip prop="comment_date" align="center"></el-table-column>
                <el-table-column label="用户评分" width="80" align="center" prop="user_score"></el-table-column>
                <el-table-column label="评论内容" show-overflow-tooltip prop="content" align="center"></el-table-column>
                <el-table-column label="操作" align="center">
                    <template v-slot="scope">
                        <el-tooltip effect="dark" content="删除评论"
                                    placement="top" :enterable="false">
                            <el-button type="danger"
                                       icon="el-icon-delete"
                                       circle size="mini"
                                       @click="deleteCommentById(scope.row.id)"></el-button>
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
import {findComment, deleteComment} from "../../api";
import moment from 'moment'
export default {
    data() {

        return {
            queryInfo: {
                query: '',
                pageNum: 1,
                pageSize: 5
            },
            commentList: [],
            total: 0,
        }
    },
    created() {
        this.getCommentList()
    },
    methods: {
        getCommentList() {
            findComment(this.queryInfo).then(res => {
                if (res.data.code === 1) {
                    this.commentList = res.data.lists.records
                    this.total = res.data.lists.total
                    //格式化日期
                    this.commentList.forEach(value => {
                        console.log(value)
                        value.comment_date = moment(value.comment_date).format('YYYY-MM-DD HH:mm:ss')
                    })
                } else {
                    this.$message.error(res.data.msg)
                }
            }).catch(err => {
                console.log(err)
            })
        },
        handleSizeChange(size) {
            this.queryInfo.pageSize = size
            this.getCommentList()
        },
        handleCurrentChange(page) {
            this.queryInfo.pageNum = page
            this.getCommentList()
        },
        async deleteCommentById(id) {
            const confirmResult = await this.$confirm('是否删除此评论?', '提示', {
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
                deleteComment({id:id}).then(res=>{
                    if (res.data.code === 1) {
                        this.$message.success(res.data.msg)
                    }
                    else{
                        this.$message.error(res.data.msg)
                    }
                    this.getCommentList()
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
