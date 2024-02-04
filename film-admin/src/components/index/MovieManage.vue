<template>
    <div>
        <el-card class="user-card">
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getMovieList">
                        <el-button slot="append" icon="el-icon-search" @click="getMovieList"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" round @click="addDialogVisible = true">添加电影</el-button>
                </el-col>
            </el-row>
            <el-table :data="movieList" border stripe>
                <el-table-column type="index" align="center" width="40px"></el-table-column>
                <el-table-column label="海报" prop="poster" align="center" width="120px">
                    <template slot-scope="scope">
                        <img :src="getPoster(scope.row.poster)" alt="" style="width: 80px;"/>
                        <el-upload
                            class="upload-demo"
                            :action="uploadPoster(scope.row.id)"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload">
                            <el-button size="mini">更新海报</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column label="电影名" show-overflow-tooltip prop="name" align="center" width="140px"></el-table-column>
                <el-table-column label="导演" align="center" show-overflow-tooltip prop="director" width="140px"></el-table-column>
                <el-table-column label="类型" align="center" width="100" prop="type"></el-table-column>
                <el-table-column label="上映时间" prop="publicDate" width="120px" align="center"></el-table-column>
                <el-table-column label="简介" align="center">
                    <template v-slot="scope">
                        <p style="height: 100px; overflow: scroll">{{ scope.row.intro }}</p>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="120px">
                    <template v-slot="scope">
                        <el-tooltip effect="dark" content="修改电影信息" placement="top" :enterable="false">
                            <el-button type="primary"
                                       icon="el-icon-edit"
                                       circle size="mini"
                                       @click="showEditDialog(scope.row.id)"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除电影"
                                    placement="top" :enterable="false">
                            <el-button type="danger"
                                       icon="el-icon-delete"
                                       circle size="mini"
                                       @click="deleteMovieById(scope.row.id)"></el-button>
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
            title="添加电影"
            :visible.sync="addDialogVisible"
            width="40%"
            @close="addDialogClosed">
            <el-form label-position="right" :rules="addFormRules" label-width="80px" :model="addForm"
                     ref="addFormRef">
                <el-form-item label="电影名" prop="name">
                    <el-col :span="16">
                        <el-input v-model="addForm.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="导演" prop="director">
                    <el-col :span="16">
                        <el-input v-model="addForm.director"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="演员" prop="actor">
                    <el-col :span="16">
                        <el-input v-model="addForm.actor"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="片长" prop="movieLong">
                    <el-col :span="16">
                        <el-input v-model="addForm.movieLong"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="语言" prop="language">
                    <el-col :span="16">
                        <el-select v-model="addForm.language" placeholder="请选择语言" style="width: 100%">
                            <el-option
                                v-for="item in languageOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="类型" prop="type">
                    <el-col :span="16">
                        <el-select v-model="addForm.type" placeholder="请选择类型" style="width: 100%">
                            <el-option
                                v-for="item in typeOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="上映时间" prop="publicDate">
                    <el-col :span="16">
                        <el-date-picker type="date" placeholder="选择日期" v-model="addForm.publicDate"
                                        :picker-options="pickerDay" value-format="yyyy-MM-dd"
                                        style="width: 100%;"></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="简介" prop="intro">
                    <el-col :span="16">
                        <el-input type="textarea" v-model="addForm.intro"
                                  :autosize="{ minRows: 8, maxRows: 10}"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addMovie">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
            title="修改影片信息"
            :visible.sync="editDialogVisible"
            width="50%">
            <el-form :model="editForm" :rules="editFormRules"
                     ref="editFormRef" label-width="90px"
                     @close="editDialogClosed">
                <el-form-item label="电影名" prop="name">
                    <el-col :span="16">
                        <el-input v-model="editForm.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="导演" prop="director">
                    <el-col :span="16">
                        <el-input v-model="editForm.director"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="演员" prop="actor">
                    <el-col :span="16">
                        <el-input v-model="editForm.actor"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="片长" prop="movieLong">
                    <el-col :span="16">
                        <el-input v-model="editForm.movieLong"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="语言" prop="language">
                    <el-col :span="16">
                        <el-select v-model="editForm.language" placeholder="请选择语言" style="width: 100%">
                            <el-option v-for="item in languageOptions" :key="item.value" :label="item.label"
                                       :value="item.value"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="类型" prop="type">
                    <el-col :span="16">
                        <el-select v-model="editForm.type" placeholder="请选择类型" style="width: 100%">
                            <el-option v-for="item in typeOptions" :key="item.value" :label="item.label"
                                       :value="item.value"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="上映时间" prop="publicDate">
                    <el-col :span="16">
                        <el-date-picker type="date" placeholder="选择日期" v-model="editForm.publicDate"
                                        :picker-options="pickerDay" value-format="yyyy-MM-dd"
                                        style="width: 100%;"></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="简介" prop="intro">
                    <el-col :span="16">
                        <el-input type="textarea" v-model="editForm.intro"
                                  :autosize="{ minRows: 8, maxRows: 10}"></el-input>
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
import {findMovie, insertMovie, updateMovie, deleteMovie, findOneMovie} from "../../api";

