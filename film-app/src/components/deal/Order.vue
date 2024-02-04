<template>
    <section id="order">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="backSeatInfo"
                      style="position: absolute;left: .5rem;"></van-icon>
            <span class="name">确认订单</span>
            <van-count-down class="time-down" :time="time">
                <template #default="timeData">
                    <span class="icon-time"></span>
                    <span class="block">{{ timeData.minutes }}</span>
                    <span class="colon">:</span>
                    <span class="block">{{ timeData.seconds }}</span>
                </template>
            </van-count-down>
        </header>
        <div class="order-info">
            <div class="info movie-name">{{ movieInfo.name }}</div>
            <div class="info date"><span class="day">{{ scheduleInfo.showDate | dateFilter }}</span><span class="time">{{
                    scheduleInfo.showTime
                }}</span><span
                class="language">{{ movieInfo.language }}3D</span></div>
            <div class="info cinema">{{ cinemaInfo.name }}</div>
            <div class="info location">
                <span class="hall">{{ scheduleInfo.hallName }}</span>
                <span class="seat"
                      v-if="this.$cookies.get('seat_1')">{{
                        formatSeatInfo(Number(this.$cookies.get('seat_1')))
                    }}</span>
                <span class="seat"
                      v-if="this.$cookies.get('seat_2')">{{
                        formatSeatInfo(Number(this.$cookies.get('seat_2')))
                    }}</span>
                <span class="seat"
                      v-if="this.$cookies.get('seat_3')">{{
                        formatSeatInfo(Number(this.$cookies.get('seat_3')))
                    }}</span>
                <span class="seat"
                      v-if="this.$cookies.get('seat_4')">{{
                        formatSeatInfo(Number(this.$cookies.get('seat_4')))
                    }}</span>
            </div>
        </div>
        <main class="content">
            <div class="tips">
                <p class="title">购票须知</p>
                <div class="intro">
                    <p>1.请确认场次和时间无误，购买成功后将不予以退还</p>
                    <p>2.由于设备故障等不可抗力因素，存在少量场次取消的情况，会进行退票退款</p>
                    <p>3.由于影院系统不稳定等因素，存在出票失败的情况，会进行退款</p>
                    <p>4.购票成功后，会收到取票码</p>
                    <p>5.下单即代表你同意我们的用户服务协议</p>
                </div>
            </div>
            <div class="phone-container">
                <div class="phone">
                    <van-field v-model="phone" type="tel" label="手机号"/>
                </div>
                <div class="text">提示：手机号用于生成订单，并以短信方式将取票码发送到手机上</div>
            </div>
        </main>
        <footer class="bottom">
            <div class="intro">
                <div class="text">不支持退票、改签</div>
                <div class="total">应支付<span
                    class="money">{{
                        (Number(scheduleInfo.price) * Number(this.$cookies.get('seat_count'))).toFixed(2)
                    }}</span><span>元</span>
                </div>
            </div>
            <van-button color="#fe9900" round type="primary" block @click="handleSubmitOrder">提交订单</van-button>
        </footer>
    </section>
</template>

<script>
import {selectCinemaInfo, selectMovieInfo, selectOneSchedule, selectUserInfo, updateScheduleSeat} from "@/api";

