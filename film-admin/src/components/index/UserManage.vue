<template>
    <div>
        <el-card class="user-card">
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getUserList">
                        <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" round @click="addDialogVisible = true">添加用户</el-button>
                </el-col>
            </el-row>
            <el-table :data="userList" border stripe>
                <el-table-column type="index" align="center" width="40px"></el-table-column>
                <el-table-column label="头像" prop="avatar" align="center" width="120px">
                    <template slot-scope="scope">
                        <img :src="getAvatar(scope.row.avatar)" alt="" style="width: 80px;"/>
                        <el-upload
                            class="upload-demo"
                            :action="uploadAvatar(scope.row.id)"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload">
                            <el-button size="mini">更新图片</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column label="账号" prop="username" align="center" width="120px"></el-table-column>
                <el-table-column label="用户名" prop="name" align="center" width="120px"></el-table-column>
                <el-table-column label="性别" prop="sex" align="center" width="60px"></el-table-column>
                <el-table-column label="生日" prop="birth" align="center" width="120px"></el-table-column>
                <el-table-column label="个性签名" align="center">
                    <template v-slot="scope">
                        <p style="height: 100px; overflow: scroll">{{ scope.row.sign }}</p>
                    </template>
                </el-table-column>
                <el-table-column label="电话" prop="phone" align="center" width="120px"></el-table-column>
                <el-table-column label="操作" align="center" width="100px">
                    <template v-slot="scope">
                        <el-tooltip effect="dark" content="修改用户信息" placement="top" :enterable="false">
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
                                       @click="deleteUserById(scope.row.id)"></el-button>
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
        <!--添加用户-->
        <el-dialog
            title="添加用户"
            :visible.sync="addDialogVisible"
            width="40%"
            @close="addDialogClosed">
            <!--            内容主体-->
            <el-form label-position="right" :rules="addFormRules" label-width="80px" :model="addForm"
                     ref="addFormRef">
                <el-form-item label="账号" prop="username">
                    <el-col :span="16">
                        <el-input v-model="addForm.username"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="用户名" prop="name">
                    <el-col :span="16">
                        <el-input v-model="addForm.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="手机" prop="phone">
                    <el-col :span="16">
                        <el-input v-model="addForm.phone"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-col :span="16">
                        <el-input v-model="addForm.password" show-password></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="性别">
                    <el-col :span="16">
                        <el-select v-model="addForm.sex" placeholder="请选择性别" style="width: 100%">
                            <el-option label="男" value="男"></el-option>
                            <el-option label="女" value="女"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="生日">
                    <el-col :span="16">
                        <el-date-picker type="date" placeholder="选择日期" v-model="addForm.birth" value-format="yyyy-MM-dd"
                                        :picker-options="pickerDay" style="width: 100%;"></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="个人签名">
                    <el-col :span="16">
                        <el-input type="textarea" v-model="addForm.sign"
                                  :autosize="{ minRows: 2, maxRows: 4}"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addUser">确 定</el-button>
            </span>
        </el-dialog>
        <!--修改用户-->
        <el-dialog
            title="修改用户信息"
            :visible.sync="editDialogVisible"
            width="50%">
            <el-form :model="editForm" :rules="editFormRules"
                     ref="editFormRef" label-width="90px"
                     @close="editDialogClosed">
                <el-form-item label="账号" prop="username">
                    <el-col :span="16">
                        <el-input v-model="editForm.username" disabled></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="用户名" prop="name">
                    <el-col :span="16">
                        <el-input v-model="editForm.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="手机" prop="phone">
                    <el-col :span="16">
                        <el-input v-model="editForm.phone"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-col :span="16">
                        <el-input v-model="editForm.password" show-password></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="性别">
                    <el-col :span="16">
                        <el-select v-model="editForm.sex" placeholder="请选择性别" style="width: 100%">
                            <el-option label="男" value="男"></el-option>
                            <el-option label="女" value="女"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="生日">
                    <el-col :span="16">
                        <el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth" value-format="yyyy-MM-dd"
                                        :picker-options="pickerDay" style="width: 100%;"></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="个人签名">
                    <el-col :span="16">
                        <el-input type="textarea" v-model="editForm.sign"
                                  :autosize="{ minRows: 2, maxRows: 4}"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
    <el-button @click="editDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="editUserInfo">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
