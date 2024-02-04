<template>
    <section class="login_container">
        <header class="top">
            <van-icon class="icon_close" name="cross" @click="$router.go(-1)"></van-icon>
        </header>
        <main class="login_main">
            <div class="login_header">
                <span class="icon icon-film-logo"></span>
                <p class="title">折月电影</p>
                <i class="eng-title">Sora Movie</i>
            </div>
            <div class="login_content">
                <div class="login_type">
                    <span :class="{active:loginType}" @click="changeLoginType">验证码登录</span>
                    <span :class="{active:!loginType}" @click="changeLoginType">密码登录</span>
                </div>
                <div class="login_main">
                    <form>
                        <!--手机验证码登录部分-->
                        <div v-show="loginType " class="login_phone">
                            <section class="login_info">
                                <input type="tel" placeholder="手机号" v-model="phone">
                                <button v-if="countDown===0" :class="{phone_right:checkPhone}"
                                        @click.prevent="getValidateCode">获取验证码
                                </button>
                                <button v-else class="phone_right">重新获取({{ countDown }}s)</button>
                            </section>
                            <section class="login_info login-verification">
                                <input type="tel" placeholder="验证码" v-model="phoneCode">
                            </section>
                            <section class="login_info login_hint">
                                温馨提示：未注册的手机号，验证后将自动注册账户，且代表已同意
                                <a href="">《服务协议与隐私政策》</a>
                            </section>
                        </div>
                        <!--账号登录部分-->
                        <div v-show="!loginType" class="login_password">
                            <section class="login_info">
                                <input type="text" placeholder="用户名" v-model="username">
                            </section>
                            <section class="login_info login-verification">
                                <input type="password" placeholder="密码" v-model="password">
                            </section>
                        </div>
                        <van-button style="width: 70%;height: 3rem;margin: 1.25rem auto;"
                                    type="primary" block round color="#dd2727"
                                    @click.prevent="login">登录</van-button>
                    </form>
                </div>
            </div>
        </main>
    </section>
</template>

<script>
import {getPhoneCode, userPhoneLogin, userPasswordLogin} from "@/api";

export default {
    name: "Login",
    data() {
        return {
            phone: '',//手机号码
            phoneCode: '',//手机验证码
            username: '',//用户名
            password: '',//密码
            loginType: true,//登录方式
            countDown: 0, //倒计时
        }
    },
    methods: {
        //选择登录类型
        changeLoginType() {
            this.loginType = !this.loginType
        },
        //获取验证码
        getValidateCode() {
            if (this.checkPhone) {
                this.countDown = 60;
                let timer = setInterval(() => {
                    this.countDown--;
                    if (this.countDown === 0) {
                        clearInterval(timer);
                    }
                }, 1000);
                getPhoneCode().then(res => {
                    // console.log(res)
                    if (res.data.code === 1) {
                        this.$notify({type: 'success', message: '验证码为：' + res.data.phoneCode})
                    } else {
                        this.$notify({type: 'danger', message: '获取手机验证码失败'})
                        clearInterval(timer)
                        this.countDown = 0;
                    }
                }).catch(err => {
                    console.log(err)
                })
            }
        },
        //登录
        login() {
            //判断登录方式
            if (this.loginType) {
                //通过手机号方式登录
                if (this.phone === '') {
                    this.$notify({type: 'warning', message: '请输入手机号'})
                } else if (!this.checkPhone) {
                    this.$notify({type: 'warning', message: '请输入正确的手机号'})
                } else if (this.phoneCode === '') {
                    this.$notify({type: 'warning', message: '请输入验证码'})
                } else {
                    let user = {phone: this.phone, phoneCode: this.phoneCode}
                    userPhoneLogin(user).then(res => {
                        // console.log(res)
                        if (res.data.code === 1) {
                            this.$cookies.set("UID", res.data.userId)
                            this.$router.go(-1)
                        }
                        this.$notify({type: 'warning', message: res.data.msg})
                    }).catch(err => {
                        console.log(err)
                    })
                }
            } else {
                if (this.username === '') {
                    this.$notify({type: 'warning', message: '用户名不能为空'})
                } else if (this.password === '') {
                    this.$notify({type: 'warning', message: '密码不能为空'})
                } else {
                    let user = {username: this.username, password: this.password}
                    userPasswordLogin(user).then(res => {
                        if (res.data.code === 1) {
                            this.$cookies.set("UID", res.data.userId)
                            this.$router.go(-1)
                        }
                        this.$notify({type: 'warning', message: res.data.msg})
                    }).catch(err => {
                        console.log(err)
                    })
                }
            }
        }
    },
    computed: {
        //检查手机格式
        checkPhone() {
            return /^1[3|4|5|6|7|8][0-9]{9}$/.test(this.phone);
        }
    }
}
</script>

<style scoped>
#login_container {
    width: 100%;
    height: 100%;
    color: #000;
    font-size: 1.125rem;
}

.top {
    width: 100%;
    height: 4rem;
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    color: #dd2727;
}

.icon_close {
    font-size: 2rem;
    position: absolute;
    left: 1rem;
}

.login_main {
    margin-top: 4rem;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-flow: column;
}

.login_header {
    display: flex;
    flex-flow: column;
    justify-content: center;
    align-items: center;
    margin-bottom: 1.2rem;
}

.icon {
    font-size: 6rem;
}

.title, .eng-title {
    font-size: 1.3125rem;
    font-weight: bolder;
    text-align: center;
    color: #dd2727;
}

.title {
    text-indent: .2rem;
    letter-spacing: .44rem;
    margin-bottom: .34rem;
}

.eng-title {
    letter-spacing: .02rem;
    font-size: 1.1rem;
}

.login_content {
    width: 100%;
    background-color: #fff;
}

.login_type {
    width: 100%;
    font-size: 1.125rem;
    font-weight: 600;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 1.125rem;
}

.login_type span {
    margin: 1.25rem;
    padding-bottom: .625rem;
    box-sizing: border-box;
}

.active {
    color: #dd2727;
    border-bottom: .275rem solid #dd2727;
}

.login_main {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

form {
    width: 100%;
}

.login_phone, .login_password {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-flow: column;
}

.login_info {
    position: relative;
    margin-bottom: 1.125rem;
    width: 70%;
}

.login_info input {
    width: 100%;
    height: 2.5rem;
    padding-left: .52rem;
    box-sizing: border-box;
    border: .06rem solid #888;
    border-radius: .875rem;
    outline: 0;
}

.login_info input:focus {
    border: .06rem solid #dd2727;
}

.login_info > button {
    position: absolute;
    top: 50%;
    right: .2rem;
    height: 80%;
    transform: translateY(-50%);
    border: 0;
    color: #ccc;
    font-size: .525rem;
    background: transparent;
    outline: none;
}

.login_info > .phone_right {
    color: #e02e24;
}

.login_hint {
    color: #888;
    font-size: .44rem;
    line-height: 20px;
}

.login_hint > a {
    color: #dd2727;
}

</style>