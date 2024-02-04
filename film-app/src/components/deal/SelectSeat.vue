<template>
    <section id="select-seat">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="$router.go(-1)" style="position: absolute;left: .5rem;"/>
            <span class="name ellipsis">{{ cinemaInfo.name }}</span>
        </header>
        <main class="seat-block">
            <div class="screen">{{ scheduleInfo.hallName }}银幕</div>
            <div class="center">银幕中央</div>
            <div class="screen-line"></div>
            <div class="row-container">
                <span>1</span>
                <span>2</span>
                <span>3</span>
                <span>4</span>
                <span>5</span>
                <span>6</span>
            </div>
            <div class="seat-container">
                <div class="row" v-if="hackReset" v-for="(itemI,indexI) in seat" :key="indexI">
                    <span class="seat" v-for="(itemJ,indexJ) in itemI" :key="indexJ"
                          :class="{'icon-sold-seat':itemJ===1,'icon-empty-seat':itemJ===0,'icon-selected-seat':itemJ===2}"
                          @click.prevent="handleSelectSeat(indexI,indexJ)"></span>
                </div>
            </div>
            <div class="seat-example">
                <div class="example empty-example"><span class="icon icon-empty-seat"></span>可选</div>
                <div class="example sold-example"><span class="icon icon-sold-seat"></span>不可选</div>
                <div class="example selected-example"><span class="icon icon-selected-seat"></span>已选</div>
            </div>
        </main>
        <footer class="bottom">
            <div class="movie-info">
                <div class="name">{{ movieInfo.name }}</div>
                <div class="intro"><span class="date">{{ scheduleInfo.showDate|dateFilter }}</span><span
                    class="time">{{ scheduleInfo.showTime }}</span><span class="language">{{ movieInfo.language }}3D</span>
                </div>
            </div>
            <div class="title" v-if="selectedSeat">已选座位</div>
            <div class="selected-seat" v-if="selectedSeat">
                <div class="seat-item" v-for="(item,index) in selectedSeatInfo" :key="index">
                    <div class="left">
                        <span class="seat">{{ item[0] + 1 }}排{{ item[1] + 1 }}座</span>
                        <span class="price">{{ (scheduleInfo.price).toFixed(2) }}元</span>
                    </div>
                    <span class="right icon-close" @click="cancelSelectedSeat(item[0],item[1])"></span>
                </div>
            </div>
            <span class="btn" v-if="!selectedSeat">请先选座</span>
            <span class="btn active" v-else @click="enterSeat">确认选座</span>
        </footer>
    </section>
</template>

<script>
import {Toast} from "vant";
import {selectCinemaInfo, selectMovieInfo, selectOneSchedule, updateScheduleSeat} from "@/api";

export default {
    name: "SelectSeat",
    data() {
        return {
            cinemaInfo: {},
            movieInfo: {},
            scheduleInfo: {},
            seatInfo: '',
            seatCount: 0,
            selectedSeat: false,
            hackReset: true,
            selectedSeatInfo: [],
            seat: [
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            ],
        }
    },
    created() {
        this.loadInfo()
    },
    methods: {
        loadInfo() {
            if (this.$route.query.cinema_id && this.$route.query.movie_id && this.$route.query.id) {
                selectMovieInfo({id: this.$route.query.movie_id}).then(res => {
                    if (res.data.code === 1) {
                        this.movieInfo = res.data.movie
                    }
                }).catch(err => {
                    console.log(err)
                })
                selectCinemaInfo({id: this.$route.query.cinema_id}).then(res => {
                    if (res.data.code === 1) {
                        this.cinemaInfo = res.data.cinema
                    }
                }).catch(err => {
                    console.log(err)
                })
                selectOneSchedule({id: this.$route.query.id}).then(res => {
                    if (res.data.code === 1) {
                        this.scheduleInfo = res.data.schedule
                        this.seatInfo = this.scheduleInfo.seatInfo
                        if (this.seatInfo) {
                            this.seatInfo = JSON.parse(this.seatInfo);
                            this.seatInfo.forEach((value) => {
                                if (value % 10 !== 0) {
                                    this.seat[parseInt(value / 10)][value % 10 - 1] = 1;
                                } else {
                                    this.seat[parseInt(value / 10) - 1][9] = 1;
                                }
                            });
                        }
                    }
                }).catch(err => {
                    console.log(err)
                })
            }
        },
        //选择座位
        handleSelectSeat(i, j) {
            if (this.seatCount === 4 && this.seat[i][j] === 0) {
                Toast.fail('一次最多选择4个座位！');
            } else {
                if (this.seat[i][j] === 0) {
                    this.seat[i][j] = 2
                    this.selectedSeatInfo.push([i, j])
                    this.seatCount += 1
                    if (!this.selectedSeat) {
                        this.selectedSeat = true;
                    }
                } else if (this.seat[i][j] === 2) {
                    this.seat[i][j] = 0
                    this.seatCount -= 1
                    let current
                    this.selectedSeatInfo.forEach(((value, index) => {
                        if (i === value[0] && j === value[1]) {
                            current = index
                        }
                    }))
                    this.selectedSeatInfo.splice(current, 1)
                    if (this.seatCount === 0) {
                        this.selectedSeat = false
                    }
                }
                this.hackReset = false
                this.$nextTick(() => {
                    this.hackReset = true
                })
            }
        },
        //取消选座
        cancelSelectedSeat(i, j) {
            this.seat[i][j] = 0;
            this.seatCount -= 1;
            let current;
            this.selectedSeatInfo.forEach((value, index) => {
                if (i === value[0] && j === value[1]) {
                    current = index;
                }
            });
            this.selectedSeatInfo.splice(current, 1);
            if (this.seatCount === 0) {
                this.selectedSeat = false;
            }
        },
        //  确认选座
        enterSeat() {
            if (this.$cookies.get('UID')){
                if (!this.seatInfo) {
                    this.seatInfo = [];
                }
                this.selectedSeatInfo.forEach((value,index)=>{
                    this.seatInfo.push(value[0]*10+value[1]+1);
                    this.$cookies.set('seat_'+(index+1),value[0]*10+value[1]+1);
                });
                this.$cookies.set('seat_count',this.selectedSeatInfo.length);
                this.seatInfo = JSON.stringify(this.seatInfo);
                let scheduleInfo = {id:this.$route.query.id,seatInfo:this.seatInfo}
                updateScheduleSeat(scheduleInfo).then(res=>{
                    if (res.data.code === 1){
                            this.$router.push({path:'/submit_order',query:{cinema_id:this.$route.query.cinema_id,movie_id:this.$route.query.movie_id,id:this.$route.query.id,}});
                            this.$notify({type:'success',message:'锁定座位信息'})
                        }
                }).catch(err=>{
                    console.log(err)
                })
            } else{
                this.$router.push('/login');
            }
        },
    },
    filters: {
        dateFilter(props) {
            props = props + '';
            return props.split('-')[0] + '年' + props.split('-')[1] + '月' + props.split('-')[2] + '日';
        }
    }
}
</script>

