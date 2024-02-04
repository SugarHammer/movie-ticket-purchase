/*
 Navicat Premium Data Transfer

 Source Server         : private
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : film

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 31/03/2021 18:47:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '管理员账户',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '管理员密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin');
INSERT INTO `admin` VALUES (2, 'admin1', 'admin');

-- ----------------------------
-- Table structure for cinema
-- ----------------------------
DROP TABLE IF EXISTS `cinema`;
CREATE TABLE `cinema`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '影院id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '影院名称',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '影院电话',
  `province` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '影院所在省份',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '影院所在市',
  `county` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '影院所在区县',
  `specified_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '影院详细地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cinema
-- ----------------------------
INSERT INTO `cinema` VALUES (1, '中影4KMAX国际影城（西丽店）', '0755-28119118', '广东省', '深圳市', '南山区', '南山区留仙大道2188号悦方广场一楼（留仙洞地铁站B出口）');
INSERT INTO `cinema` VALUES (2, '中影星美国际影城（南山云城店）', '0755-86522580', '广东省', '深圳市', '南山区', '南山区西丽街道留仙洞打石二路1号万科云城5层L5-015M-02号');
INSERT INTO `cinema` VALUES (3, '中影红树林国际影城（前海店）', '0755-86165216', '广东省', '深圳市', '南山区', '南山区桂湾三路龙海商业广场北区13-17号铺');
INSERT INTO `cinema` VALUES (4, '华夏君盛影城（南山店）', '0755-29902555', '广东省', '深圳市', '南山区', '深圳市南山区南头街道前海路港湾丽都二楼');
INSERT INTO `cinema` VALUES (5, '百川国际影城（南山欢乐颂店）', '0755-86605666', '广东省', '深圳市', '南山区', '深圳市南山区南新路3030号欢乐颂Fun2F5');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `user_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `movie_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '电影id',
  `user_score` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户评分',
  `content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '用户评论内容',
  `comment_date` datetime(6) NULL DEFAULT NULL COMMENT '评论日期',
  `support_num` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '点赞数',
  `support_user` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '点赞用户数组',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `movie_id`(`movie_id`) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (2, 1, 12, 10, '好看！', '2021-03-14 02:40:36.000000', 2, '[1,2]');
INSERT INTO `comment` VALUES (3, 2, 12, 9, 'good!', '2021-03-14 02:44:10.000000', NULL, NULL);
INSERT INTO `comment` VALUES (4, 3, 13, 10, '好看!', '2021-03-31 16:10:38.000000', NULL, NULL);

-- ----------------------------
-- Table structure for hall
-- ----------------------------
DROP TABLE IF EXISTS `hall`;
CREATE TABLE `hall`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '影厅id',
  `cinema_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '影院id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '影厅名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cinema_id`(`cinema_id`) USING BTREE,
  CONSTRAINT `hall_ibfk_1` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hall
-- ----------------------------
INSERT INTO `hall` VALUES (1, 1, '1号厅');
INSERT INTO `hall` VALUES (2, 1, '2号厅');
INSERT INTO `hall` VALUES (3, 1, '3号厅');
INSERT INTO `hall` VALUES (4, 2, '1号厅');
INSERT INTO `hall` VALUES (5, 2, '2号厅');
INSERT INTO `hall` VALUES (6, 2, '3号厅');
INSERT INTO `hall` VALUES (7, 3, '1号厅');
INSERT INTO `hall` VALUES (8, 3, '2号厅');
INSERT INTO `hall` VALUES (9, 3, '3号厅');
INSERT INTO `hall` VALUES (10, 4, '1号厅');
INSERT INTO `hall` VALUES (11, 4, '2号厅');
INSERT INTO `hall` VALUES (12, 4, '3号厅');
INSERT INTO `hall` VALUES (13, 5, '1号厅');
INSERT INTO `hall` VALUES (14, 5, '2号厅');
INSERT INTO `hall` VALUES (15, 5, '3号厅');

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '电影id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '电影名称',
  `poster` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '电影海报',
  `director` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '导演',
  `actor` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '主演',
  `movie_long` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '电影片长',
  `language` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '电影语言版本',
  `intro` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '电影简介',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '电影类型',
  `public_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '电影上映时间',
  `wish_num` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '想看人数',
  `score` decimal(4, 1) NULL DEFAULT NULL COMMENT '电影综合评分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie
-- ----------------------------
INSERT INTO `movie` VALUES (1, '钢铁侠3', '/poster/16156559621221.jpg', '沙恩·布莱克', '小罗伯特·唐尼、格温妮斯·帕特洛', '134分钟', '英语', '自纽约事件以来，托尼·斯塔克（小罗伯特·唐尼饰）为前所未有的焦虑症所困扰。他疯狂投入钢铁侠升级版的研发，为此废寝忘食，甚至忽略了女友佩珀·波茨（格温妮斯·帕特洛饰）的感受。\n与此同时，臭名昭著的恐怖头目“曼达林”（本·金斯利饰）制造了一连串的爆炸事件，托尼当年最忠诚的保镖哈皮即在最近的一次袭击中身受重伤。托尼、佩珀以及曾与他有过一面之缘的女植物学家玛雅（丽贝卡·豪尔饰）在家中遭到猛烈的炮火袭击，几乎丧命，而这一切似乎都与22年前那名偶然邂逅的科学家阿尔德里奇·基连（盖·皮尔斯饰）及其终极生物的研究有关。即使有精密先进的铠甲护身，也无法排遣发自心底的焦虑。被击碎一切的托尼，如何穿越来自地狱的熊熊烈火。\n', '科幻', '2021-03-15', 0, NULL);
INSERT INTO `movie` VALUES (2, '哆啦A梦：伴我同行', '/poster/16156562931442.jpg', '八木龙一，山崎贵', '水田山葵、大原惠、嘉数由美、木村昴、关智一', '95分钟', '日语', '为了什么都做不来的野比大雄，22 世纪的玄孙野比世修送了猫型机器人──哆啦A梦来现代。\n笨笨的野比大雄原本自己开了间公司，但很不幸的倒闭，之后剩下了一屁股债务，子孙们吃了莫大的苦。于是，野比世修才打算送哆啦A梦到现代，打算改变大雄的未来，并且为哆啦A梦设定了完成程式，如果野比大雄不幸福的话，哆啦A梦就不能回22世纪。\n哆啦A梦于是开始勉勉强强的协助野比大雄的日常生活。虽然刚开始不太习惯，但两个人的关系也日渐变的紧密。得知野比大雄的梦想是打算与梦中情人的同班同学源静香结婚以后，哆啦A梦就想尽办法要帮助野比大雄获得静香的芳心。\n正当源静香总算答应了野比大雄的求婚的时候，任务完成的哆啦A梦却被完成程式要求，要在48小时内回到22世纪。难道，得到了什么，自然的，也会失去些什么这个命定的预言，没办法被哆啦A梦跟野比大雄突破。面对哆啦A梦即将离开的冲击，野比大雄又该如何自处。\n', '动漫', '2021-06-01', 0, NULL);
INSERT INTO `movie` VALUES (3, '我不是药神', '/poster/16156567525303.jpeg', '文牧野', '徐峥、周一围、王传君、谭卓、章宇、杨新鸣', '116分钟', '国语', '印度神油店老板程勇日子过得窝囊，店里没生意，老父病危，手术费筹不齐。前妻跟有钱人怀上了孩子，还要把他儿子的抚养权给拿走。一日，店里来了一个白血病患者，求他从印度带回一批仿制的特效药，好让买不起天价正版药的患者保住一线生机。百般不愿却走投无路的程勇，意外因此一夕翻身，平价特效药救人无数，让他被病患封为“药神”，但随着利益而来的，是一场让他的生活以及贫穷病患性命都陷入危机的多方拉锯战。\n', '剧情', '2021-05-14', 1, NULL);
INSERT INTO `movie` VALUES (4, '复仇者联盟4：终局之战', '/poster/16156568016944.jpg', '安东尼·罗素、乔·罗素', '小罗伯特·唐尼、克里斯·埃文斯、马克·鲁法洛、克里斯·海姆斯沃斯、斯嘉丽·约翰逊、杰瑞米·雷纳', '181分钟', '英语', '来自泰坦星的灭霸为了解决宇宙资源匮乏、人口暴增的问题，集齐了所有无限宝石，一个响指成功地使全宇宙生命随机减半。\n宇宙由于灭霸的行动而变得满目疮痍，但是五年之后，被困在量子领域的蚁人意外回到现实世界，他的出现为幸存的复仇者点燃了希望。无论前方将遭遇怎样的后果，幸存的复仇者都必须在剩余盟友的帮助下再一次集结，以逆转灭霸的所作所为，彻底恢复宇宙的秩序\n', '科幻', '2021-05-01', 1, NULL);
INSERT INTO `movie` VALUES (5, '扫毒2：天地对决', '/poster/16156568986525.jpg', '邱礼涛', '刘德华、古天乐、苗侨伟', '99分钟', '国语', '毒品市场维持四分天下的格局已久，自从地藏（古天乐饰）与墨西哥大毒枭合作扩张势力，策划一连串黑吃黑事件企图“毒霸”香港毒品市场，警员林正风（苗侨伟饰）便带着他的扫毒行动组全力追缉毒贩。而儿时亲眼目睹父亲被毒品所毁而嫉毒如仇的慈善家兼金融巨子余顺天（刘德华饰），悬赏一亿追杀香港最大毒贩，此举在社会上引起轩然大波。原来，余顺天和地藏有着不可告人的同门关系，一场天地对决一触即发。在二十年的同门情谊面前，兄弟二人又将如何面对这场“毒局”。与此同时，林正风妻子、同事在执行缉毒任务时被吸毒者和毒贩残忍杀害，背负着丧妻之痛的他也将与毒贩们展开殊死对决\n', '剧情', '2021-03-26', 0, NULL);
INSERT INTO `movie` VALUES (6, '升起的烟花，从下面看？还是从侧面看？', '/poster/16156569452526.jpg', '新房昭之，武内宣之', '广濑铃，菅田将晖，宫野真守，松隆子', '90分钟', '日语', '暑假，某个海边小镇。在烟花晚会之前，热热闹闹地聊着“升空的烟花从侧面看是圆的？还是平的？”这样的话题的同班同学。与此同时，典道暗恋着的奈砂，因为奈砂的母亲的再婚对象已确定所以要转学了。\n“我们私奔、吧”\n奈砂向典道发出了一起逃出小镇的邀请，却在那之前被母亲带走了。而典道只是眼睁睁地看着这些。\n“如果，那时候我…”\n没有救下奈砂的典道，因为心中的苛责而丢出了奈砂在海中捡到的不可思议的珠子。就这样，不知何时起，时间被倒回到了奈砂被带走之前的时候…。一次又一次被重复的一天，奈砂与典道的命运会？\n', '动漫', '2021-05-05', 0, NULL);
INSERT INTO `movie` VALUES (7, '你好，李焕英', '/poster/16156593162617.jpg', '贾玲', '贾玲、张小斐、沈腾、陈赫', '128分钟', '国语', '2001年的某一天，刚刚考上大学的贾晓玲（贾玲 饰）经历了人生中的一次大起大落。一心想要成为母亲骄傲的她却因母亲突遭严重意外，而悲痛万分。在贾晓玲情绪崩溃的状态下，竟意外的回到了1981年，并与年轻的母亲李焕英（张小斐 饰）相遇，二人形影不离，宛如闺蜜。与此同时，也结识了一群天真善良的好朋友。晓玲以为来到了这片“广阔天地”，她可以凭借自己超前的思维，让母亲“大有作为”，但结果却让晓玲感到意外\n', '言情', '2021-03-15', 0, NULL);
INSERT INTO `movie` VALUES (8, '唐人街探案3', '/poster/16156593537158.jpg', '陈思诚', '王宝强、刘昊然、妻夫木聪、托尼·贾', '136分钟', '国语', '继曼谷、纽约之后，东京再出大案。唐人街神探唐仁（王宝强 饰）、秦风（刘昊然 饰）受侦探野田昊（妻夫木聪 饰）的邀请前往破案。“CRIMASTER世界侦探排行榜”中的侦探们闻讯后也齐聚东京，加入挑战，而排名第一Q的现身，让这个大案更加扑朔迷离，一场亚洲最强神探之间的较量即将爆笑展开\n', '悬疑恐怖', '2021-03-15', 0, NULL);
INSERT INTO `movie` VALUES (9, '功夫', '/poster/16156593866869.jpg', '周星驰', '周星驰、黄圣依、陈国坤、元华、元秋、梁小龙', '95分钟', '国语', '故事发生在1930年代的中国南方某地。黑帮斧头帮在当地欺行霸市，且收买当地的警察逃脱指控。\n阿星（周星驰饰）整日梦想成为黑帮人物，但意志不坚、无所作为。只好冒充斧头帮，跑到黑帮无暇顾及的贫民窟——猪笼城寨敲诈点钱财。不料猪笼城寨的老百姓各个身怀绝技，阿星被众人暴打。恰好斧头帮路过，副头领被阿星的假斧头帮信号炮炸伤，阿星把此事嫁祸给猪笼城寨。帮众进入城寨寻事打斗，结果斧头帮被猪笼城寨的三位武功高手（五郎八卦棍、洪家铁线拳、十二路谭腿）大挫锐气，大失颜面，败阵而退。\n阿星回想起童年时，在奸商手上买了失传武功秘笈《如来神掌》，开始习武，有一天他看见一名不能说话的小女孩被多人欺负，欲见义勇为帮她解围，但反被他们击倒，甚至在阿星身上撒尿。这位哑女孩欲把手中的波板糖送给阿星，但阿星觉得自己深受侮辱，哭著离去，因此便打算在长大后去当坏人。\n斧头帮帮主随后请来两名杀手榜知名高手前去铲除这三个家伙，他俩利用手中古筝，弹出暗器（冲击波），在猪笼城寨杀了他们。猪笼城寨的包租公和包租婆最后忍无可忍，露出自己暗藏已久的功夫（太极拳、狮吼功），将这两名杀手打得落花流水、武功尽失，替三个邻居报了仇。\n斧头帮坐不住了，利用阿星开锁的手艺，从”非正常人类研究中心“（精神病院）放出了号称当今武林第一高人”火云邪神“（梁小龙饰），期望能对付猪笼城寨的武术高强的房东夫妇。火云邪神果然不负斧头帮之期望，力克包租公、包租婆。阿星在关键时候，叛离斧头帮，帮助包租公、包租婆，但不幸自己落入火云邪神手中。火云邪神把阿星打得差点一命呜呼，混乱中不但重创了包租婆和包租公，也要了斧头帮帮主的性命。并成了新的斧头帮帮主。但没想到火云邪神的几拳竟然打通了阿星的任督二脉，加上阿星年幼时练习的“如来神掌”，阿星一下子具有了震撼之极的武功。最终，火云邪神带斧头帮众前去猪笼城寨与阿星决斗，阿星使出“如来神掌”击破火云邪神的“蛤蟆功”，把火云邪神打败，而火云邪神最后的暗器”莲花毒箭“也被阿星破解，使火云邪神臣服于阿星。\n一切归于平静后，阿星和那位哑女孩重新相遇……\n', '动作', '2021-03-15', 0, NULL);
INSERT INTO `movie` VALUES (10, '哥斯拉：怪兽之王', '/poster/161565941817110.jpg', '迈克尔·道赫蒂', '凯尔·钱德勒，维拉·法梅加，米莉·博比·布朗，渡边谦，章子怡，布莱德利·惠特福德，莎莉·霍金斯', '132分钟', '英语', '动物基因组学（帝王计划）研究机构的一个小组在一座庙里研究着魔斯拉。她从幼体中孵出，在一群雇佣兵袭击这个机构时，魔斯拉逃了出去。然后她在一个瀑布包了茧，随后破茧而出，完成了全形态生长。在2014年，哥斯拉被整个世界看到后，“帝王组织”在水下建了一个基地，在这个位于水下好几英里的水中基地中，他们研究着哥斯拉和其它可能存在的生物。那是他们真正的研究核心 [2]  。\n长期沉睡于地底和冰川里面神秘的巨兽在星球上面已经有上万，甚至好几万年的历史，它要把这个星球重新夺回它自己的手下。为了大自然的一个自然平衡物，哥斯拉苏醒了，它去消灭那些未来可能会去损害大自然平衡的生物 [3]  。后来，哥斯拉和魔斯拉、拉顿以及它的强敌基多拉相遇。此前一直被认为只存在于神话当中的古老的超级怪兽一一登场，他们将为争夺“怪兽之王”而战，人类的生存也受到严重威胁\n', '科幻', '2021-03-15', 0, NULL);
INSERT INTO `movie` VALUES (11, '流浪地球', '/poster/161565944939911.jpg', '郭帆', '吴京、屈楚萧、赵今麦、李光洁、吴孟达', '136分钟', '国语', '近年来，科学家们发现太阳急速衰老膨胀，短时间内包括地球在内的整个太阳系都将被太阳所吞没。为了自救，人类提出一个名为“流浪地球”的大胆计划，即倾全球之力在地球表面建造上万座发动机和转向发动机，推动地球离开太阳系，用2500年的时间奔往新家园。\n中国航天员刘培强（吴京饰）在儿子刘启四岁那年前往领航员空间站，和国际同侪肩负起领航者的重任。转眼刘启（屈楚萧饰）长大，他带着妹妹韩朵朵（赵今麦饰）偷偷跑到地表，偷开外公韩子昂（吴孟达饰）的运输车，结果不仅遭到逮捕，还遭遇了全球发动机停摆的事件。为了修好发动机，阻止地球坠入木星，全球开始展开饱和式营救，连刘启他们的车也被强征加入。在与时间赛跑的过程中，无数的人前仆后继，奋不顾身，只为延续百代子孙生存的希望\n', '科幻', '2021-03-15', 0, NULL);
INSERT INTO `movie` VALUES (12, 'Hello Wrold', '/poster/161565948330912.jpeg', '伊藤智彦', '北村匠海、松坂桃李、滨边美波、钉宫理惠', '97分钟', '日语', '在京都居住的内向男高中生直实的面前，突然出现从10年后穿越而来26岁的自己。未来的直实告诉他，自己不久便会与琉璃相爱并指导直实如何去撩妹，可是之后烟花大会时她却会因为一场事故意外离世。 为了拯救爱人，16岁的直实卷入了这场现实与虚拟的记忆世界，经历了一系列超乎想象的事情。\n', '动漫', '2021-03-15', 2, NULL);
INSERT INTO `movie` VALUES (13, '天气之子', '/poster/161565951419413.jpg', '新海诚', '醍醐虎汰朗、森七菜', '112分钟', '日语', '“想尝试前往，那道光芒之中”\n高中一年级的夏天，从离岛离家出走，来到东京的帆高。但是他的生活立马变得穷困，在度过孤独的每一天之后终于找到的工作，是为古怪的超自然杂志撰稿。\n如同预示着他接下来的命运一般，连日不断地下雨。此时，在人潮熙熙攘攘的都市一角，帆高遇到了一位少女。\n由于某些缘故，少女·阳菜和弟弟两人一起坚强生活。而她，拥有不可思议的能力。\n“呐，现在开始就要放晴了哦”\n雨逐渐停止，街道笼罩在美丽的光芒中。那是仅仅在心中祈祷，就能让天空放晴的力量——\n', '动漫', '2021-03-17', 1, NULL);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `schedule_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '影院id',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '下单手机',
  `order_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户下单时间',
  `num` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '购买电影票数',
  `price` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '电影票单价',
  `seat_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '座位信息',
  `pay_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '支付方式',
  `phone_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '手机取票码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `schedule_id`(`schedule_id`) USING BTREE,
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 1, 1, '13966668888', '2021-3-14', 4, 132.00, '[34,35,36,37]', '0', '485036');
INSERT INTO `order` VALUES (2, 2, 1, '13988886666', '2021-3-14', 2, 66.00, '[14,17]', '0', '342726');
INSERT INTO `order` VALUES (3, 3, 4, '15362749548', '2021-3-31', 4, 132.00, '[25,26,27,24]', '0', '159162');

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '排片id',
  `movie_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '电影id',
  `cinema_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '影院id',
  `hall_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '影厅名字',
  `show_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '放映日期',
  `show_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '放映时间',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '电影售价',
  `seat_info` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '座位信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `movie_id`(`movie_id`) USING BTREE,
  INDEX `cinema_id`(`cinema_id`) USING BTREE,
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (1, 12, 1, '1号厅', '2021-03-15', '09:30', 33.00, '[34,35,36,37,14,17]');
INSERT INTO `schedule` VALUES (2, 6, 1, '1号厅', '2021-05-07', '20:20', 20.00, NULL);
INSERT INTO `schedule` VALUES (3, 7, 3, '1号厅', '2021-03-15', '09:30', 44.00, NULL);
INSERT INTO `schedule` VALUES (4, 13, 1, '1号厅', '2021-03-31', '20:20', 33.00, '[25,26,27,24]');
INSERT INTO `schedule` VALUES (5, 1, 1, '1号厅', '2021-03-31', '22:00', 33.00, NULL);
INSERT INTO `schedule` VALUES (6, 3, 1, '1号厅', '2021-05-14', '09:30', 33.00, NULL);
INSERT INTO `schedule` VALUES (7, 2, 1, '2号厅', '2021-06-01', '09:30', 33.00, NULL);
INSERT INTO `schedule` VALUES (8, 4, 1, '1号厅', '2021-05-01', '16:30', 44.00, NULL);
INSERT INTO `schedule` VALUES (9, 4, 1, '2号厅', '2021-05-01', '16:30', 44.00, NULL);
INSERT INTO `schedule` VALUES (10, 7, 4, '1号厅', '2021-04-01', '09:30', 33.00, NULL);
INSERT INTO `schedule` VALUES (11, 7, 4, '1号厅', '2021-04-01', '16:30', 33.00, NULL);
INSERT INTO `schedule` VALUES (12, 7, 4, '2号厅', '2021-04-01', '20:20', 33.00, NULL);
INSERT INTO `schedule` VALUES (13, 10, 5, '3号厅', '2021-03-31', '22:00', 33.00, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '账户',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户密码',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户手机号码',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户性别',
  `birth` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户出生年月日',
  `sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户签名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '13966668888', '测试用户1', '/img/1615660711852微信图片_20200613161732.jpg', '123456', '13966668888', '男', '2021-3-14', '测试');
INSERT INTO `user` VALUES (2, '13988886666', '测试用户2', '/img/1615661304203微信图片_20200613161719.jpg', '123456', '13988886666', '女', '2021-3-15', '测试');
INSERT INTO `user` VALUES (3, '15362749548', '测试用户3', '/img/1617178069173微信图片_20200613161732.jpg', '123456', '15362749548', '男', '2021-3-31', 'testtetst');

-- ----------------------------
-- Table structure for wishmovie
-- ----------------------------
DROP TABLE IF EXISTS `wishmovie`;
CREATE TABLE `wishmovie`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '想看电影id',
  `user_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `movie_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '电影id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `movie_id`(`movie_id`) USING BTREE,
  CONSTRAINT `wishmovie_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `wishmovie_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wishmovie
-- ----------------------------
INSERT INTO `wishmovie` VALUES (1, 1, 12);
INSERT INTO `wishmovie` VALUES (2, 2, 12);
INSERT INTO `wishmovie` VALUES (3, 3, 13);
INSERT INTO `wishmovie` VALUES (4, 3, 3);
INSERT INTO `wishmovie` VALUES (5, 3, 4);

SET FOREIGN_KEY_CHECKS = 1;
