<template>
    <section id="movie-detail">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="$router.go(-1)" style="position: absolute;left: .5rem;"/>
            <span class="name">电影</span>
        </header>
        <main class="info">
            <van-image :src="getPoster(movieInfo.poster)" width="5rem" height="6rem" fit="cover"/>
            <div class="describe">
                <div class="name">{{ movieInfo.name }}</div>
                <div class="small type">类型：{{ movieInfo.type }}</div>
                <div class="small ellipsis">主演：{{ movieInfo.actor }}</div>
                <div class="small play-time">片长：{{ movieInfo.movieLong }}</div>
                <div class="small show-time">上映：{{ movieInfo.publicDate }}上映</div>
            </div>
        </main>
        <section class="action">
            <div class="btn" :class="{'active':!notWishMovie}" @click="wishBtnHandle"><span
                class="icon-like-fill"></span><span>想看</span></div>
            <div class="btn" @click="watchedBtnHandle"><span class="icon-star-fill"></span><span>看过</span></div>
        </section>
        <section class="public-praise">
            <header class="header">
                <div class="title">口碑</div>
                <div class="wish" v-if="!isShowMovie">
                    <span>{{ movieInfo.wishNum }}人想看</span>
                </div>
            </header>
            <main class="mark" v-if="isShowMovie">
                <div class="left">
                    <van-rate v-model="averageScore" :size="25" color="#ffd21e" void-icon="star"
                              void-color="#eee" allow-half readonly :count="10"/>
                </div>
                <div class="right">
                    <div class="score">{{ this.averageScore ? this.averageScore : 0 }}<span class="small">分</span></div>
                    <div class="score-people">{{ commentNum ? commentNum : '暂无' }}人评</div>
                </div>
            </main>
        </section>
        <section class="intro">
            <div class="title">简介</div>
            <div class="content">
                {{ movieInfo.intro }}
            </div>
        </section>
        <section class="comment">
            <header class="header">
                <span class="title">评论</span><span class="join" @click="watchedBtnHandle">参与评论</span>
            </header>
            <main class="content">
                <div class="comment-container comment-list-container" v-if="userCommentDate.length">
                    <span class="title">精选评论</span>
                    <div class="comment-item" v-for="(item,index) in userCommentDate" :key="index">
                        <div class="left">
                            <van-image round fit="cover" :src="getAvatar(item.avatar)">
                                <template v-slot:error>
                                    <van-image round fit="cover" :src="imgSrc"></van-image>
                                </template>
                            </van-image>
                        </div>
                        <div class="right">
                            <div class="user-name">{{ item.name }}</div>
                            <div class="scored">给这部作品打了{{ item.user_score }}分</div>
                            <div class="comment-content">{{ item.content }}</div>
                            <div class="bottom">
                                <span class="comment-date">{{ formatCommentDate(item.comment_date) }}</span>
                                <span class="support" :class="{'active':userIsSupportComment(item.support_user)}"
                                      @click="supportBtnHandle(item.id)"><span class="icon-support"></span><span
                                    class="number">{{ item.support_num }}</span></span>
                            </div>
                        </div>
                    </div>
                </div>
                <span class="tips" v-if="!userCommentDate.length">暂无评论！</span>
            </main>
        </section>
        <section class="buy">
            <van-button block color="#dd2727" round class="btn"
                        @click="$router.push({path:'/select_cinema',query:{movie_id:$route.query.movie_id}})">特惠购票
            </van-button>
        </section>
    </section>
</template>

