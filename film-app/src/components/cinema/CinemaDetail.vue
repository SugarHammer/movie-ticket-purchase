<template>
    <section id="cinema-detail">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="$router.go(-1)" style="position: absolute;left: .5rem;"/>
            <span class="name ellipsis">影院</span>
        </header>
        <section class="cinema-info">
            <span class="name">{{ cinemaInfo.name }}</span>
            <span class="address"><span class="icon icon-address"></span>{{ cinemaInfo.specifiedAddress }}</span>
            <span class="tel"><span class="icon icon-tel"></span>{{ cinemaInfo.phone }}</span>
        </section>
        <el-carousel :autoplay=false type="card" height="15rem" arrow="never" :loop=false :initial-index=initMovieId
                     indicator-position="none" @change="changeCarousel" v-if="carouselReset">
            <el-carousel-item v-for="(item,index) in hasMovieInfo" :key="index">
                <a href="#" @click.prevent="$router.push({path:'/movie_detail',query:{movie_id:item.movie_id}})">
                    <img :src="getPoster(item.poster)" alt=""/>
                </a>
            </el-carousel-item>
        </el-carousel>
        <section class="movie-info" v-for="(item,index) in hasMovieInfo" :key="index"
                 v-show="movieIndex===Number(index)">
            <span class="arrow"></span>
            <span class="main"><span class="name">{{ item.movie_name }}</span><span class="score">
                <span class="num" v-if="item.score">{{ item.score.toFixed(1) }}分</span>
                <span v-else style="font-size: .28rem">暂无评分</span></span></span>
            <span class="intro">
        <span class="time">{{ item.movie_long }}</span><span class="split">|</span><span
                class="type">{{ item.type }}</span><span class="split">|</span><span
                class="actors">{{ item.actor }}</span>
        </span>
        </section>
        <ly-tab
            v-model="selectedId"
            :items="items"
            :options="options"
            class="ly-tab"
            v-if="hackReset"
            @change="changeLyTabItem"
        />
        <section class="ticket-container">
            <div class="item" v-for="(item,index) in movieDaySchedule" :key="index">
                <div class="left">
                    <span class="start-date">{{ item.show_time }}</span>
                    <span class="end-date">{{ endDate(item) }}散场</span>
                </div>
                <div class="center">
                    <div class="info">
                        <span class="language">{{ item.language }}3D</span>
                        <span class="hall">{{ item.hall_name }}</span>
                    </div>
                    <div class="price">{{ item.price.toFixed(2) }}元</div>
                </div>
                <div class="right">
                    <span class="buy-btn"
                          @click="$router.push({path:'/select_seat',query:{cinema_id:item.cinema_id,movie_id:item.movie_id,id:item.id,}})">购票</span>
                </div>
            </div>
        </section>
    </section>
</template>

<script>
import {selectCinemaInfo, selectCinemaScheduleInfo, selectCinemaMovieScheduleInfo} from "@/api";
import {formatDate} from "@/assets/js/util";

