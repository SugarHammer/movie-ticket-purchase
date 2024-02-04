<template>
    <div>
        <el-card class="user-card">
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getMovieScheduleList">
                        <el-button slot="append" icon="el-icon-search" @click="getMovieScheduleList"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" round @click="addDialogVisible = true">电影排片</el-button>
                </el-col>
            </el-row>
            <el-table :data="movieScheduleList" border stripe>
                <el-table-column type="index" align="center" width="40px"></el-table-column>
                <el-table-column label="电影名" show-overflow-tooltip prop="name" align="center"></el-table-column>
                <el-table-column label="影院名" show-overflow-tooltip prop="cinema_name" align="center"></el-table-column>
                <el-table-column label="放映大厅" prop="hall_name" align="center"></el-table-column>
                <el-table-column label="放映日期" prop="show_date" align="center"></el-table-column>
                <el-table-column label="放映时间" prop="show_time" align="center"></el-table-column>
                <el-table-column label="售价（元）" prop="price" align="center"></el-table-column>
                <el-table-column label="操作" align="center">
                    <template v-slot="scope">
                        <el-tooltip effect="dark" content="撤回"
                                    placement="top" :enterable="false">
                            <el-button type="danger"
                                       icon="el-icon-delete"
                                       circle size="mini"
                                       @click="deleteMovieScheduleById(scope.row.id)"></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="queryInfo.pageNum"
                :page-sizes="[10, 15, 20]"
                :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </el-card>
        <el-dialog
            title="排片安排"
            :visible.sync="addDialogVisible"
            width="60%"
            @close="addDialogClosed">
            <el-form label-position="right" :rules="addFormRules" label-width="80px" :model="addForm"
                     ref="addFormRef">
                <el-form-item label="电影名" prop="movieId">
                    <el-col :span="12">
                        <el-select v-model="addForm.movieId" placeholder="请选择" style="width: 100%"
                                   @change="getPublicDate">
                            <el-option v-for="item in movieOptions" :key="item.id" :label="item.name"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="影院" prop="cinemaId">
                    <el-col :span="12">
                        <el-select v-model="addForm.cinemaId" placeholder="请选择" style="width: 100%"
                                   @change="getHallList">
                            <el-option v-for="item in cinemaOptions" :key="item.id" :label="item.name"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="影厅" prop="hallName">
                    <el-col :span="12">
                        <el-select v-model="addForm.hallName" placeholder="请选择" style="width: 100%">
                            <el-option v-for="item in hallOptions" :key="item.id" :label="item.name"
                                       :value="item.name"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="上映日期" prop="publicDate">
                    <el-col :span="12">
                        <el-date-picker v-model="moviePublicDate" type="date" readonly
                                        style="width: 100%"></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="放映日期" prop="showDate">
                    <el-col :span="12">
                        <el-date-picker v-model="addForm.showDate" type="date" placeholder="选择日期"
                                        :picker-options="pickerDay" value-format="yyyy-MM-dd"
                                        style="width: 100%" @change="getMovieShowTime"></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="放映时间" prop="showTime">
                    <el-col :span="12">
                        <el-select v-model="addForm.showTime" placeholder="请选择时间" style="width: 100%">
                            <i slot="prefix" class="el-input__icon el-icon-time"></i>
                            <el-option v-for="item in timeOptions" :key="item.value" :label="item.label"
                                       :value="item.value"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="售价" prop="price">
                    <el-col :span="12">
                        <el-input v-model="addForm.price" style="width: 100%"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addMovieSchedule">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {
    findMovieSchedule,
    insertMovieSchedule,
    deleteMovieSchedule,
    getMovieList,
    getCinemaList,
    findHallList, findMovieScheduleList
} from "../../api"