<script>
import {
    cancelWishMovie,
    getCommentById,
    getCommentInfo,
    insertWishMovie, isWishMovie,
    selectMovieInfo,
    updateUserSupport
} from "@/api";
import {formatDate} from "@/assets/js/util";
import moment from "moment";
export default {
    name: "MovieDetail",
    data() {
        return {
            movieInfo: [],
            isShowMovie: false,
            notWishMovie: true,
            starValue: 0,
            averageScore: 0,
            commentNum: 0,
            userCommentDate: [],
            imgSrc: require('../../assets/img/userIcon.png')
        }
    },
    created() {
        this.loadMovieInfo()
    },
    methods: {
        loadMovieInfo() {
            if (this.$route.query.movie_id) {
                selectMovieInfo({id: this.$route.query.movie_id}).then(res => {
                    if (res.data.code === 1) {
                        this.movieInfo = res.data.movie
                        //判断电影是否上映
                        new Date() - new Date(this.movieInfo.publicDate) >= 0 ? this.isShowMovie = true : this.isShowMovie = false;
                        if (this.isShowMovie) {
                            this.starValue = this.movieInfo.score * 0.5.toFixed(1);
                        }
                        if (this.$cookies.get('UID')) {
                            //判断用户是否喜欢该电影
                            isWishMovie({
                                user_id: this.$cookies.get('UID'),
                                movie_id: this.$route.query.movie_id
                            }).then(res => {
                                this.notWishMovie = res.data.code !== 1;
                            }).catch(err => {
                                console.log(err)
                            })
                            //获取所有评论
                            getCommentInfo({movie_id: this.$route.query.movie_id}).then(res => {
                                if (res.data.code === 1 && res.data.commentInfo.length) {
                                    let sum = 0
                                    this.userCommentDate = res.data.commentInfo
                                    this.commentNum = this.userCommentDate.length
                                    this.userCommentDate.forEach((value) => {
                                        sum += value.user_score;
                                    });
                                    this.averageScore = sum / (this.commentNum);
                                    if (this.averageScore !== 0 && this.averageScore !== 10) {
                                        this.averageScore = this.averageScore.toFixed(1);
                                    }
                                    this.starValue = this.averageScore * 0.5;

                                    this.userCommentDate.sort((a, b) => {
                                        return b.support_num - a.support_num
                                    });
                                }
                            }).catch(err => {
                                console.log(err)
                            })

                        }
                    } else {
                        this.$notify({type: 'warning', message: res.data.msg})
                    }
                }).catch(err => {
                    console.log(err)
                })
            }
        },
        getPoster(path) {
            return 'http://localhost:9090' + path
        },
        //想看按钮处理
        wishBtnHandle() {
            if (this.$cookies.get('UID')) {
                let wishMovieInfo = {userId: this.$cookies.get('UID'), movieId: this.$route.query.movie_id}
                //不想看
                if (this.notWishMovie) {
                    insertWishMovie(wishMovieInfo).then(res => {
                        if (res.data.code === 1) {
                            this.notWishMovie = false;
                            this.loadMovieInfo();
                        }
                    }).catch(err => {
                        console.log(err)
                    })
                } else {
                    cancelWishMovie({userId: this.$cookies.get('UID'), movieId: this.$route.query.movie_id}).then(res => {
                        if (res.data.code === 1) {
                            this.notWishMovie = true;
                            this.loadMovieInfo();
                        }
                    }).catch(err => {
                        console.log(err)
                    })
                }
            } else {
                this.$router.push('/login');
            }
        },
        //看过按钮处理
        watchedBtnHandle() {
            //用户已登录
            if (this.$cookies.get('UID')) {
                this.$router.push({path: '/comment_panel', query: {movie_id: this.$route.query.movie_id}});
            } else {
                this.$router.push('/login');
            }
        },
        //点赞按钮处理
        supportBtnHandle(commentId) {
            if (this.$cookies.get('UID')) {
                let supportUser, supportNum
                getCommentById({id: commentId}).then(res => {
                    if (res.data.code === 1) {
                        let info = res.data.info
                        //有点赞数据
                        if (info.supportUser) {
                            supportUser = JSON.parse(info.supportUser);
                            //当前用户已点赞
                            if (supportUser.indexOf(Number(this.$cookies.get('UID'))) > -1) {
                                //取消点赞
                                supportUser.splice(supportUser.indexOf(Number(this.$cookies.get('UID'))), 1);
                                supportNum = supportUser.length;
                                if (!supportUser.length) {
                                    supportUser = undefined;
                                }
                            } else {
                                //点赞
                                supportUser.push(Number(this.$cookies.get('UID')));
                                supportNum = supportUser.length;
                            }
                        } else {
                            //无点赞数据
                            supportUser = [];
                            supportUser.push(Number(this.$cookies.get('UID')));
                            supportNum = supportUser.length;
                        }
                        let comment = {
                            id: commentId,
                            supportNum: supportNum,
                            supportUser: JSON.stringify(supportUser)
                        }
                        updateUserSupport(comment).then(res => {
                            if (res.data.code === 1) {
                                //获取所有用户评论
                                getCommentInfo({movie_id: this.$route.query.movie_id}).then(res=>{
                                    if (res.data.code === 1 && res.data.commentInfo.length) {
                                        let sum = 0
                                        this.userCommentDate = res.data.commentInfo
                                        this.commentNum = this.userCommentDate.length
                                        this.userCommentDate.forEach((value) => {
                                            sum += value.user_score;
                                        });
                                        this.averageScore = sum / (this.commentNum);
                                        if (this.averageScore !== 0 && this.averageScore !== 10) {
                                            this.averageScore = this.averageScore.toFixed(1);
                                        }
                                        this.starValue = this.averageScore * 0.5;
                                        this.userCommentDate.sort((a, b) => {
                                            return b.support_num - a.support_num
                                        });
                                    }
                                }).catch(err=>{
                                    console.log(err)
                                })
                            }
                        }).catch(err => {
                            console.log(err)
                        })

                    }
                }).catch(err => {
                    console.log(err)
                })

            } else {
                this.$router.push('/login');
            }
        },
        //判断用户是否点赞
        userIsSupportComment(supportStrArr) {
            return supportStrArr && JSON.parse(supportStrArr).indexOf(Number(this.$cookies.get('UID'))) > -1;
        },
        //处理评论日期
        formatCommentDate(date) {
            return formatDate(new Date(moment(date).format('YYYY-MM-DD HH:mm:ss')),false);
        },
        getAvatar(path){
            return 'http://localhost:9090' + path
        }
    }
}
</script>

