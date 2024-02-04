import axios from "./api"

//登录
export const login = (params) => axios.post('/login', params)


//获取用户数据
export const findUser = (params) => axios.get('/findUserInfo', {params})
//获取指定ID用户
export const findOneUser = (params) => axios.get('/findOneUserInfo', {params})
//添加用户
export const insertUser = (params) => axios.post('/addUser', params)
//修改用户
export const updateUser = (params) => axios.post('/updateUser', params)
//删除用户
export const deleteUser = (params) => axios.delete('deleteUser', {params});
//获取用户数据
export const getUserList = () => axios.get('/getUserList')


//获取影片信息
export const findMovie = (params) => axios.get('/findMovie', {params})
//获取影片
export const getMovieList = () => axios.get('/getMovieList')
//获取指定影片信息
export const findOneMovie = (params) => axios.get('/findOneMovie', {params})
//添加影片
export const insertMovie = (params) => axios.post('/addMovie', params)
//修改影片信息
export const updateMovie = (params) => axios.post('/updateMovie', params)
//删除影片
export const deleteMovie = (params) => axios.delete('/deleteMovie', {params})


//获取影院信息
export const findCinema = (params) => axios.get('/findCinema', {params})
export const getCinemaList = () => axios.get('/getCinemaList' )
//获取指定影院信息
export const findOneCinema = (params) => axios.get('/findOneCinema', {params})
//添加影院
export const insertCinema = (params) => axios.post('/addCinema', params)
//修改影院信息
export const updateCinema = (params) => axios.post('/updateCinema', params)
//删除影院
export const deleteCinema = (params) => axios.delete('/deleteCinema', {params})


//获取影厅信息
export const findHall = (params) => axios.get('/findHall', {params})
//获取
export const findHallList = (params) => axios.get('/findHallList',{params})
//获取指定影厅信息
export const findOneHall = (params) => axios.get('/findOneHall', {params})
//添加影厅
export const insertHall = (params) => axios.post('/addHall', params)
//修改影厅信息
export const updateHall = (params) => axios.post('/updateHall', params)
//删除影厅
export const deleteHall = (params) => axios.delete('/deleteHall', {params})

//获取影片排片信息
export const findMovieSchedule = (params) => axios.get('/findMovieSchedule', {params})
//获取指定排片
export const findMovieScheduleList = (params) => axios.get('/findMovieScheduleList',{params})
//添加影片排片
export const insertMovieSchedule = (params) => axios.post('/addMovieSchedule', params)
//删除影片排片
export const deleteMovieSchedule = (params) => axios.delete('/deleteMovieSchedule', {params})

//获取评论信息
export const findComment = (params) => axios.get('/findComment', {params})
//删除评论
export const deleteComment = (params) => axios.delete('/deleteComment', {params})

//获取订单信息
export const findOrder = (params) => axios.get('/findOrder', {params})
export const getOrderList = () => axios.get('/getOrderList')
//删除订单
export const deleteOrder = (params) => axios.delete('/deleteOrder', {params})