export default {
    name: "Order",
    data() {
        return {
            cinemaInfo: {},
            movieInfo: {},
            scheduleInfo: {},
            seatInfo: '',
            phone: '',
            time: 15 * 60 * 1000,
        }
    },
    created() {
        this.loadInfo()
    },
    methods: {
        loadInfo() {
            if (this.$route.query.cinema_id && this.$route.query.movie_id && this.$route.query.id && this.$cookies.get('UID')) {
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
                        this.seatInfo = JSON.parse(this.seatInfo);
                    }
                }).catch(err => {
                    console.log(err)
                })
                selectUserInfo({id: this.$cookies.get('UID')}).then(res => {
                    if (res.data.code === 1) {
                        this.phone = res.data.user.phone;
                    }
                }).catch(err => {
                    console.log(err)
                })

            }
        },
        //座位信息
        formatSeatInfo(seatNum) {
            if (seatNum % 10 === 0) {
                return seatNum / 10 + '排' + 10 + '座';
            } else {
                return parseInt(seatNum / 10) + 1 + '排' + seatNum % 10 + '座';
            }
        },
        //回退座位信息
        backSeatInfo() {
            let currentIndex;
            if (this.$cookies.get('seat_1')) {
                this.seatInfo.forEach((value, index) => {
                    if (Number(this.$cookies.get('seat_1')) === value) {
                        currentIndex = index;
                    }
                });
                this.seatInfo.splice(currentIndex, 1);
            }
            if (this.$cookies.get('seat_2')) {
                this.seatInfo.forEach((value, index) => {
                    if (Number(this.$cookies.get('seat_2')) === value) {
                        currentIndex = index;
                    }
                });
                this.seatInfo.splice(currentIndex, 1);
            }
            if (this.$cookies.get('seat_3')) {
                this.seatInfo.forEach((value, index) => {
                    if (Number(this.$cookies.get('seat_3')) === value) {
                        currentIndex = index;
                    }
                });
                this.seatInfo.splice(currentIndex, 1);
            }
            if (this.$cookies.get('seat_4')) {
                this.seatInfo.forEach((value, index) => {
                    if (Number(this.$cookies.get('seat_4')) === value) {
                        currentIndex = index;
                    }
                });
                this.seatInfo.splice(currentIndex, 1);
            }
            let scheduleInfo = {id: this.$route.query.id, seatInfo: JSON.stringify(this.seatInfo)}
            updateScheduleSeat(scheduleInfo).then(res => {
                if (res.data.code === 1) {
                    this.$cookies.remove('seat_1');
                    this.$cookies.remove('seat_2');
                    this.$cookies.remove('seat_3');
                    this.$cookies.remove('seat_4');
                    this.$cookies.remove('seat_count');
                    clearInterval(this.timer);
                    this.$notify({type: 'success', message: '解除座位信息成功'})
                    this.$router.go(-1);
                }
            }).catch(err => {
                console.log(err)
            })
        },
        //提交订单
        handleSubmitOrder() {
            if (/^1[3|4|5|6|7|8][0-9]{9}$/.test(this.phone)) {
                this.$cookies.set('total_price', this.scheduleInfo.price * Number(this.$cookies.get('seat_count')))
                this.$cookies.set('time', this.time)
                this.$cookies.set('order_num', (Number(Math.random()) * 10000000000000).toFixed(0))
                this.$cookies.set('order_phone', this.phone)
                clearInterval(this.timer)
                this.$router.push({path: '/pay', query: {id: this.$route.query.id}})
            } else {
                this.$notify({type: 'warning', message: '手机格式不正确'})
            }
        },
    },
    filters: {
        dateFilter(date) {
            date = date + '';
            return date.split('-')[0] + '年' + date.split('-')[1] + '月' + date.split('-')[2] + '日';
        },
        timeFilter(time) {
            if (Number(time) <= 9) {
                return '0' + time;
            } else {
                return time;
            }
        }
    }
}
</script>

<style scoped>
#submit-order {
    width: 100%;
    height: 100%;
    color: #000;
    font-size: 1.25rem;
}

.top {
    width: 100%;
    height: 3rem;
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    background-color: #779db0;
    color: #fff;
}

.time-down {
    right: .5rem;
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1rem;
}

.colon {
    display: inline-block;
    margin: 0 4px;
}

.block {
    display: inline-block;
    width: 22px;
    color: #fff;
    font-size: 1rem;
    text-align: center;
}

.order-info {
    margin-top: 3rem;
    background-color: #779db0;
    color: #fff;
    padding: .3rem;
    height: 10rem;
}

.info {
    margin-bottom: .45rem;
    font-size: .875rem;
}

.info span {
    margin-right: .12rem;
}

.movie-name {
    font-size: 1.375rem;
    font-weight: 700;
}

.content {
    background-color: #f1f1f1;
    width: 100%;
    position: fixed;
    top: 12rem;
    left: 0;
    bottom: 0;
}

.phone-container {
    background-color: #fff;
    display: flex;
    flex-flow: column;
    margin-top: 1.12rem;
}

/deep/ .phone {
    width: 100%;
    box-sizing: border-box;
    height: 3rem;
    line-height: 2rem;
    display: flex;
    border-bottom: .02rem solid #f1f1f1;
}

.text {
    color: #dd2727;
    font-size: .85rem;
    line-height: .9rem;
    padding: .25rem .3rem;
    letter-spacing: .02rem;
}

.tips {
    display: flex;
    flex-flow: column;
    background-color: #fff;
    padding: 1.15rem;
}

.intro {
    width: 100%;
    margin-bottom: .55rem;
    justify-content: space-between;
    padding: .22rem 0;
}

.intro p {
    margin-bottom: .12rem;
    font-size: 1.25rem;
    color: #888;
    line-height: 1.25rem;
    letter-spacing: .12rem;
    text-align: justify;
}


.title {
    color: #333;
    font-size: 1.3125rem;
    font-weight: 700;
    padding-bottom: .575rem;
    border-bottom: .1rem solid #f1f1f1;
}

.bottom {
    border-top: .02rem solid #f1f1f1;
    position: fixed;
    width: 100%;
    left: 0;
    bottom: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-flow: column;
    padding: .25rem;
    box-sizing: border-box;
    z-index: 999;
    background-color: #fff;
}


.text {
    color: #ffb400;
}

.total {
    color: #888;
}

.money {
    font-size: 1.3125rem;
    color: #dd2727;
    margin-left: .12rem;
}

.money span {
    color: #dd2727;
    font-size: .42rem;
    margin-left: .12rem;
}


</style>