<style scoped lang="stylus">
#movie-detail
    width 100%
    height 100%
    background linear-gradient(to bottom, #ff6034 0%, #ee0a24 100%)
    color #fff

    .top
        width 100%
        height 3rem
        display flex
        justify-content center
        align-items center
        margin-bottom .2rem
        position fixed
        top 0
        background-color: rgba(3, 3, 3, 0.67);
        z-index 999

    .info
        width 100%
        padding .2rem
        box-sizing border-box
        height 7rem
        margin 3rem auto 0
        display flex
        justify-content space-around
        align-items center
        color #fff
        border-radius .5rem

        .describe
            width 62%

            .name
                font-size 1.345rem
                font-weight 700
                margin-bottom .42rem

            .small
                font-size .568rem
                margin-bottom .22rem

    .action
        width 100%
        height 2rem
        margin 0 auto
        display flex
        justify-content space-around
        align-items center
        border-radius .4rem

        .btn
            font-size 1.4rem
            width 36%
            height 1.6rem
            border-radius .5rem
            background-color #ffb400
            display flex
            justify-content center
            align-items center

            &.active
                background-color #fff1d4
                color red

            span:last-child
                font-size 1.125rem
                margin-left .1rem

    .public-praise
        width 90%
        height auto
        margin .5rem auto
        box-sizing border-box
        padding .45rem
        border-radius .5rem

        .header
            display flex
            justify-content space-between
            align-items center
            margin-bottom .45rem

            .title
                font-weight bolder

            .wish
                font-size 1.125rem
                padding .04rem 0

        /deep/ .mark
            width 100%
            display flex
            justify-content space-between
            align-items flex-start

            .right
                display flex
                flex-direction column
                justify-content center
                align-items center

                .score
                    font-size 1.128rem
                    font-weight bolder
                    color #ffb400
                    margin-bottom .4rem

                    .small
                        font-size 1.125rem

                .score-people
                    font-size .4rem
                    color #ffb400

    .intro
        width 90%
        height auto
        margin .4rem auto
        box-sizing border-box
        padding .45rem
        border-radius .5rem

        .title
            font-weight bolder
            color #fff
            margin-bottom .55rem

        .content
            text-align justify
            font-size .38rem
            line-height 1rem

    .comment
        width 100%
        box-sizing border-box
        padding .25rem
        height auto
        margin 0 auto
        background-color #fff
        color #333
        border-top-left-radius .2rem
        border-top-right-radius .2rem

        .header
            display flex
            justify-content space-between
            align-items center
            height 1rem

            .title
                font-weight bolder
                padding .25rem

            .join
                padding .14rem
                background-color #dd2727
                margin-right .25rem
                color #fff
                border-radius .12rem
                font-size .24rem

        .content
            width 100%
            padding .25rem
            padding-bottom 0
            box-sizing border-box
            margin-bottom 1rem

            .comment-container
                width 100%

                .title
                    font-weight lighter
                    font-size .25rem
                    padding-bottom .25rem

                .comment-item
                    display flex
                    padding-top .25rem

                    .left
                        width 10%
                        height 10%
                        border-radius 50%
                        overflow hidden

                    .right
                        margin 0 .25rem .25rem
                        font-size .3125rem
                        width 100%

                        .user-name
                            font-size .55rem
                            font-weight bolder
                            margin-bottom .2rem

                        .scored
                            font-size .2rem
                            font-weight lighter
                            margin-bottom .2rem

                        .comment-content
                            font-size .48rem
                            line-height .5rem
                            margin-bottom .1rem
                            text-align justify

                        .bottom
                            display flex
                            justify-content space-between
                            align-items center
                            font-size 1.28rem

                            .comment-date
                                font-size .44rem

                            .support
                                padding .1rem
                                border 0.01rem solid #ccc
                                border-radius .24rem

                                &.active
                                    color #dd2727
                                    border 0.01rem solid #dd2727

                                .number
                                    font-weight lighter
                                    margin-left .5rem
                                    font-size 1.25rem

            .tips
                font-size 1.25rem

    .buy
        position fixed
        bottom 0
        width 100%
        height 2rem
        background-color #fff
        color #fff
        display flex
        justify-content center

        .btn
            width 90%
            height 2rem
            display flex
            justify-content center
            font-weight light
</style>