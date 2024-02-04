<template>
    <section style="width: 100%;background-color: #f5f5f5;">
        <header>
            <van-search v-model="name" shape="round" show-action clearable placeholder="请输入影院、电影名称"
                        @cancel="$router.go(-1)" @input="search"/>
        </header>
        <main class="content">
            <div class="movie-container" v-if="movieInfo.length">
                <div class="title">影片</div>
                <movie-item :movie-list="movieInfo" :search-name="name"></movie-item>
            </div>
            <div class="cinema-container" v-if="cinemaInfo.length">
                <div class="title">影院</div>
                <div class="item" v-for="(item,index) in cinemaInfo" :key="index"
                     @click="$router.push({path:'/cinema_detail',query:{cinema_id:item.id}})">
                    <div class="left">
                        <div class="name ellipsis" v-html="ruleName(item.name)"></div>
                        <div class="address ellipsis">{{ item.specified_address }}</div>
                        <div class="label-block"><span>小吃</span><span>4D厅</span><span>杜比全景声厅</span><span>巨幕厅</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tips" v-if="name&&!movieInfo.length&&!cinemaInfo.length">
                <span class="icon icon-empty-content"></span>
                <span class="text">暂无内容</span>
            </div>
        </main>
    </section>
</template>

<script>
import MovieItem from "@/components/movie/MovieItem";
import {searchCinemaByName, searchMovieByName} from "@/api";

export default {
    name: "Search",
    data() {
        return {
            name: '',
            movieInfo: [],
            cinemaInfo: [],
        }
    },
    methods: {
        search(value) {
            this.movieInfo = []
            this.cinemaInfo = []
            clearTimeout(this.timer)
            if (value){
                this.timer = setTimeout(()=>{
                    searchMovieByName({query:value}).then(res=>{
                        if (res.data.code === 1){
                            this.movieInfo = res.data.movieInfo
                        }
                    }).catch(err=>{
                        console.log(err)
                    })
                    searchCinemaByName({query: value}).then(res=>{
                        if (res.data.code === 1){
                            this.cinemaInfo = res.data.cinemaInfo
                        }
                    }).catch(err=>{
                        console.log(err)
                    })
                },500)
            }
        }
    },
    computed: {
        ruleName() {
            return (nameString) => {
                let replaceReg = new RegExp(this.name, 'g');
                let replaceString = `<span style="color: #dd2727">${this.name}</span>`;
                return nameString.replace(replaceReg, replaceString);
            }
        }
    },
    components: {MovieItem}
}
</script>

<style scoped>
.content {
    width: 100%;
    background: #fff;
}

.movie-container {
    width: 100%;
    font-size: 1.3125rem;
    padding: .3rem;
    box-sizing: border-box;
    border-top: .04rem solid #f1f1f1;
}

.name {
    font-weight: bolder;
    padding-bottom: .2rem;
    color: #333;
}

.cinema-container {
    width: 100%;
    font-size: .3125rem;
    padding: .3rem;
    box-sizing: border-box;
    border-top: .04rem solid #f1f1f1;
}

.title {
    font-size: 1.3rem;
    padding-bottom: .25rem;
    border-bottom: .03rem solid #f1f1f1;
}

.item {
    display: flex;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    padding: .25rem;
    width: 100%;
    border-bottom: .03rem solid #f1f1f1;
    margin-bottom: .25rem;
}

.left {
    width: 100%;
}

.name {
    font-size: 1.345rem;
    line-height: 1.36rem;
    margin-bottom: .4rem;
    font-weight: 700;
}

.address {
    font-size: .88rem;
    line-height: .88rem;
    color: #666;
    margin-bottom: .35rem;
}

.label-block {
    display: flex;
}

.label-block span {
    padding: .06rem;
    font-size: .2rem;
    border: .01rem solid #f90;
    margin-right: .2rem;
    border-radius: .04rem;
    color: #f90;
}

.tips {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-flow: column;
    font-size: 1.5rem;
    padding-top: 8rem;
    border-top: .04rem solid #f1f1f1;
}

.icon {
    font-size: 5rem;
    margin-bottom: .55rem;
}

.text {
    color: #ccc;
}

</style>