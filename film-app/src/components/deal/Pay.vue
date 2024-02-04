<template>
    <section id="pay">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="back"
                      style="position: absolute;left: .5rem;"></van-icon>
            <span class="name ellipsis">支付订单</span>
        </header>
        <main class="order-info">
            <div class="time-down">
                <div class="title">支付剩余时间</div>
                <van-count-down class="time" :time="time">
                    <template #default="timeData">
                        <span class="block">{{ timeData.minutes }}</span>
                        <span class="colon">:</span>
                        <span class="block">{{ timeData.seconds }}</span>
                    </template>
                </van-count-down>
            </div>
            <div class="order-num">
                <span class="left icon-shop"></span>
                <div class="right">
                    <div class="total">¥{{ Number(this.$cookies.get('total_price')).toFixed(2) }}</div>
                    <div class="num">订单编号：{{ this.$cookies.get('order_num') }}</div>
                </div>
            </div>
        </main>
        <div class="pay-type">
            <div class="wechat type"><span class="icon icon-wechat"></span>
                <p>微信支付</p><span
                    :class="[{'icon-circle-selected-fill':selectPayType},{'icon-circle-unselect':!selectPayType}]"
                    @click="selectPayType?selectPayType:selectPayType=!selectPayType"></span></div>
            <div class="alipay type"><span class="icon icon-alipay"></span>
                <p>支付宝</p><span
                    :class="[{'icon-circle-selected-fill':!selectPayType},{'icon-circle-unselect':selectPayType}]"
                    @click="!selectPayType?selectPayType:selectPayType=!selectPayType"></span></div>
        </div>
        <footer class="bottom">
            <van-button color="#fe9900" round type="primary" block @click="handlePay">确认支付</van-button>
        </footer>
    </section>
</template>

<script>
import {insertOrder, selectOneSchedule, updateScheduleSeat} from "@/api";

export default {
    name: "Pay",
    data() {
        return {
            scheduleInfo: {},
            seatInfo: [],
            selectPayType: true,//微信
            time: 30 * 60 * 1000
        }
    },
    created() {
        this.loadInfo()
        this.time = this.$cookies.get('time')
    },
    methods: {
        loadInfo() {
            selectOneSchedule({id: this.$route.query.id}).then(res => {
                if (res.data.code === 1) {
                    this.scheduleInfo = res.data.schedule;
                    this.seatInfo = this.scheduleInfo.seatInfo;
                    this.seatInfo = JSON.parse(this.seatInfo);
                }
            }).catch(err => {
                console.log(err)
            })
        },
        back() {
            this.$cookies.remove('time');
            this.$cookies.remove('order_num');
            this.$cookies.remove('total_price');
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
                    this.$cookies.remove('order_phone');
                    clearInterval(this.timer);
                    this.$notify({type: 'success', message: '取消订单'})
                    this.$router.go(-2);
                }
            }).catch(err => {
                console.log(err)
            })
        },
        handlePay() {
            let info;
            if (this.selectPayType) {
                info = '您的微信将支付￥' + (Number(this.$cookies.get('total_price')).toFixed(2)) + '元';
            } else {
                info = '您的支付宝将支付￥' + (Number(this.$cookies.get('total_price')).toFixed(2)) + '元';
            }
            this.$dialog.confirm({
                title: '支付提示',
                message: info,
            })
                .then(() => {
                    // on confirm
                    let seatArr = [];
                    if (this.$cookies.get('seat_1')) {
                        seatArr.push(Number(this.$cookies.get('seat_1')));
                    }
                    if (this.$cookies.get('seat_2')) {
                        seatArr.push(Number(this.$cookies.get('seat_2')));
                    }
                    if (this.$cookies.get('seat_3')) {
                        seatArr.push(Number(this.$cookies.get('seat_3')));
                    }
                    if (this.$cookies.get('seat_4')) {
                        seatArr.push(Number(this.$cookies.get('seat_4')));
                    }
                    let orderInfo = {
                        userId: this.$cookies.get('UID'),
                        scheduleId: this.$route.query.id,
                        phone: this.$cookies.get('order_phone'),
                        orderDate: new Date().getFullYear() + '-' + (Number(new Date().getMonth()) + 1) + '-' + new Date().getDate(),
                        num: seatArr.length,
                        price: this.$cookies.get('total_price'),
                        seatInfo: JSON.stringify(seatArr),
                        payType: this.selectPayType ? 0 : 1
                    }
                    insertOrder(orderInfo).then(res => {
                        if (res.data.code === 1) {
                            this.$dialog.alert({
                                title: '取票码',
                                message: res.data.phoneCode,
                                theme: 'round-button',
                            }).then(() => {
                                // on close
                            });
                        }
                        this.$cookies.remove('seat_1');
                        this.$cookies.remove('seat_2');
                        this.$cookies.remove('seat_3');
                        this.$cookies.remove('seat_4');
                        this.$cookies.remove('seat_count');
                        this.$cookies.remove('order_phone');
                        this.$cookies.remove('time');
                        this.$cookies.remove('order_num');
                        this.$cookies.remove('total_price');
                        clearInterval(this.timer);
                        this.$router.push('/index');
                    }).catch(err => {
                        console.log(err)
                    })
                })
                .catch(() => {
                    // on cancel
                });
        }
    }
}
</script>

<style scoped>
#pay {
    width: 100%;
    height: 100%;
    color: #000;
    font-size: 1rem;
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
    background-color: #2d98f3;
    color: #fff;
}

.order-info {
    margin-top: 4rem;
    height: 14rem;
}

.time-down {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-flow: column;
    padding-bottom: 1.25rem;
    border-bottom: .25rem dashed #ccc;
    margin: 0 .25rem;
}

.title {
    padding: .5rem 0;
    font-size: 1.28rem;
}

.time {
    display: flex;
    justify-content: center;
    align-items: center;
}

.time span {
    font-size: 1.25rem;
    display: inline-block;
    text-align: center;
    width: 2rem;
    background-color: #888888;
    color: #fff;
}

.time p {
    padding: 0 .12rem;
}

.colon {
    display: inline-block;
    margin: 0 .25rem;
}

.block {
    display: inline-block;
    width: 1rem;
    color: #fff;
    font-size: 1rem;
    text-align: center;
}

.order-num {
    display: flex;
    justify-content: center;
    align-items: center;
    padding-top: .4rem;
    position: relative;
}

.left {
    font-size: 6rem;
    position: absolute;
    left: 3rem;
    top: 1.4rem;
}

.right {
    position: absolute;
    left: 10rem;
    top: 2.4rem;
    display: flex;
    flex-flow: column;
}

.total {
    font-size: 1.255rem;
    margin-bottom: .5rem;
}

.num {
    font-size: 1rem;
    letter-spacing: .02rem;
}

.pay-type {
    border-top: .66rem solid #f1f1f1;
    position: fixed;
    width: 100%;
    left: 0;
    top: 18rem;
    bottom: 0;
    background-color: #f1f1f1;
}

.type {
    padding: 1.25rem .83rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
    border-bottom: .04rem solid #f1f1f1;
    background-color: #fff;
}

.type span {
    font-size: 3rem;
}

.type p {
    position: absolute;
    left: 5rem;
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
}

</style>