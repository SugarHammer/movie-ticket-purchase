<template>
    <div class="login">
        <div class="box">
            <el-form label-width="60px" :model="loginForm">
                <h3>影院售票后台系统</h3>
                <el-form-item label="用户名">
                    <el-input v-model="loginForm.username" prefix-icon="el-icon-user-solid"
                              clearable placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input placeholder="请输入密码" v-model="loginForm.password" prefix-icon="el-icon-lock"
                              show-password></el-input>
                </el-form-item>
                <el-form-item style="margin: 30px 0 0 30px">
                    <el-button @click="reset">重置</el-button>
                    <el-button style="margin-left: 30px" type="primary" size="medium" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import {login} from '../api/index'

export default {
    data() {
        return {
            loginForm: {
                username: 'admin',
                password: 'admin'
            }
        }
    },
    methods: {
        login() {
            login(this.loginForm).then(res => {
                if (res.data.code === 1){
                    this.$message.success(res.data.msg)
                    this.$router.replace('/index')
                }else{
                    this.$message.error(res.data.msg)
                    this.reset()
                }
            }).catch(err => {
                console.log(err)
            })
        },
        reset(){
            this.loginForm.username = ''
            this.loginForm.password = ''
        }
    }
}
</script>

<style scoped>
.login {
    width: 100%;
    height: 100%;
    background: url('../assets/img/login.jpg');
    background-size: cover;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-flow: column;
    color: #fff;
}

.box {
    width: 500px;
    height: 400px;
    background-color: rgba(255, 255, 255, .8);
    display: flex;
    justify-content: center;
    align-items: center;
    flex-flow: column;
    border-radius: 4px;
    box-shadow: 0 0 8px 8px #ccc;
}

h3 {
    margin-bottom: 40px;
    font-size: 36px;
    letter-spacing: 2px;
    color: #888;
    text-align: center
}
</style>
