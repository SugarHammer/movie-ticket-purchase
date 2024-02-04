<template>
    <section class="home">
        <header class="header">
            <van-row type="flex">
                <van-col span="4">
                    <van-dropdown-menu>
                        <van-dropdown-item v-model="value" :options="option"/>
                    </van-dropdown-menu>
                </van-col>
                <van-col span="20">
                    <van-search style="height: 3rem" shape="round" disabled
                                placeholder="请输入电影、影院名称"
                                @click="$router.push('/search')"/>
                </van-col>
            </van-row>
        </header>
        <MySwiper/>
        <van-grid direction="horizontal" :column-num="2">
            <van-grid-item icon="fire-o" text="正在热映" @click="$router.push('/movie')"/>
            <van-grid-item icon="tv-o" text="影院" @click="$router.push('/cinema')"/>
        </van-grid>
        <main class="main_container">
            <div>
                <div class="op1"><span class="left">正在热映</span><span class="right"
                                                                     @click="$router.push({path:'/movie',query:{hotMovie:1}})">共{{
                        movieList.length
                    }}部<van-icon
                        name="arrow"/></span></div>
                <div class="movie_list">
                    <div class="item" v-for="(item,index) in movieList.slice(0,4)" :key="index">
                        <van-image width="5rem" height="6rem" fit="cover"
                                   :src="getPoster(item.poster)"
                                   @click="$router.push({path:'/movie_detail',query:{movie_id:item.movie_id}})"/>
                        <div style="position: relative">
                            <div class="describe">
                                <span class="name ellipsis">{{ item.movie_name }}</span>
                                <span class="score" v-if="item.score"><i
                                    class="interger">{{ item.score.toFixed(1).split('.')[0] }}</i>.<i class="fraction">{{
                                        item.score.toFixed(1).split('.')[1]
                                    }}分</i></span>
                                <span class="score" v-else>暂无评分</span>
                            </div>
                        </div>
                        <van-button size="mini" color="red"
                                    @click="$router.push({path:'/select_cinema',query:{movie_id:item.movie_id}})">购买
                        </van-button>
                    </div>
                </div>
            </div>
            <div>
                <div class="op2"><span class="left">即将上映</span><span class="right"
                                                                     @click="$router.push({path:'/movie',query:{hotMovie:0}})">共{{
                        notShowMovieList.length
                    }}部<van-icon
                        name="arrow"/></span></div>
                <div class="movie_list">
                    <div class="item" v-for="(item,index) in notShowMovieList.slice(0,4)" :key="index">
                        <van-image width="5rem" height="6rem" fit="cover"
                                   :src="getPoster(item.poster)"
                                   @click="$router.push({path:'/movie_detail',query:{movie_id:item.movie_id}})"/>
                        <div style="position: relative">
                            <div class="peopleNumber">
                                <span class="num">{{ item.wish_num }}人想看</span>
                            </div>
                        </div>
                        <div class="presell">
                            <div class="name ellipsis">{{ item.movie_name }}</div>
                            <div class="info">
                                                        <span
                                                            class="date"
                                                            style="color: #ffb400;font-size: .725rem">{{
                                                                item.show_date.split('-')[1]
                                                            }}月{{
                                                                item.show_date.split('-')[2]
                                                            }}日</span>
                                <van-button size="mini" type="info"
                                            @click="$router.push({path:'/select_cinema',query:{movie_id:item.movie_id}})">
                                    预售
                                </van-button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </section>
</template>

<script>
import MySwiper from './index/MySwiper'
import {selectMovieSchedule} from "@/api";

export default {
    name: "Index",
    data() {
        return {
            value: 0,
            option: [
                {text: '深圳', value: 0},
            ],
            movieList: [],
            notShowMovieList: []
        }
    },
    created() {
        this.loadMovieInfo()
    },
    components: {MySwiper},
    methods: {
        loadMovieInfo() {
            selectMovieSchedule().then(res => {
                // console.log(res)
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
        getPoster(path) {
            return 'http://localhost:9090' + path
        }
    }
}
</script>

<style scoped>
.main_container {
    width: 100%;
    padding-bottom: 2rem;
    position: relative;
    background-color: #fff;
}

.op1 {
    display: flex;
    padding: .25rem 0 .8rem 1.25rem;
}

.op2 {
    display: flex;
    padding: 1rem 0 .8rem 1.25rem;
}

.op1 .left {
    font-size: 1.125rem;
    flex: 1;
    font-weight: 600;
}

.op2 .left {
    font-size: 1.125rem;
    flex: 1;
    font-weight: 600;
}

.right {
    flex: 1;
    text-align: right;
    vertical-align: middle;
    font-size: .875rem;
}

.movie_list {
    display: flex;
    flex-wrap: wrap;
}

.item {
    width: 6rem;
    padding: .4rem;
    box-sizing: border-box;
    position: relative;
    overflow: hidden;
}

.describe {
    display: flex;
    align-items: center;
    height: 1.125rem;
    box-sizing: border-box;
    padding-top: .2rem;
}

.name {
    color: #333;
    font-size: .725rem;
    box-sizing: border-box;
    font-weight: 600;
}

.score {
    top: -0.8rem;
    position: absolute;
    color: #ffb400;
    font-weight: 800;
    background: #666;
    font-size: .45rem;
}

.interger {
    font-size: .7rem;
}

.fraction {
    font-size: .7rem;
}

.peopleNumber {
    top: -1.125rem;
    position: absolute;
    left: 0;
    bottom: 0;
    align-items: center;
    background-color: rgba(0, 0, 0, .4);
    box-sizing: border-box;
}

.num {
    color: #fff;
    font-size: .125rem;
}

.info {
    padding-bottom: 1rem;
}
</style>