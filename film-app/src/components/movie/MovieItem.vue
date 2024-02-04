<template>
    <section v-if="movieList.length">
        <section class="movie-item" v-for="(item,index) in movieList" :key="index">
            <van-image width="5rem" height="6rem" fit="cover"
                       :src="getPoster(item.poster)"
                       @click="$router.push({path:'/movie_detail',query:{movie_id:item.movie_id}})"/>
            <div class="info">
                <div class="name" @click="$router.push({path:'/movie_detail',query:{movie_id:item.movie_id}})"
                     v-html="ruleName(item.movie_name)"></div>
                <div v-if="new Date()-new Date(item.show_date)>=0">
                    <div class="descInfo" v-if="item.score">评分：<span class="number">{{ item.score.toFixed(1) }}</span>分
                    </div>
                    <div class="descInfo" v-else>暂无评分</div>
                </div>
                <div v-else>
                    <div class="descInfo">想看：<span class="number">{{item.wish_num}}</span>人想看</div>
                </div>
                <div class="descInfo ellipsis">主演：<span>{{ item.actor }}</span></div>
            </div>
            <van-button size="small" round color="red"
                        @click="$router.push({path:'/select_cinema',query:{movie_id:item.movie_id}})">{{
                    new Date(item.show_date) - new Date() > 0 ? '预售' : '购票'
                }}
            </van-button>
        </section>
    </section>
</template>

<script>
export default {
    name: "MovieItem",
    data() {
        return {}
    },
    props: {
        movieList: {
            type: Array,
            require: true,
            default: []
        },
        searchName: {
            type: String,
            default: ''
        }
    },
    methods:{
        getPoster(path){
            return 'http://localhost:9090' + path
        }
    },
    computed: {
        ruleName() {
            return (nameString) => {
                if (this.searchName) {
                    let replaceReg = new RegExp(this.searchName, 'g');
                    let replaceString = `<span style="color:#dd2727">${this.searchName}</span>`;
                    return nameString.replace(replaceReg, replaceString);
                }
                return nameString
            }
        }
    }
}
</script>

<style scoped lang="stylus">
.movie-item
    display flex
    justify-content space-around
    align-items center
    padding .5rem 0

    .info
        width 60%
        display flex
        flex-flow column
        padding .25rem
        font-size .825rem
        color #9d9d9d
    

        .name
            font-weight 700
            font-size 1.125rem
            padding-bottom .5rem
            color #333

        .descInfo
            padding-bottom .42rem

            .number
                color #ffb400
                font-family PingFangSC-Regular, Hiragino Sans GB, sans-serif
                font-weight 700
                font-size .875rem
</style>