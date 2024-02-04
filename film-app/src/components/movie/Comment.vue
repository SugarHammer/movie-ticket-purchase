<template>
    <section id="comment-panel">
        <header class="top">
            <van-icon name="arrow-left" size="1rem" @click="$router.go(-1)" style="position: absolute;left: .5rem;"/>
            <span class="title">评论</span>
            <span class="btn" @click="commentBtnHandle">发布</span>
        </header>
        <main class="mark">
            <span class="score"><span class="number">{{ score }}</span>分</span>
            <span class="text">{{ handleScoreText }}</span>
            <van-rate v-model="starValue" allow-half icon="like" void-icon="like-o"
                      @change="changeScore"/>
        </main>
        <textarea class="comment-text" placeholder="好看吗？快来写下你的感受吧!" v-model="textarea">
      </textarea>
    </section>
</template>

<script>
import {getUserComment, insertUserComment, updateUserComment} from "@/api";
import moment from 'moment'

export default {
    name: "Comment",
    data() {
        return {
            id:0,
            textarea: '',
            score: 0,
            starValue: 0,
            hasComment: false
        }
    },
    created() {
        this.loadCommentInfo()
    },
    methods: {
        //改变分数
        changeScore(score) {
            this.score = score * 2;
        },
        //获取用户评论
        loadCommentInfo() {
            getUserComment({
                user_id: this.$cookies.get('UID'),
                movie_id: this.$route.query.movie_id
            }).then(res => {
                if (res.data.code === 1) {
                    this.id = res.data.commentInfo.id
                    this.starValue = res.data.commentInfo.userScore * 0.5
                    this.score = res.data.commentInfo.userScore
                    this.textarea = res.data.commentInfo.content
                    this.hasComment = true
                }
            }).catch(err => {
                console.log(err)
            })
        },
        commentBtnHandle() {
            if (this.textarea) {
                if (this.hasComment) {
                    let info = {
                        id: this.id,
                        userId: this.$cookies.get('UID'),
                        movieId: this.$route.query.movie_id,
                        userScore: this.score,
                        content: this.textarea,
                        commentDate: moment().format('YYYY-MM-DD HH:mm:ss')
                    }
                    updateUserComment(info).then(res => {
                        if (res.data.code === 1) {
                            this.$dialog.alert({
                                message: '评论修改成功！',
                                theme: 'round-button',
                            }).then(() => {
                                this.$router.go(-1)
                            });
                        }
                    }).catch(err => {
                        console.log(err)
                    })
                } else {
                    let info = {
                        userId: this.$cookies.get('UID'),
                        movieId: this.$route.query.movie_id,
                        userScore: this.score,
                        content: this.textarea,
                        commentDate: moment().format('YYYY-MM-DD HH:mm:ss')
                    }
                    insertUserComment(info).then(res => {
                        if (res.data.code === 1) {
                            this.$dialog.alert({
                                message: '评论成功！',
                                theme: 'round-button',
                            }).then(() => {
                                this.$router.go(-1)
                            });
                        }
                    }).catch(err => {
                        console.log(err)
                    })
                }
            } else {
                this.$notify({type: 'warning', message: '请输入评论内容'})
            }
        }
    }
    ,
    computed: {
        //处理分数文本
        handleScoreText() {
            if (this.score === 1 || this.score === 2) {
                return '超烂啊'
            } else if (this.score === 3 || this.score === 4) {
                return '比较差'
            } else if (this.score === 5 || this.score === 6) {
                return '一般般'
            } else if (this.score === 7 || this.score === 8) {
                return '还不错'
            } else if (this.score === 9 || this.score === 10) {
                return '棒极了'
            }
        }
        ,
    }
}
</script>

<style scoped lang="stylus">
#comment-panel
    width 100%
    background-color #fff

    .top
        display flex
        justify-content center
        align-items center
        height 3rem
        background-color #f9f9f9
        position relative

        .title
            font-size 1.125rem
            letter-spacing .04rem

        .btn
            position absolute
            right .25rem
            padding .16rem .24rem
            background-color #dd2727
            color white
            border-radius .1rem
            font-size .45rem

    /deep/ .mark
        width 100%
        height 100%
        padding .25rem
        padding-bottom .5rem
        box-sizing border-box
        display flex
        flex-flow column
        justify-content center
        align-items center
        font-size .65rem
        border-bottom 0.0125rem solid #f1f1f1

        .score
            color #ffb400
            padding .25rem

            .number
                font-size 1.8rem

        .text
            margin-bottom .3rem
            color #888

        .el-rate
            display flex
            justify-content center
            align-items center

            i
                font-size .8rem

    .comment-text
        font-size 1.3125rem
        line-height 1.4rem
        padding .4rem
        text-align justify
        width 100%
        height 6rem
        box-sizing border-box
        border none
        outline none
        resize none
</style>