<template>
    <van-tabbar id="tab-bar" v-model="active" active-color="#ee0a24"
                inactive-color="#000">
        <van-tabbar-item :to="{path: '/index'}" icon="home-o" name="/index">首页</van-tabbar-item>
        <van-tabbar-item :to="{path: '/movie',query:{hotMovie:1}}" icon="video-o" name="/movie">电影</van-tabbar-item>
        <van-tabbar-item :to="{path: '/cinema'}" icon="tv-o" name="/cinema">影院</van-tabbar-item>
        <van-tabbar-item :to="{path: '/user'}" icon="contact" name="/user">我的</van-tabbar-item>
    </van-tabbar>
</template>

<script>
export default {
    name: "TabBar",
    data() {
        return {
            active: '/index',
            // path: '/'
        }
    },
    watch: {
        '$route': function (to, from) {
            // this.path = to.path;
            // this.active = this.path.replace('/','')
            this.active = to.path
        }
    },
    beforeMount() {
        this.active = this.$route.path;
    }
}
</script>

<style scoped>
#tab-bar {
    width: 100%;
    height: 3rem;
    position: fixed;
    left: 0;
    display: flex;
    bottom: 0;
    right: 0;
    z-index: 999;
    background-color: #fff;
    border-top: 1px solid #f1f1f1;
}

.van-tabbar-item {
    display: flex;
    flex: 1;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    font-size: .5rem;
    color: #333;
}
</style>