<style scoped lang="stylus">
#select-seat
    width 100%
    height 100%
    color #000
    font-size .3125rem
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
            text-align center
            font-size .875rem
    .seat-block
        width 100%
        background: #f1f1f1
        position absolute
        left 0
        top 3rem
        bottom 0
        .screen
            width 22rem
            margin 0 auto
            text-align center
            background #dcdcdc
            font-size .875rem
            padding .08rem 0
            border-bottom-left-radius .4rem
            border-bottom-right-radius .4rem
            position relative
            left .28rem
        .center
            font-size .2rem
            width 1rem
            position absolute
            text-align center
            padding .08rem .1rem
            top 1.2rem
            left 50%
            margin-left -.3125rem
            letter-spacing .02rem
            color #888
            border-radius .12rem
        .screen-line
            width 0
            height 4.8rem
            border .02rem dashed #dcdcdc
            position absolute
            top 1.6rem
            left 50%
            margin-left .28rem
        .row-container
            position absolute
            top 8rem
            left .5rem
            width 1rem
            height 16rem
            padding .6rem 0
            background-color rgba(168,168,168,.8)
            display flex
            flex-flow column
            text-align center
            justify-content space-between
            align-items center
            color #fff
            border-radius .2rem
            font-size 1rem
        .seat-container
            padding .4rem 0
            position absolute
            top 8rem
            left 1.8rem
            .row
                margin-bottom 1.5rem
                .seat
                    font-size 1.5rem
                    margin-left .75rem
        .seat-example
            position absolute
            top 6rem
            width 80%
            margin-left 10%
            display flex
            justify-content center
            align-items center
            .example
                font-size .875rem
                color #888
                margin-right .8rem
                display flex
                justify-content center
                align-items center
                .icon
                    font-size 1.5rem
                    margin-right .42rem
    .bottom
        position fixed
        width 100%
        left 0
        bottom 0
        background #fff
        z-index 999
        display flex
        flex-flow column
        padding .25rem
        box-sizing border-box
        .movie-info
            display flex
            flex-flow column
            padding .25rem
            .name
                margin-bottom .25rem
                font-size 1.345rem
                font-weight 700
            .intro
                font-size .675rem
                margin-bottom .25rem
                color #888
                span
                    margin-right .12rem
        .title
            font-size 1rem
            font-weight 700
            margin-bottom .25rem
        .btn
            height 2rem
            line-height 2rem
            background-color #f7dbb3
            color #fff
            text-align center
            border-radius 1rem
            font-size 1.125rem
            &.active
                background-color #fe9900
        .selected-seat
            height 2.5rem
            display flex
            .seat-item
                width 25%
                height 2.5rem
                display flex
                box-sizing border-box
                justify-content space-around
                align-items center
                border 0.02rem solid #f1f1f1
                margin-right .25rem
                &:last-child
                    margin-right 0
                .left
                    display flex
                    flex-flow column
                    font-size .25rem
                    .seat
                        font-size .875rem
                        color #666
                        margin-bottom .22rem
                    .price
                        color #dd2727
                        font-size .24rem
                .right
                    font-size .25rem
</style>