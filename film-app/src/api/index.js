import axios from './api'

//用户登录
export const userPhoneLogin = (params) => axios.post('/phoneLogin',params)
export const userPasswordLogin = (params) => axios.post('/passwordLogin',params)
//获取验证码
export const getPhoneCode = () => axios.get('/getPhoneCode')
//获取用户信息
export const selectUserInfo = (params) => axios.get('/findOneUserInfo',{params})
//用户修改信息
export const updateInfo = (params) => axios.post('/updateUser',params)
//用户修改头像
export const updateAvatar = (params) => axios.post('/updateAvatar',params)
//获取排片信息
export const selectMovieSchedule = () => axios.get('/findSchedule')
//获取影片信息
export const selectMovieInfo = (params) => axios.get('/findOneMovie',{params})
//获取影片排片
export const selectMovieScheduleInfo = (params) => axios.get('/findMovieScheduleInfo',{params})
export const selectMovieCinemaScheduleInfo = (params) => axios.get('/findMovieCinemaScheduleInfo',{params})
//查询用户是否喜欢电影
export const isWishMovie = (params) => axios.get('/isWishMovie',{params})
//添加是否希望看的电影
export const insertWishMovie = (params) => axios.post('/addWishMovie',params)
//取消希望看的电影
export const cancelWishMovie = (params) => axios.delete('/cancelWishMovie', {params})
//获取评论信息
export const getCommentInfo = (params) => axios.get('/getCommentInfo',{params})
//获取指定评论
export const getCommentById = (params) => axios.get('/getOneCommentInfo',{params})
//用户点赞处理
export const updateUserSupport = (params) => axios.post('/updateComment',params)
//获取影院列表
export const selectCinemaList = () => axios.get('/findCinemaList')
//获取指定影院信息
export const selectCinemaInfo = (params) => axios.get('/findOneCinema',{params})
//获取影院影片信息
export const selectCinemaScheduleInfo = (params) => axios.get('/findCinemaSchedule',{params})
//获取影院影片排片信息
export const selectCinemaMovieScheduleInfo = (params) => axios.get('/findCinemaMovieSchedule',{params})
//获取指定排片信息
export const selectOneSchedule = (params) => axios.get('/findOneSchedule',{params})
//下单座位信息
export const updateScheduleSeat = (params) => axios.post('/updateMovieSchedule',params)
//获取订单
export const selectOrderInfo = (params) => axios.get('/findUserOrder',{params})
//提交订单
export const insertOrder = (params) => axios.post('/addOrder',params)
//搜索电影
export const searchMovieByName = (params) => axios.get('/searchMovieByName',{params})
//搜索影院
export const searchCinemaByName = (params) => axios.get('/searchCinemaByName',{params})
//获取评论信息
export const getUserComment = (params) => axios.get('/findUserComment',{params})
//发布评论
export const insertUserComment = (params) => axios.post('/addComment',params)
//修改评论信息
export const updateUserComment = (params) => axios.post('/updateComment',params)
//获取用户想看的电影
export const getUserLike = (params) => axios.get('/getUserLikeMovie',{params})
//获取观看历史
export const getUserWatched = (params) => axios.get('/getWatchedMovie',{params})