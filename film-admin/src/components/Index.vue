<template>
    <el-container class="home-container">
        <el-header>
            <div class="header_img">
                <img src="../assets/img/tip.png" alt="">
                <span>折月电影</span>
            </div>
            <div style="display: flex">
                <div class="user_avatar">
                    <img src="../assets/img/user.jpg" alt=""/>
                </div>
                <el-dropdown trigger="click" @command="HandleCommand">
                    <span class="el-dropdown-link" style="font-weight: 600;color: #fff">
                        admin
                        <i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item icon="el-icon-switch-button" command="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>

        </el-header>
        <el-container>
            <el-aside :width="isCollapse ? '64px':'200px'">
                <div class="toggle_button" @click="toggleCollapse">|||</div>
                <el-menu background-color="#333744" text-color="#fff"
                         active-text-color="#409eff" unique-opened :collapse="isCollapse"
                         :collapse-transition="false"  :default-active="activePath">
                    <el-menu-item :index="item.path" v-for="item in menuList"
                                    @click="handleMenuItem(item.path)">
                        <template>
                            <i :class="item.icon"></i>
                            <span slot="title">{{item.title}}</span>
                        </template>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>

</template>

<script>
    export default {
        data() {
            return {
                isCollapse: false,
                activePath: '/database',
                menuList:[
                    {path:'/database',icon:'el-icon-s-platform',title:'数据显示'},
                    {path:'/user_manage',icon:'el-icon-user-solid',title:'用户管理'},
                    {path:'/movie_manage',icon:'el-icon-video-camera',title:'电影管理'},
                    {path:'/cinema_manage',icon:'el-icon-video-camera-solid',title:'影院管理'},
                    {path:'/hall_manage',icon:'el-icon-bangzhu',title:'影厅管理'},
                    {path:'/movie_schedule',icon:'el-icon-s-fold',title:'电影排片'},
                    {path:'/comment_manage',icon:'el-icon-s-comment',title:'评论管理'},
                    {path:'/order_manage',icon:'el-icon-s-ticket',title:'订单管理'}
                ]
            }
        },
        created() {
            if (sessionStorage.getItem('activePath') !== null){
                this.activePath = sessionStorage.getItem('activePath')
            }
        },
        methods: {
            logout() {
                this.$router.replace('/login')
            },
            toggleCollapse() {
                this.isCollapse = !this.isCollapse
            },
            handleMenuItem(path){
                this.$router.push('/index' + path)
                this.activePath = path
                sessionStorage.setItem('activePath',path)
            },
            HandleCommand(command) {
                if (command === 'logout') {
                    this.$router.replace('/login');
                }
            }
        },
    }
</script>

<style scoped>
    .home-container {
        height: 100%;

    }
    .header_img {
        display: flex;
        align-items: center;
    }
    .el-header {
        background-color: #373d41;
        display: flex;
        justify-content: space-between;
        padding-left: 0;
        align-items: center;
        color: #ffffff;
        font-size: 20px;
    }

    span {
        margin-left: 15px;
    }

    .el-aside {
        background-color: #333744;
    }

    .el-menu {
        border-right: 0;
    }

    .el-main {
        background-color: #eaedf1;
    }

    .toggle_button {
        background-color: #4A5064;
        font-size: 10px;
        line-height: 24px;
        color: #fff;
        text-align: center;
        letter-spacing: 0.2em;
        cursor: pointer;
    }

    .user_avatar {
        margin-left: 20px;
    }

    .user_avatar img {
        display: block;
        width: 50px;
        height: 50px;
        border-radius: 50%;
    }

    .el-dropdown {
        align-items: center;
        display: flex;
    }

    .el-dropdown-link {
        color: #000;
        cursor: pointer;
        margin-left: 10px;
    }
</style>

