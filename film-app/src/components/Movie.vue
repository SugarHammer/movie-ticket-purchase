<template>
    <section class="movie_container">
        <header class="tab_header">
            <span class="location">深圳</span>
            <span :class="['normal',{'active':isHotMovie}]" @click="optionMovieType(true)">正在热映</span>
            <span :class="['normal',{'active':!isHotMovie}]" @click="optionMovieType(false)">即将上映</span>
            <van-icon class="search" name="search" @click="$router.push('search')"></van-icon>
        </header>
        <main class="main_content">
            <div class="panel" v-show="isHotMovie">
                <movie-item :movie-list="movieList"></movie-item>
            </div>
            <div class="panel" v-show="!isHotMovie">
                <movie-item :movie-list="notShowMovieList"></movie-item>
            </div>
        </main>
    </section>
</template>

<script>
import MovieItem from "@/components/movie/MovieItem";
import {selectMovieSchedule} from "@/api";

export default {
    name: "Movie",
    components: {MovieItem},
    data() {
        return {
            isHotMovie: true,
            //热门电影列表
            movieList: [],
            //未上映电影列表
            notShowMovieList: [],
        }
    },
    created() {
        if (this.$route.query.hotMovie == 0) {
            this.isHotMovie = false;
        }
        this.loadMovieInfo()
    },
    methods: {
        optionMovieType(flag) {
            this.isHotMovie = flag;
            window.scroll(0, 0);
        },
        loadMovieInfo() {
            selectMovieSchedule().then(res => {
                if (res.data.code === 1) {
                    let movieInfo = res.data.schedules
                    let time = new Date()
                    movieInfo.forEach((value) => {
                        if ((time - new Date(value.show_date)) >= 0) {
                            this.movieList.push(value)
                        } else {
                            this.notShowMovieList.push(value)
                        }
                    })
                    this.movieList.sort((a, b) => {
                        return b.score - a.score
                    })
                    this.notShowMovieList.sort((a, b) => {
                        return b.score - a.score
                    })
                }
            }).catch(err => {
                console.log(err)
            })
        },
    }
}
</script>

<style scoped>
.tab_header {
    display: flex;
    justify-content: space-around;
    align-items: center;
    height: 3rem;
    width: 100%;
    font-size: 1.25rem;
    background-color: rgba(3, 3, 3, 0.67);
    color: white;
}

.normal {
    opacity: .6;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 80%;
}

.active {
    color: #fff;
    border-bottom: .2rem solid #fff;
    opacity: 1;
}

.search {
    font-size: 1.825rem;
}

.panel {
    width: 100%;
    padding-bottom: 3rem;
}


</style>