export default {
    data() {
        let checkPrice = (rule, value, callback) => {
            if (!value) {
                callback(new Error('售价不能为空'));
            } else {
                if (/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/.test(value)) {
                    callback();
                } else {
                    callback(new Error('请输入正确格式的售价'));
                }
            }
        };
        return {
            queryInfo: {
                query: '',
                pageNum: 1,
                pageSize: 10
            },
            movieScheduleList: [],
            total: 0,
            addDialogVisible: false,
            addForm: {
                movieId: '',
                cinemaId: '',
                hallName: '',
                showDate: '',
                showTime: '',
                price: ''
            },
            addFormRules: {
                movieId: [
                    {required: true, message: '电影名不能为空', trigger: 'change'},
                ],
                cinemaId: [
                    {required: true, message: '影院名不能为空', trigger: 'change'},
                ],
                hallName: [
                    {required: true, message: '影厅名不能为空', trigger: 'change'},
                ],
                showDate: [
                    {required: true, message: '放映日期不能为空', trigger: 'change'},
                ],
                showTime: [
                    {required: true, message: '放映时间不能为空', trigger: 'change'},
                ],
                price: [
                    {required: true, message: '售价不能为空', trigger: 'blur'},
                    {validator: checkPrice, trigger: 'blur'}
                ]
            },
            pickerDay: {
                disabledDate:this.disabledDate
            },
            movieOptions: [],
            cinemaOptions: [],
            hallOptions: [],
            timeOptions: [],
            moviePublicDate: '',
        }
    },
    created() {
        this.getMovieScheduleList()
        this.getMovieList()
        this.getCinemaList()
    },
    methods: {
        getMovieList() {
            getMovieList().then(res => {
                if (res.data.code === 1) {
                    this.movieOptions = res.data.movieList
                } else {
                    this.$message.error(res.data.msg)
                }
            }).catch(err => {
                console.log(err)
            })
        },
        getCinemaList() {
            getCinemaList().then(res => {
                if (res.data.code === 1) {
                    this.cinemaOptions = res.data.cinemaList
                } else {
                    this.$message.error(res.data.msg)
                }
            }).catch(err => {
                console.log(err)
            })
        },
        getHallList() {
            this.addForm.hallName = ''
            findHallList({id: this.addForm.cinemaId}).then(res => {
                if (res.data.code === 1) {
                    this.hallOptions = res.data.halls
                } else {
                    this.$message.error(res.data.msg)
                }
            }).catch(err => {
                console.log(err)
            })
        },
        getMovieScheduleList() {
            findMovieSchedule(this.queryInfo).then(res => {
                if (res.data.code === 1) {
                    this.movieScheduleList = res.data.lists.records
                    this.total = res.data.lists.total
                }
            }).catch(err => {
                console.log(err)
            })
        },
        handleSizeChange(size) {
            this.queryInfo.pageSize = size
            this.getMovieScheduleList()
        },
        handleCurrentChange(page) {
            this.queryInfo.pageNum = page
            this.getMovieScheduleList()
        },
        addDialogClosed() {
            this.$refs.addFormRef.resetFields()
            this.moviePublicDate = ''
        },
        addMovieSchedule() {
            // console.log(this.addForm)
            // console.log("添加排片信息")
            insertMovieSchedule(this.addForm).then(res => {
                if (res.data.code === 1) {
                    this.$message.success(res.data.msg)
                } else {
                    this.$message.error(res.data.msg)
                }
                this.addDialogVisible = false
                this.getMovieScheduleList()
            }).catch(err => {
                console.log(err)
            })
        },
        async deleteMovieScheduleById(id) {
            const confirmResult = await this.$confirm('是否删除此次安排?', '提示', {
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
                deleteMovieSchedule({id: id}).then(res => {
                    if (res.data.code === 1) {
                        this.$message.success(res.data.msg)
                    } else {
                        this.$message.error(res.data.msg)
                    }
                    this.getMovieScheduleList()
                }).catch(err => {
                    console.log(err)
                })
            }
        },

        getPublicDate(value) {
            if (value) {
                this.movieOptions.forEach((val => {
                    if (val.id === value) {
                        this.moviePublicDate = val.publicDate
                    }
                }))
            }
        },

        getMovieShowTime(value) {
            if (value && this.addForm.cinemaId && this.addForm.hallName) {
                let movieScheduleInfo = {
                    cinemaId: this.addForm.cinemaId,
                    hallName: this.addForm.hallName,
                    showDate: this.addForm.showDate
                }
                findMovieScheduleList(movieScheduleInfo).then(res => {
                    if (res.data.code === 1) {
                        if (res.data.schedules.length !== 0) {
                            this.timeOptions = [
                                {label: '09:30', value: '09:30'},
                                {label: '12:40', value: '12:40'},
                                {label: '16:30', value: '16:30'},
                                {label: '20:20', value: '20:20'},
                                {label: '22:00', value: '22:00'}
                            ]
                            let temp = []
                            this.timeOptions.forEach((val => {
                                let flag = false
                                res.data.schedules.forEach((value => {
                                    if (val.label === value.showTime) {
                                        flag = true
                                    }
                                }))
                                if (!flag) {
                                    temp.push(val)
                                }
                            }))
                            this.timeOptions = temp
                        } else {
                            this.timeOptions = [
                                {label: '09:30', value: '09:30'},
                                {label: '12:40', value: '12:40'},
                                {label: '16:30', value: '16:30'},
                                {label: '20:20', value: '20:20'},
                                {label: '22:00', value: '22:00'}
                            ]
                        }
                    }
                }).catch(err => {
                    console.log(err)
                })
            }
        },

        disabledDate(time){
            if (this.addForm.movieId){
                if (new Date(this.moviePublicDate)-new Date()>0){
                    return time.getTime() < new Date(this.moviePublicDate) - 8.64e7;
                } else{
                    return time.getTime() < Date.now() - 8.64e7;//8.64e7 = 1000 * 60 * 60 * 24(一天)
                }
            } else{
                return time.getTime() > 0;
            }
        }
    }
}
</script>

<style scoped>

</style>
