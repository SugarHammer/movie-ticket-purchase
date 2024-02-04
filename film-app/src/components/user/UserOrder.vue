<template>
    <section id="my-order">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="$router.go(-1)" style="position: absolute;left: .5rem;"/>
            <span class="name">我的订单</span>
        </header>
        <main class="order-container" v-if="myOrderInfo.length">
            <div class="item" v-for="(item,value) in myOrderInfo">
                <div class="item-top">
                    <span class="cinema-name">{{item.cinema_name}}</span><span class="status">已完成</span>
                </div>
                <div class="item-center">
                    <div class="left">
                        <van-image :src="getPoster(item.poster)" alt="" width="5rem" height="7rem"/>
                    </div>
                    <div class="right">
                        <div class="movie">{{item.movie_name}}<span class="num">{{item.num}}张</span></div>
                        <div class="date"><span class="day">{{item.show_date.split('-')[1]+'月'+item.show_date.split('-')[2]+'日'}}</span><span class="time">{{item.show_time}}</span></div>
                        <div class="cinema"><span class="hall">{{item.hall_name}}</span><span class="seat" v-for="(itemSeat,valueSeat) in JSON.parse(item.seat_info)" :key="valueSeat">{{formatSeat(itemSeat)}}</span></div>
                    </div>
                </div>
                <div class="item-bottom">
                    <div class="price">总价:<span>{{item.price}}元</span></div>
                    <div style="color: red">取票码:<span>{{item.phone_code}}</span></div>
                </div>
            </div>
        </main>
        <footer v-else>
            <van-empty description="暂无订单" />
        </footer>
    </section>
</template>

<script>
import {selectOrderInfo} from "@/api";

export default {
    name: "UserOrder",
    data(){
        return{
            myOrderInfo:[],
        }
    },
    created() {
        this.loadInfo();
    },
    methods:{
        loadInfo(){
            if (this.$route.query.UID){
                selectOrderInfo({userId:this.$route.query.UID}).then(res=>{
                    if (res.data.code === 1){
                        this.myOrderInfo = res.data.orderInfo
                        this.myOrderInfo.sort((a,b)=>{
                            return new Date(b.order_date)-new Date(a.order_date);
                        });
                    }
                }).catch(err=>{
                    console.log(err)
                })
            }
        },
        //格式座位
        formatSeat(num){
            if (num%10===0){
                return (num/10)+'排'+10+'座';
            } else{
                return parseInt(num/10)+1+'排'+num%10+'座';
            }
        },
        getPoster(path){
            return 'http://localhost:9090' + path
        }
    }
}
</script>

<style scoped lang="stylus">
#my-order
    width 100%
    height 100%
    color #000
    .top
        width 100%
        height 3rem
        display flex
        justify-content center
        align-items center
        top 0
        left 0
        background-color #dd2727
        color #fff
        .name
            width 60%
            text-align center
            font-size 1.345rem
    .order-container
        display flex
        flex-flow column
        width 100%
        background #f1f1f1
        .item
            display flex
            justify-content center
            align-items center
            flex-flow column
            padding .5rem
            background #fff
            border-bottom .03rem solid #f1f1f1
            .item-top, .item-center,.item-bottom
                width 100%
                display flex
                justify-content space-between
                align-items center
                font-size .875rem
                color #333
            .item-top
                padding-bottom .12rem
            .item-center
                width 100%
                justify-content flex-start
                padding .12rem 0
                .left
                    width 5rem
                .right
                    align-self flex-start
                    .movie
                        font-weight bolder
                        font-size 1.25rem
                        margin .2rem
                        margin-bottom .5rem
                        .num
                            margin-left 1.12rem
                    .date,.cinema,.price
                        margin .2rem
                        span
                            margin-right .52rem
            .item-bottom
                margin-top .12rem
</style>