import {deleteUser, findUser, insertUser, updateUser,findOneUser} from "../../api";

export default {
    data() {
        // 自定义校验规则
        var checkPhone = (rule, value, cb) => {
            const regTel = /^1[3|4|5|6|7|8][0-9]{9}$/
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
            userList: [],
            total: 0,
            //添加用户
            addDialogVisible: false,
            addForm: {
                username: '',
                name: '',
                password: '',
                phone: '',
                birth: '',
                sex: ''
            },
            addFormRules: {
                username: [
                    {required: true, message: '请输入账号', trigger: 'blur'},
                    {min: 3, max: 10, message: '长度在3-10个字符之间', trigger: 'blur'}
                ],
                name: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                    {min: 0, max: 10, message: '长度在0-10个字符之间', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 6, max: 12, message: '长度在6-12个字符之间', trigger: 'blur'}
                ],
                phone: [
                    {required: true, message: '请输入手机号码', trigger: 'blur'},
                    {validator: checkPhone, trigger: 'blur'}
                ]
            },
            pickerDay: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                }
            },
            //修改用户
            editDialogVisible: false,
            editForm: {},
            editFormRules: {
                name: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                    {min: 0, max: 10, message: '长度在0-10个字符之间', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 6, max: 12, message: '长度在6-12个字符之间', trigger: 'blur'}
                ],
                phone: [
                    {required: true, message: '请输入手机号码', trigger: 'blur'},
                    {validator: checkPhone, trigger: 'blur'}
                ]
            }
        }
    },
    created() {
        this.getUserList()
    },
    methods: {
        getUserList() {
            findUser(this.queryInfo).then(res => {
                // console.log(res)
                if (res.data.code === 1) {
                    this.userList = res.data.lists.records
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
            this.getUserList()
        },
        handleCurrentChange(page) {
            this.queryInfo.pageNum = page
            this.getUserList()
        },
        addDialogClosed() {
            this.$refs.addFormRef.resetFields()
        },
        getAvatar(path){
            return 'http://localhost:9090' + path
        },
        uploadAvatar(id) {
            return `http://localhost:9090/updateAvatar?id=${id}`
        },
        addUser() {
            // console.log(this.addForm)
            insertUser(this.addForm).then(res => {
                if (res.data.code === 1) {
                    this.$message.success(res.data.msg)
                } else {
                    this.$message.error(res.data.msg)
                }
                this.addDialogVisible = false
                this.getUserList()
            }).catch(err => {
                console.log(err)
            })
        },
        showEditDialog(id) {
            this.editDialogVisible = true
            findOneUser({id:id}).then(res=>{
                if (res.data.code === 1){
                    this.editForm = res.data.user
                } else{
                    this.$message.error(res.data.msg)
                    this.editDialogVisible = false
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        editDialogClosed() {
            this.$refs.editFormRef.resetFields()
        },
        editUserInfo() {
            updateUser(this.editForm).then(res => {
                if (res.data.code === 1){
                    this.$message.success(res.data.msg)
                } else{
                    this.$message.error(res.data.msg)
                }
                this.editDialogVisible = false
                this.getUserList()
            }).catch(err => {
                console.log(err)
            })
        },
        async deleteUserById(id) {
            const confirmResult = await this.$confirm('是否删除此用户?', '提示', {
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
                deleteUser({id:id}).then(res => {
                    if (res.data.code === 1){
                        this.$message.success(res.data.msg)
                    } else{
                        this.$message.error(res.data.msg)
                    }
                    this.getUserList()
                }).catch(err => {
                    console.log(err)
                })
            }
        },
        handleAvatarSuccess(res, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
            this.getUserList()
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        }
    }
}
</script>

<style scoped>

</style>
