<template>
    <div>
        <el-card class="user-card">
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getCinemaList">
                        <el-button slot="append" icon="el-icon-search" @click="getCinemaList"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" round @click="addDialogVisible = true">添加影院</el-button>
                </el-col>
            </el-row>
            <el-table :data="cinemaList" border stripe>
                <el-table-column type="index" align="center" width="40px"></el-table-column>
                <el-table-column label="影院名" show-overflow-tooltip prop="name" align="center"></el-table-column>
                <el-table-column label="联系电话" align="center" width="140" prop="phone"></el-table-column>
                <el-table-column label="详细地址" show-overflow-tooltip prop="specifiedAddress"
                                 align="center"></el-table-column>
                <el-table-column label="操作" align="center" width="120">
                    <template v-slot="scope">
                        <el-tooltip effect="dark" content="修改影院信息" placement="top" :enterable="false">
                            <el-button type="primary" icon="el-icon-edit" circle size="mini"
                                       @click="showEditDialog(scope.row.id)"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除影院" placement="top" :enterable="false">
                            <el-button type="danger" icon="el-icon-delete" circle size="mini"
                                       @click="deleteCinemaById(scope.row.id)"></el-button>
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
        <el-dialog title="添加影院" :visible.sync="addDialogVisible" width="40%" @close="addDialogClosed">
            <el-form label-position="right" :rules="addFormRules" label-width="80px" :model="addForm"
                     ref="addFormRef">
                <el-form-item label="影院名" prop="name">
                    <el-col :span="18">
                        <el-input v-model="addForm.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="省/市/区" prop="address">
                    <el-cascader v-model="value" :options="options" :props="{ expandTrigger: 'hover' }"
                                 clearable @change="handleChange"></el-cascader>
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                    <el-col :span="18">
                        <el-input v-model="addForm.phone"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="地址" prop="specifiedAddress">
                    <el-col :span="18">
                        <el-input v-model="addForm.specifiedAddress" type="textarea"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addCinema">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="修改影院信息" :visible.sync="editDialogVisible" width="40%">
            <el-form label-position="right" label-width="80px" :model="editForm" :rules="editRules">
                <el-form-item label="影院名" prop="name">
                    <el-col :span="18">
                        <el-input v-model="editForm.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                    <el-col :span="18">
                        <el-input v-model="editForm.phone"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="地址" prop="specifiedAddress">
                    <el-col :span="18">
                        <el-input v-model="editForm.specifiedAddress" type="textarea"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editCinema">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {findCinema, insertCinema, updateCinema, deleteCinema, findOneCinema} from "../../api";

export default {
    data() {
        var checkPhone = (rule, value, cb) => {
            const regTel = /(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^[0−9]3,4[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/
            if (regTel.test(value)) {
                return cb()
            }

            cb(new Error('手机号码不合法'))
        }
        return {
            queryInfo: {
                query: '',
                pageNum: 1,
                pageSize: 5
            },
            cinemaList: [],
            total: 0,
            addDialogVisible: false,
            addForm: {
                name: '',
                phone: '',
                specifiedAddress: '',
                city:'',
                county:'',
                province:''
            },
            addFormRules: {
                name: [
                    {required: true, message: '请输入影院名称', trigger: 'blur'},
                ],
                phone: [
                    {required: true, message: '请输入手机号码', trigger: 'blur'},
                    {validator: checkPhone, trigger: 'blur'}
                ],
                specifiedAddress: [
                    {required: true, message: '请输入影院详细地址', trigger: 'blur'},
                ],
                address: [
                    {required: true, message: '请选择影院地址', trigger: 'blur'}
                ]
            },
            editDialogVisible: false,
            editForm: {},
            editRules: {
                name: [
                    {required: true, message: '请输入影院名称', trigger: 'blur'},
                ],
                phone: [
                    {required: true, message: '请输入手机号码', trigger: 'blur'},
                    {validator: checkPhone, trigger: 'blur'}
                ],
                specifiedAddress: [
                    {required: true, message: '请输入影院详细地址', trigger: 'blur'},
                ]
            },
            value:[],
            options:[{
                value: '广东省',
                label: '广东省',
                children: [{
                    value: '深圳市',
                    label: '深圳市',
                    children: [{
                        value: '南山区',
                        label: '南山区'
                    }],
                }]
            }]
        }
    },
    created() {
        this.getCinemaList()
    },
    methods: {
        getCinemaList() {
            findCinema(this.queryInfo).then(res => {
                // console.log(res)
                if (res.data.code === 1) {
                    this.cinemaList = res.data.lists.records
                    this.total = res.data.lists.total
                } else{
                    this.$message.error(res.data.msg)
                }
            }).catch(err => {
                console.log(err)
            })
        },
        handleSizeChange(size) {
            this.queryInfo.pageSize = size
            this.getCinemaList()
        },
        handleCurrentChange(page) {
            this.queryInfo.pageNum = page
            this.getCinemaList()
        },
        handleChange(value){
            // console.log(value)
            this.addForm.province = value[0]
            this.addForm.city = value[1]
            this.addForm.county = value[2]
            // console.log(this.addForm)
        },
        addDialogClosed() {
            this.$refs.addFormRef.resetFields()
            this.value = ''
            this.addForm.province = ''
            this.addForm.city = ''
            this.addForm.county = ''
        },
        addCinema() {
            insertCinema(this.addForm).then(res => {
                if (res.data.code === 1) {
                    this.$message.success(res.data.msg)
                } else {
                    this.$message.error(res.data.msg)
                }
                this.addDialogVisible = false
                this.getCinemaList()
            }).catch(err => {
                console.log(err)
            })
        },
        showEditDialog(id) {
            this.editDialogVisible = true
            findOneCinema({id:id}).then(res=>{
                if (res.data.code === 1){
                    this.editForm = res.data.cinema
                } else{
                    this.$message.error(res.data.msg)
                    this.editDialogVisible = false
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        editCinema() {
            updateCinema(this.editForm).then(res => {
                if (res.data.code === 1) {
                    this.$message.success(res.data.msg)
                } else {
                    this.$message.error(res.data.msg)
                }
                this.editDialogVisible = false
                this.getCinemaList()
            }).catch(err => {
                console.log(err)
            })
        },
        async deleteCinemaById(id) {
            const confirmResult = await this.$confirm('是否删除此影院?', '提示', {
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
                deleteCinema({id:id}).then(res=>{
                    if (res.data.code === 1) {
                        this.$message.success(res.data.msg)
                    }
                    else{
                        this.$message.error(res.data.msg)
                    }
                    this.getCinemaList()
                }).catch(err=>{
                    console.log(err)
                })
            }
        },
    }
}
</script>

<style scoped>

</style>
