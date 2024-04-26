<template>
    <section>
        <header>
            <div class="nav">
                <span class="location">深圳</span>
                <span class="title">影院</span>
                <van-icon class="search" name="search" @click="$router.push('search')"></van-icon>
            </div>
        </header>
        <main class="main_content">
            <div class="item" v-for="(item,index) in cinemaList" :key="index"
                 @click="$router.push({path: '/cinema_detail',query:{cinema_id:item.id}})">
                <div class="left" style="width: 100%;">
                    <div class="name">{{ item.name }}</div>
                    <div class="address">{{ item.specified_address }}</div>
                    <div class="label_block"><span>小吃</span><span>4D厅</span><span>巨幕厅</span><span>杜比全景声厅</span></div>
                    <van-divider/>
                </div>
            </div>
        </main>
    </section>
</template>

<script>
import {selectCinemaList} from "@/api";

export default {
    name: "Cinema",
    data() {
        return {
            cinemaList:[]
        }
    },
    created() {
        this.loadCinemaInfo()
    },
    methods: {
        loadCinemaInfo(){
            selectCinemaList().then(res=>{
                // console.log(res)
                if (res.data.code === 1){
                    this.cinemaList = res.data.cinemas
                }
            }).catch(err=>{
                console.log(err)
            })
        }
    }
}
</script>

<style scoped>
.nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 3rem;
    background-color: rgba(3,3,3,0.67);
    color: white;
    position: relative;
    font-size: 1.25rem;
}

.location {
    width: 20%;
    text-indent: .975rem;
}

.title {
    width: 60%;
    text-align: center;
    font-size: 1.25rem;
    line-height: .38rem;
}

.search {
    width: 20%;
    font-size: 2rem;
    text-align: center;
}

.main_content {
    font-size: 1rem;
    margin-top: 1rem;
    padding-bottom: .8rem;
}

.item {
    display: flex;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    padding: .25rem 1.35rem;
    width: 100%;
    margin-bottom: .25rem;
}

.name {
    font-size: 1.25rem;
    line-height: 1.36rem;
    margin-bottom: .45rem;
    font-weight: 700;
}

.address {
    font-size: 1rem;
    line-height: 1.125rem;
    color: #666;
    margin-bottom: .45rem;
}

.label_block {
    display: flex;
}

.label_block span {
    padding: .06rem;
    font-size: .5rem;
    border: .01rem solid #f90;
    margin-right: .4rem;
    border-radius: .04rem;
    color: #f90;
}

</style>
