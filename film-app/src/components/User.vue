<template>
    <section class="user_container">
        <header class="user_header">
            <div class="left">
                <van-image round width="6rem" height="6rem" fit="cover"
                           :src="getAvatar(userInfo.avatar)">
                    <template v-slot:error>
                        <van-image round width="6rem" height="6rem" fit="cover"
                                   :src="imgSrc"></van-image>
                    </template>
                </van-image>
                <span class="login" @click="$router.push('/login')" v-if="!loginStatus">点击登录</span>
                <div v-else class="user-info">
                    <span class="ellipsis"
                          style="font-size: 1.45rem;line-height: 1.45rem;margin-bottom: .42rem">{{
                            userInfo.name ? userInfo.name : '用户' + userInfo.phone
                        }}</span>
                    <span class="ellipsis">{{ userInfo.sign ? userInfo.sign : '暂无签名' }}</span>
                </div>
            </div>
        </header>
        <main class="user_main">
            <div class="card">
                <van-grid square :gutter="10" :border="false">
                    <van-grid-item v-show="loginStatus" icon="friends-o" text="个人信息"
                                   @click="$router.push({path:'/user_info',query:{UID:userInfo.id}})"/>
                    <van-grid-item icon="coupon-o" text="我的订单"
                                   @click="$router.push({path:'/user_order',query:{UID:userInfo.id}})"/>
                    <van-grid-item icon="browsing-history-o" text="想看"
                                   @click="$router.push({path:'/user_movie',query:{UID:userInfo.id}})"/>
                    <van-grid-item icon="eye-o" text="看过"
                                   @click="$router.push({path:'/user_history',query:{UID:userInfo.id}})"/>
                </van-grid>
            </div>

        </main>
    </section>

</template>

<script>
import {selectUserInfo} from "@/api";

export default {
    name: "User",
    data() {
        return {
            userInfo: [],
            imgSrc: require('../assets/img/userIcon.png'),
            loginStatus: false,
        }
    },
    created() {
        this.loadUserInfo()
    },
    methods: {
        getAvatar(path) {
            return 'http://localhost:9090' + path
        },
        loadUserInfo() {
            if (this.$cookies.get("UID")) {
                selectUserInfo({id: this.$cookies.get("UID")}).then(res => {
                    if (res.data.code === 1) {
                        this.userInfo = res.data.user
                        this.loginStatus = true
                    }
                }).catch(err => {
                    console.log(err)
                })
            }
        },

    }
}
</script>

<style scoped>
.user_container {
    height: 100%;
    width: 100%;
}

.user_header {
    height: 12rem;
    background-color: rgba(3, 3, 3, 0.67);
}

.left {
    width: 14rem;
    padding: 3rem 0;
    margin: 0 0 0 3rem;
    align-items: center;
    display: flex;
    position: relative;
}


.login {
    position: absolute;
    left: 7rem;
}

.user_main {
    padding-top: 2rem;
}

.card {
    padding: .225rem 0;
    width: 80%;
    border-radius: 1rem 1rem;
    position: relative;
    top: -2.75rem;
    left: 10%;
    background-color: #fff;
    box-shadow: 0 .575rem .8rem #666;
}

.user-info {
    width: 100%;
    position: absolute;
    left: 6.5rem;
    display: flex;
    flex-flow: column;
}

.user-info span:last-child {
    font-size: 1rem;
}

</style>