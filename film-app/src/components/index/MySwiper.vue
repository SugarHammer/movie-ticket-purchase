<template>
    <van-swipe :autoplay="3000">
        <van-swipe-item v-for="(image, index) in images" :key="index">
            <img v-lazy="image" style="width: 100%;height: 10rem"/>
        </van-swipe-item>
    </van-swipe>
</template>

<script>
export default {
    name: "MySwiper",
    data() {
        return {
            images: [
                require('../../assets/img/l1.jpg'),
                require('../../assets/img/l2.jpg'),
                require('../../assets/img/l3.jpg'),
                require('../../assets/img/l4.jpg'),
                require('../../assets/img/l5.jpg')
            ],
        }
    }
}
</script>

<style scoped>

</style>