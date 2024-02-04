<template>
    <section id="select-cinema">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="$router.go(-1)" style="position: absolute;left: .5rem;"/>
            <span class="name ellipsis">{{ movieInfo.name }}</span>
        </header>
        <section class="info">
            <van-image :src="getPoster(movieInfo.poster)" width="5rem" height="6rem" fit="cover"/>
            <div class="describe">
                <div class="name">{{ movieInfo.name }}</div>
                <div class="small type">类型：{{ movieInfo.type }}</div>
                <div class="small ellipsis">主演：{{ movieInfo.actor }}</div>
                <div class="small play-time">片长：{{ movieInfo.movieLong }}</div>
                <div class="small show-time">上映：{{ movieInfo.publicDate }}上映</div>
            </div>
        </section>
        <section class="action">
            <div class="btn" :class="{'active':!notWishMovie}" @click="wishBtnHandle"><span
                class="icon-like-fill"></span><span>想看</span></div>
            <div class="btn" @click="watchedBtnHandle"><span class="icon-star-fill"></span><span>看过</span></div>
        </section>
        <ly-tab
            v-model="selectedId"
            :items="items"
            :options="options"
            class="ly-tab"
            v-if="hackReset"
            @change="changeLyTabItem"
        />
        <main class="content">
            <div class="item" v-for="(item,index) in dateCinemaSchedule" :key="index"
                 @click="$router.push({path:'/cinema_detail',query:{movie_id:item.movie_id,cinema_id:item.cinema_id}})">
                <div class="left">
                    <div class="name ellipsis">{{ item.name }}</div>
                    <div class="address ellipsis">{{ item.specified_address }}</div>
                    <div class="label-block"><span>小吃</span><span>4D厅</span><span>杜比全景声厅</span><span>巨幕厅</span></div>
                </div>
            </div>
        </main>
    </section>
</template>

<script>
import {formatDate} from "@/assets/js/util";
import {
    selectMovieInfo,
    selectMovieScheduleInfo,
    selectMovieCinemaScheduleInfo,
    insertWishMovie,
    cancelWishMovie, isWishMovie
} from "@/api";

export default {
    name: "SelectCinema",
    data() {
        return {
            movieInfo: {},
            selectedId: 0,
            cinemaInfo: [],
            //特定日期的影院
            dateCinemaSchedule: [],
            hackReset: true,
            items: [],
            options: {
                activeColor: '#dd2727',
            },
            notWishMovie: true
        }
    },
    created() {
        this.loadCinemaInfo()
    },
    methods: {
        loadCinemaInfo() {
            selectMovieInfo({id: this.$route.query.movie_id}).then(res => {
                if (res.data.code === 1) {
                    this.movieInfo = res.data.movie
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
                    }
                }
            }).catch(err => {
                console.log(err)
            })
            selectMovieScheduleInfo({movieId: this.$route.query.movie_id}).then(res => {
                if (res.data.code === 1) {
                    this.cinemaInfo = res.data.movieSchedule
                    this.cinemaInfo.forEach((value) => {
                        //日期标签
                        this.items.push({label: formatDate(new Date(value.show_date), true), date: value.show_date});
                    });
                    this.hackReset = false;
                    this.$nextTick(() => {
                        this.hackReset = true;
                    });
                    this.getMovieCinema(this.cinemaInfo[0].show_date)
                }
            }).catch(err => {
                console.log(err)
            })
        },
        changeLyTabItem(item) {
            this.getMovieCinema(item.date)
        },
        getMovieCinema(showDate) {
            //当前日期有该影片的影院
            selectMovieCinemaScheduleInfo({
                movieId: this.$route.query.movie_id,
                showDate: showDate
            }).then(res => {
                this.dateCinemaSchedule = res.data.movieCinema
            }).catch(err => {
                console.log(err)
            })
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
                    cancelWishMovie({
                        userId: this.$cookies.get('UID'),
                        movieId: this.$route.query.movie_id
                    }).then(res => {
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
    }
}
</script>

<style scoped lang="stylus">
#select-cinema
    width 100%
    height 100%
    color #fff

    .top
        width 100%
        height 3rem
        display flex
        justify-content center
        align-items center
        position fixed
        top 0
        left 0
        background-color #dd2727
        color #fff

        .name
            width 60%
            font-size 1.125rem
            line-height 1.125rem
            text-align center

    .info
        width 100%
        padding .2rem
        box-sizing border-box
        height 7rem
        margin 3rem auto 0
        display flex
        justify-content space-around
        align-items center
        color #333
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

    .ly-tab
        width 100%
        position fixed
        top 12rem
        left 0

    .select
        position fixed
        left 0
        top 1.68rem
        width 100%
        height .8rem
        display flex
        justify-content space-around
        align-items center
        border-bottom .03rem solid #f1f1f1
        border-top .03rem solid #f1f1f1
        box-sizing border-box
        background-color #fff

        .option
            width 33.33%
            box-sizing border-box
            padding .1rem .6rem
            display flex
            justify-content center
            align-items flex-end
            font-size .28rem
            border-right .02rem solid #f1f1f1

            &:last-child
                border-right none

            .arrow
                margin-left .1rem
                padding-bottom .05rem

                &::after
                    content ''
                    width 0
                    height 0
                    overflow hidden
                    font-size 0
                    line-height 0
                    border-width .1rem
                    border-style solid
                    border-color #888 transparent transparent transparent

    .content
        margin-top 3rem

        .item
            display flex
            justify-content center
            align-items center
            box-sizing border-box
            padding .25rem
            width 100%
            margin-bottom .25rem

            .left
                width 100%
                color #666

                .name
                    font-size 1.125rem
                    line-height 1.36rem
                    font-weight 700
                    margin-bottom .25rem

                .address
                    font-size .28rem
                    height .3rem
                    margin-bottom .75rem

                .label-block
                    display flex

                    span
                        padding .06rem
                        font-size .1rem
                        border .01rem solid #f90
                        margin-right .2rem
                        border-radius .04rem
                        color #f90
</style>