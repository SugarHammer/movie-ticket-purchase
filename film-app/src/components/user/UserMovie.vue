<template>
    <section id="my-movie">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="$router.go(-1)" style="position: absolute;left: .5rem;"/>
            <span class="name">想看的电影</span>
        </header>
        <main class="movie">
            <div class="want">
                <movie-item :movie-list="wishMovie"></movie-item>
                <div class="tips" v-if="wishMovie.length===0">
                    <van-empty description="暂无想看的影片" />
                </div>
            </div>
        </main>
    </section>
</template>

<script>
import MovieItem from "@/components/movie/MovieItem";
import {getUserLike} from "@/api";
export default {
    name: "UserMovie",
    components: {MovieItem},
    data(){
        return{
            wishMovie:[],
        }
    },
    created() {
        this.loadWishMovieInfo()
    },
    methods:{
        loadWishMovieInfo(){
            if (this.$route.query.UID){
                getUserLike({userId:this.$route.query.UID}).then(res=>{
                    if (res.data.code === 1){
                        this.wishMovie = res.data.movies
                        this.wishMovie.sort((a,b)=>{
                            return new Date(a.public_date)-new Date(b.public_date);
                        })
                    }
                }).catch(err=>{
                    console.log(err)
                })
            }
        }
    }
}
</script>

<style scoped lang="stylus">
#my-movie
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
    .movie
        padding-top 3rem
</style>