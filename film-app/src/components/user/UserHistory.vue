<template>
    <section id="my-history">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="$router.go(-1)" style="position: absolute;left: .5rem;"/>
            <span class="name">观看历史</span>
        </header>
        <main class="watched">
            <div class="item" v-for="(item,index) in watchedMovie" :key="index" @click="$router.push({path:'movie_detail',query:{'movie_id':item.movie_id}})">
                <van-image :src="getPoster(item.poster)" alt="" height="7rem" width="5rem"/>
                <div class="info">
                    <div class="name">{{item.name}}</div>
                    <div class="my-score">
                        <span>我评：</span>
                        <van-rate v-model="item.user_score" allow-half readonly color="#ffd21e"
                            void-icon="star" void-color="#eee"/>
                    </div>
                    <div class="my-comment ellipsis">{{item.content}}</div>
                </div>
            </div>
            <div class="tips" v-if="watchedMovie.length===0">
                <van-empty description="暂无观看历史" />
            </div>
        </main>
    </section>
</template>

<script>
import {getUserWatched} from "@/api";

export default {
    name: "UserHistory",
    data(){
        return{
            watchedMovie:[]
        }
    },
    created() {
        this.loadHistory()
    },
    methods:{
        loadHistory(){
            if (this.$route.query.UID){
                getUserWatched({userId:this.$route.query.UID}).then(res=>{
                    if (res.data.code === 1){
                        this.watchedMovie = res.data.info
                        this.watchedMovie.sort((a,b)=>{
                            return new Date(b.comment_date)- new Date(a.comment_date)
                        })
                    }
                }).catch(err=>{
                    console.log(err)
                })
            }
        },
        getPoster(path){
            return 'http://localhost:9090' + path
        }
    }
}
</script>

<style scoped lang="stylus">
#my-history
    width 100%
    height 100%
    color #000
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
            font-size 1.345rem
    .watched
        padding 0 .3rem
        padding-top 3rem
        .item
            display flex
            align-items center
            padding .5rem 0
            .info
                width 70%
                display flex
                flex-flow column
                padding .25rem
                font-size .8rem
                color #9d9d9d
                overflow hidden
                .name
                    font-weight 700
                    font-size 1.345rem
                    padding-bottom .2rem
                    color #333
                .my-score
                    display flex
                    align-items center
                    color #ffb400
                    padding-bottom .2rem
                    font-size .8rem
                    span
                        margin-right .22rem
</style>