export default {
    data() {
        let checkMovieLong = (rule, value, callback) => {
            if (!value) {
                callback(new Error('片长信息不能为空'));
            } else {
                if (/^[1-9]{1}[0-9]+分钟$/.test(value)) {
                    callback();
                } else {
                    callback(new Error('格式应为xx分钟'));
                }
            }
        };
        return {
            queryInfo: {
                query: '',
                pageNum: 1,
                pageSize: 5
            },
            movieList: [],
            total: 0,
            addDialogVisible: false,
            addForm: {
                name: '',
                director: '',
                actor: '',
                movieLong: '',
                language: '',
                type: '',
                publicDate: '',
                intro: ''
            },
            addFormRules: {
                name: [
                    {required: true, message: '电影名不能为空', trigger: 'blur'},
                ],
                director: [
                    {required: true, message: '导演信息不能为空', trigger: 'blur'},
                ],
                actor: [
                    {required: true, message: '主演信息不能为空', trigger: 'blur'},
                ],
                movieLong: [
                    {required: true, message: '片长信息不能为空', trigger: 'blur'},
                    {validator: checkMovieLong, trigger: 'blur'}
                ],
                language: [
                    {required: true, message: '请选择语言', trigger: 'change'},
                ],
                type: [
                    {required: true, message: '请选择电影类型', trigger: 'change'},
                ],
                publicDate: [
                    {required: true, message: '请选择上映日期', trigger: 'blur'},
                ],
                intro: [
                    {required: true, message: '电影简介信息不能为空', trigger: 'blur'},
                ]
            },
            languageOptions: [{
                value: '粤语',
                label: '粤语'
            }, {
                value: '国语',
                label: '国语'
            }, {
                value: '英语',
                label: '英语'
            }, {
                value: '日语',
                label: '日语'
            }, {
                value: '其它',
                label: '其它'
            }],
            typeOptions: [{
                value: '动漫',
                label: '动漫'
            }, {
                value: '言情',
                label: '言情'
            }, {
                value: '科幻',
                label: '科幻'
            }, {
                value: '喜剧',
                label: '喜剧'
            },{
                value: '剧情',
                label: '剧情'
            }, {
                value: '动作',
                label: '动作'
            }, {
                value: '冒险',
                label: '冒险'
            }, {
                value: '青春',
                label: '青春'
            }, {
                value: '悬疑恐怖',
                label: '悬疑恐怖'
            },{
                value: '其它',
                label: '其它'
            }
            ],
            pickerDay: {
                disabledDate(time) {
                    return time.getTime() < Date.now() - 8.64e7;//8.64e7 = 1000 * 60 * 60 * 24(一天)
                }
            },
            editDialogVisible: false,
            editForm: {},
            editFormRules: {
                name: [
                    {required: true, message: '电影名不能为空', trigger: 'blur'},
                ],
                director: [
                    {required: true, message: '导演信息不能为空', trigger: 'blur'},
                ],
                actor: [
                    {required: true, message: '主演信息不能为空', trigger: 'blur'},
                ],
                movieLong: [
                    {required: true, message: '片长信息不能为空', trigger: 'blur'},
                    {validator: checkMovieLong, trigger: 'blur'}
                ],
                language: [
                    {required: true, message: '请选择语言', trigger: 'change'},
                ],
                type: [
                    {required: true, message: '请选择电影类型', trigger: 'change'},
                ],
                publicDate: [
                    {required: true, message: '请选择上映日期', trigger: 'blur'},
                ],
                intro: [
                    {required: true, message: '电影简介信息不能为空', trigger: 'blur'},
                ]
            }
        }
    },
    created() {
        this.getMovieList()
    },
    methods: {
        getMovieList() {
            findMovie(this.queryInfo).then(res => {
                // console.log(res)
                if (res.data.code === 1) {
                    this.movieList = res.data.lists.records
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
            this.getMovieList()
        },
        handleCurrentChange(page) {
            this.queryInfo.pageNum = page
            this.getMovieList()
        },
        addDialogClosed() {
            this.$refs.addFormRef.resetFields()
        },
        uploadPoster(id) {
            return `http://localhost:9090/updateMoviePoster?id=${id}`
        },
        getPoster(path){
            return 'http://localhost:9090' + path
        },
        addMovie() {
            // console.log(this.addForm)
            insertMovie(this.addForm).then(res => {
                if (res.data.code === 1) {
                    this.$message.success(res.data.msg)
                } else {
                    this.$message.error(res.data.msg)
                }
                this.addDialogVisible = false
                this.getMovieList()
            }).catch(err => {
                console.log(err)
            })
        },
        showEditDialog(id) {
            this.editDialogVisible = true
            findOneMovie({id:id}).then(res=>{
                if (res.data.code === 1){
                    this.editForm = res.data.movie
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
            updateMovie(this.editForm).then(res => {
                if (res.data.code === 1) {
                    this.$message.success(res.data.msg)
                } else {
                    this.$message.error(res.data.msg)
                }
                this.editDialogVisible = false
                this.getMovieList()
            }).catch(err => {
                console.log(err)
            })
        },
        async deleteMovieById(id) {
            const confirmResult = await this.$confirm('是否删除此影片?', '提示', {
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
                deleteMovie({id: id}).then(res => {
                    if (res.data.code === 1) {
                        this.$message.success(res.data.msg)
                    } else {
                        this.$message.error(res.data.msg)
                    }
                    this.getMovieList()
                }).catch(err => {
                    console.log(err)
                })
            }
        },
        handleAvatarSuccess(res, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
            this.getMovieList()
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