export default {
    name: "CinemaDetail",
    data() {
        return {
            initMovieId: 0,
            //当前影院信息
            cinemaInfo: {},
            //影院的电影信息
            hasMovieInfo: [],
            //所有的电影安排
            allMovieSchedule: [],
            //电影某天的安排
            movieDaySchedule: [],
            hackReset: false,
            carouselReset: true,
            movieIndex: 0,
            selectedId: 0,
            items: [],
            options: {
                activeColor: '#dd2727',
            },
        }
    },
    created() {
        this.loadCinemaInfo()
    },
    methods: {
        loadCinemaInfo() {
            if (this.$route.query.cinema_id) {
                selectCinemaInfo({id: this.$route.query.cinema_id}).then(res => {
                    if (res.data.code === 1) {
                        this.cinemaInfo = res.data.cinema
                    }
                }).catch(err => {
                    console.log(err)
                })
                selectCinemaScheduleInfo({cinemaId: this.$route.query.cinema_id}).then(res => {
                    if (res.data.code === 1) {
                        this.hasMovieInfo = res.data.schedule
                        if (this.$route.query.movie_id) {
                            this.hasMovieInfo.forEach((val, index) => {
                                if (Number(this.$route.query.movie_id) === val.movie_id) {
                                    this.initMovieId = index
                                    this.movieIndex = index
                                    this.carouselReset = false
                                    this.$nextTick(() => {
                                        this.carouselReset = true
                                    });
                                }
                            });
                        }
                        this.getMovieSchedule(this.hasMovieInfo[this.movieIndex].movie_id)
                    }
                }).catch(err => {
                    console.log(err)
                })
            }
        },
        getPoster(path) {
            return 'http://localhost:9090' + path
        },
        changeCarousel(index) {
            this.movieIndex = index;
            this.movieDaySchedule = [];
            this.selectedId = 0;
            this.getMovieSchedule(this.hasMovieInfo[index].movie_id)
        },
        //切换日期
        changeLyTabItem(item) {
            this.movieDaySchedule = [];
            this.allMovieSchedule.movieInfo.forEach((value) => {
                if (value.show_date === item.date) {
                    this.movieDaySchedule.push(value);
                }
            });
            this.movieDaySchedule.sort((a, b) => {
                return new Date(a.show_date + ',' + a.show_time) - new Date(b.show_date + ',' + b.show_time);
            });
        },
        getMovieSchedule(movieId) {
            selectCinemaMovieScheduleInfo({
                cinemaId: this.$route.query.cinema_id,
                movieId: movieId
            }).then(res => {
                let movieScheduleInfo = res.data.movieSchedules
                let movieDate = [];
                let movieInfo = [];
                movieScheduleInfo.forEach((value) => {
                    if (new Date() - new Date(value.show_date + ',' + value.show_time) <= 0) {
                        let flag = true;
                        movieDate.forEach((val) => {
                            if (val.date === value.show_date) {
                                flag = false;
                            }
                        });
                        if (flag) {
                            movieDate.push({
                                label: formatDate(new Date(value.show_date), true),
                                date: value.show_date
                            });
                        }
                        movieInfo.push({
                            cinema_id: value.cinema_id,
                            movie_id: value.movie_id,
                            id: value.id,
                            show_date: value.show_date,
                            show_time: value.show_time,
                            language: value.language,
                            movie_long: value.movie_long,
                            hall_name: value.hall_name,
                            price: value.price
                        })
                    }
                })
                movieDate.sort((a, b) => {
                    return a.date - b.date;
                })
                movieInfo.sort((a, b) => {
                    return a.date - b.date;
                })
                this.allMovieSchedule = {movieDate, movieInfo}
                this.items = this.allMovieSchedule.movieDate;
                this.hackReset = false;
                this.$nextTick(() => {
                    this.hackReset = true;
                });
                this.allMovieSchedule.movieInfo.forEach((value) => {
                    if (value.show_date === this.allMovieSchedule.movieDate[0].date) {
                        this.movieDaySchedule.push(value);
                    }
                });
                this.movieDaySchedule.sort((a, b) => {
                    return new Date(a.show_date + ',' + a.show_time) - new Date(b.show_date + ',' + b.show_time);
                });
            }).catch(err => {
                console.log(err)
            })
        },
        //影片结束时间
        endDate(item) {
            let h = parseInt(Number(item.show_time.split(':')[0]) + (parseInt(item.movie_long) / 60));
            let m = Number(item.show_time.split(':')[1]) + parseInt(item.movie_long) % 60;
            if (m > 59) {
                return ((h + parseInt(m / 60)) < 10 ? '0' + (h + parseInt(m / 60)) : (h + parseInt(m / 60))) + ':' + ((m % 60) < 10 ? '0' + (m % 60) : (m % 60));
            } else {
                return (h < 10 ? '0' + h : h) + ':' + (m < 10 ? '0' + m : m);
            }
        }
    },
}
</script>

<style scoped lang="stylus">
#cinema-detail
    width 100%
    height 100%

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
            font-size 1.25rem
            text-align center
            line-height 1.25rem

    .cinema-info
        margin-top 3rem
        color #888
        display flex
        flex-flow column
        padding 1.5rem

        .name
            color #000
            font-size 1.5rem
            font-weight 700
            margin-bottom .85rem

        .address, .tel
            margin-bottom .32rem
            font-size .725rem
            line-height .725rem
            display flex
            letter-spacing .12rem
            align-items flex-start

            .icon
                font-size 1.125rem
                margin-right .28rem

    .movie-info
        height 3rem
        display flex
        flex-flow column
        align-items center

        .arrow
            border .2rem solid transparent
            border-bottom .2rem solid #fff
            margin-top -.38rem
            z-index 999

        .main
            font-size .875rem

            .name
                line-height .875rem
                font-weight 600

            .score
                color #ffb400
                margin-left .25rem
                font-size 1.4rem

                .num
                    font-size .887rem
                    font-weight 700
                    font-family PingFangSC-Regular, Hiragino Sans GB, sans-serif

        .intro
            font-size .875rem
            color #888

            .split
                margin 0 .08rem

    .el-carousel
        background linear-gradient(to bottom, #242342 0%, #11182B 100%)

        .el-carousel__item
            margin-top .25rem
            height 90%

            a
                font-size .3125rem
                padding-top .04rem

                img
                    width 100%
                    height 100%
                    box-sizing border-box
                    border 0.06rem solid #fff

    .ly-tab
        width 100%
        color #000
        border none

    .ticket-container
        padding .25rem

        .item
            display flex
            justify-content space-around
            align-items center
            border-bottom 0.02rem solid #f1f1f1
            padding .25rem 0

            .left
                flex 1
                display flex
                flex-flow column
                justify-content center

                .start-date
                    font-weight bolder
                    font-size .875rem
                    margin-bottom .12rem

                .end-date
                    color #888
                    font-size .675rem

            .center
                flex 3
                display flex
                justify-content space-between
                align-items center
                margin 0 .25rem

                .info
                    display flex
                    flex-flow column
                    justify-content center
                    align-items center
                    margin-left .25rem

                    .language
                        font-size .875rem
                        margin-bottom .22rem

                    .hall
                        color #888
                        font-size .875rem

                .price
                    color #dd2727
                    margin-right .25rem

            .right
                flex 1
                text-align center
                border 0.0125rem solid #dd2727
                padding .12rem 0
                border-radius .5rem
                color #dd2727
                font-size .787rem
</style>