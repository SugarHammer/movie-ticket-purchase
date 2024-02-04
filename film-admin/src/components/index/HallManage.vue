<template>
    <div>
        <el-card class="user-card">
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getHallList">
                        <el-button slot="append" icon="el-icon-search" @click="getHallList"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" round @click="addDialogVisible = true">添加影厅</el-button>
                </el-col>
            </el-row>
            <el-table :data="hallList" border stripe>
                <el-table-column type="index" align="center" width="40px"></el-table-column>
                <el-table-column label="影院名" show-overflow-tooltip prop="cinema_name" align="center"></el-table-column>
                <el-table-column label="影院电话" align="center"  prop="cinema_phone"></el-table-column>
                <el-table-column label="影厅名" show-overflow-tooltip prop="name" align="center" ></el-table-column>
                <el-table-column label="操作" align="center" width="120">
                    <template v-slot="scope">
                        <el-tooltip effect="dark" content="修改影厅信息" placement="top" :enterable="false">
                            <el-button type="primary"
                                       icon="el-icon-edit"
                                       circle size="mini"
                                       @click="showEditDialog(scope.row.id)"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除用户"
                                    placement="top" :enterable="false">
                            <el-button type="danger"
                                       icon="el-icon-delete"
                                       circle size="mini"
                                       @click="deleteHallById(scope.row.id)"></el-button>
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
        <el-dialog
            title="添加影厅"
            :visible.sync="addDialogVisible"
            width="60%"
            @close="addDialogClosed">
            <el-form label-position="right" :rules="addFormRules" label-width="80px" :model="addForm"
                     ref="addFormRef">
                <el-form-item label="影院名" prop="cinemaId">
                    <el-col :span="12">
                        <el-select v-model="addForm.cinemaId" placeholder="请选择" style="width: 100%">
                            <el-option v-for="item in cinemaList" :key="item.id" :label="item.name"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="影厅名" prop="name">
                    <el-col :span="12">
                        <el-input v-model="addForm.name"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addHall">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
            title="修改影厅信息"
            :visible.sync="editDialogVisible"
            width="60%">
            <el-form label-position="right" :rules="editFormRules" label-width="80px" :model="editForm">
                <el-form-item label="影院名" prop="cinemaId">
                    <el-col :span="12">
                        <el-select v-model="editForm.cinemaId" placeholder="请选择" style="width: 100%">
                            <el-option v-for="item in cinemaList" :key="item.id" :label="item.name"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="影厅名" prop="name">
                    <el-col :span="12">
                        <el-input v-model="editForm.name"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editHall">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {findHall, insertHall, updateHall, deleteHall, findOneHall, getCinemaList} from "../../api";

export default {
    data() {
        return {
            queryInfo: {
                query: '',
                pageNum: 1,
                pageSize: 5
            },
            hallList: [],
            total: 0,
            addDialogVisible: false,
            addForm: {
                cinemaId:'',
                name:'',
            },
            addFormRules: {
                cinemaId: [
                    {required: true, message: '影院名不能为空', trigger: 'change'},
                ],
                name: [
                    {required: true, message: '影厅名不能为空', trigger: 'blur'},
                ],
            },
            cinemaList: [],
            editDialogVisible: false,
            editForm: {},
            editFormRules: {
                cinemaId: [
                    {required: true, message: '影院名不能为空', trigger: 'change'},
                ],
                name: [
                    {required: true, message: '影厅名不能为空', trigger: 'blur'},
                ],
            },
        }
    },
    created() {
        this.getHallList()
        this.getCinemaList()
    },
    methods: {
        getCinemaList() {
            getCinemaList().then(res=>{
                if (res.data.code === 1){
                    this.cinemaList = res.data.cinemaList
                }else{
                    this.$message.error(res.data.msg)
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        getHallList() {
            findHall(this.queryInfo).then(res => {
                if (res.data.code === 1) {
                    this.hallList = res.data.lists.records
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
            this.getHallList()
        },
        handleCurrentChange(page) {
            this.queryInfo.pageNum = page
            this.getHallList()
        },
        addDialogClosed() {
            this.$refs.addFormRef.resetFields()
        },
        addHall() {
            insertHall(this.addForm).then(res => {
                if (res.data.code === 1) {
                    this.$message.success(res.data.msg)
                } else {
                    this.$message.error(res.data.msg)
                }
                this.addDialogVisible = false
                this.getHallList()
            }).catch(err => {
                console.log(err)
            })
        },
        showEditDialog(id) {
            this.editDialogVisible = true
            findOneHall({id:id}).then(res=>{
                if (res.data.code === 1){
                    this.editForm = res.data.hall
                } else{
                    this.$message.error(res.data.msg)
                    this.editDialogVisible = false
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        editHall() {
            // console.log(this.editForm)
            updateHall(this.editForm).then(res => {
                if (res.data.code === 1) {
                    this.$message.success(res.data.msg)
                } else {
                    this.$message.error(res.data.msg)
                }
                this.editDialogVisible = false
                this.getHallList()
            }).catch(err => {
                console.log(err)
            })
        },
        async deleteHallById(id) {
            const confirmResult = await this.$confirm('是否删除此影厅?', '提示', {
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
                deleteHall({id: id}).then(res => {
                    if (res.data.code === 1) {
                        this.$message.success(res.data.msg)
                    } else {
                        this.$message.error(res.data.msg)
                    }
                    this.getHallList()
                }).catch(err => {
                    console.log(err)
                })
            }
        }
    }
}
</script>

<style scoped>

</style>
