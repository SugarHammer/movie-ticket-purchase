<template>
    <section class="user-info">
        <header style="height: 3rem">
            <van-nav-bar title="个人信息" left-text="返回" left-arrow @click-left="$router.go(-1)">
                <template #right>
                    <van-icon name="close" size="1rem" @click="logout"/>
                </template>
            </van-nav-bar>
        </header>
        <main>
            <van-form>
                <van-field name="uploader" label="头像" center>
                    <template #input>
                        <van-uploader :before-read="beforeRead" :after-read="uploadAvatar">
                            <van-image round width="6rem" height="6rem" fit="cover"
                                       :src="getAvatar(userInfo.avatar)">
                                <template v-slot:error>
                                    <van-image round width="6rem" height="6rem" fit="cover"
                                               :src="imgSrc"></van-image>
                                </template>
                            </van-image>
                        </van-uploader>
                    </template>
                </van-field>
                <van-field v-model="userInfo.name" label="用户名" clearable type="text"/>
                <van-field v-model="userInfo.password" label="密码" clearable type="password"/>
                <van-field name="radio" label="性别" v-model="userInfo.sex">
                    <template #input>
                        <van-radio-group v-model="userInfo.sex" direction="horizontal">
                            <van-radio name="男">男</van-radio>
                            <van-radio name="女">女</van-radio>
                        </van-radio-group>
                    </template>
                </van-field>
                <van-field readonly clickable name="datetimePicker" :value="userInfo.birth" label="生日"
                           placeholder="点击修改生日" @click="inputType?false:showPicker = true"/>
                <van-field v-model="userInfo.phone" label="手机号" clearable type="tel"
                           :rules="[{ validator: checkPhone, message: '请输入正确的手机号码' }]"/>
                <van-field v-model="userInfo.sign" rows="2" autosize label="个性签名" type="textarea"
                           maxlength="50" show-word-limit/>
                <div style="margin: 1rem;">
                    <van-button round block type="info" @click="updateUserInfo">修改</van-button>
                </div>
            </van-form>
        </main>
        <van-popup v-model="showPicker" position="bottom">
            <van-datetime-picker v-model="currentDate" type="date" @confirm="onConfirm"
                                 @cancel="showPicker = false" :min-date="minDate" :max-date="maxDate"
                                 :formatter="formatter"/>
        </van-popup>
    </section>
</template>

<script>
import {selectUserInfo, updateAvatar, updateInfo} from "@/api";

export default {
    name: "UserInfo",
    data() {
        return {
            userInfo: {},
            imgSrc: require('../../assets/img/userIcon.png'),
            showPicker: false,
            minDate: new Date(1990, 0, 1),
            maxDate: new Date(2025, 10, 1),
            currentDate: new Date(),
        }
    },
    created() {
        this.loadUserInfo()
    },
    methods: {
        loadUserInfo(){
            if (this.$route.query.UID){
                selectUserInfo({id:this.$route.query.UID}).then(res=>{
                    if (res.data.code === 1){
                        this.userInfo = res.data.user
                    }
                }).catch(err=>{
                    console.log(err)
                })
            }
        },
        getAvatar(path) {
            return 'http://localhost:9090' + path
        },
        beforeRead(file) {
            if (file.type !== 'image/jpeg') {
                this.Toast('请上传 jpg 格式图片');
                return false;
            }
            return true;
        },
        uploadAvatar(file) {
            let data = new FormData()
            data.append('file', file.file)
            data.append('id', this.userInfo.id)
            updateAvatar(data).then(res => {
                if (res.data.code === 1) {
                    this.$notify({type: 'success', message: res.data.msg})
                } else {
                    this.$notify({type: 'danger', message: res.data.msg})
                }
            }).catch(err => {
                console.log(err)
            })
        },
        updateUserInfo() {
            var info = {
                id: this.userInfo.id,
                name: this.userInfo.name,
                password: this.userInfo.password,
                sex: this.userInfo.sex,
                birth: this.userInfo.birth,
                phone: this.userInfo.phone,
                sign: this.userInfo.sign
            }
            updateInfo(info).then(res => {
                if (res.data.code === 1) {
                    this.$notify({type: 'success', message: res.data.msg})
                    this.$router.go(-1)
                } else {
                    this.$notify({type: 'warning', message: res.data.msg})
                }
            }).catch(err => {
                console.log(err)
            })
        },
        onConfirm(time) {
            this.userInfo.birth = this.timeFormat(time)
            this.showPicker = false;
        },
        formatter(type, val) {
            if (type === 'year') {
                return `${val}年`;
            } else if (type === 'month') {
                return `${val}月`;
            } else if (type === 'day') {
                return `${val}日`
            }
            return val;
        },
        timeFormat(value) {
            return value.getFullYear() + '-' + (value.getMonth() + 1) + '-' + value.getDate()
        },
        logout(){
            if (this.$cookies.get('UID')){
                this.$cookies.remove('UID')
                this.$router.push('/user')
            }
        },
        checkPhone(value) {
            return /^1[3|4|5|6|7|8][0-9]{9}$/.test(value)
        },
    }
}
</script>

<style scoped>
</style>