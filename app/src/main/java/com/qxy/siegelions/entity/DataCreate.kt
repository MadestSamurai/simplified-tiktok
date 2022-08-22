package com.qxy.siegelions.entity

import com.qxy.siegelions.R
import com.qxy.siegelions.entity.Video.User
import java.util.*

/**
 * 本地数据创建，代替接口获取数据
 * @author MadSamurai
 */
class DataCreate {

    fun initData() {

        val videoOne = Video()
        videoOne.coverRes = R.mipmap.cover1
        videoOne.content = "#街坊 #颜值打分 给自己颜值打100分的女生集合"
        videoOne.videoRes = R.raw.video1
        videoOne.distance = 7.9f
        videoOne.isFocused = false
        videoOne.isLiked = true
        videoOne.likeCount = 226823
        videoOne.commentCount = 3480
        videoOne.shareCount = 4252

        val userOne = User()
        userOne.uid = 1
        userOne.head = R.mipmap.head1
        userOne.nickName = "南京街坊"
        userOne.sign = "你们喜欢的话题，就是我们采访的内容"
        userOne.subCount = 119323
        userOne.focusCount = 482
        userOne.fansCount = 32823
        userOne.workCount = 42
        userOne.dynamicCount = 42
        userOne.likeCount = 821
        userList.add(userOne)
        videoOne.user = userOne

        val videoTwo = Video()
        videoTwo.coverRes = R.mipmap.cover2
        videoTwo.content = "400 户摊主开进济南环联夜市，你们要的烟火气终于来了！"
        videoTwo.videoRes = R.raw.video2
        videoTwo.distance = 19.7f
        videoTwo.isFocused = true
        videoTwo.isLiked = false
        videoTwo.likeCount = 1938230
        videoTwo.commentCount = 8923
        videoTwo.shareCount = 5892

        val userTwo = User()
        userTwo.uid = 2
        userTwo.head = R.mipmap.head2
        userTwo.nickName = "民生直通车"
        userTwo.sign = "直通现场新闻，直击社会热点，深入事件背后，探寻事实真相"
        userTwo.subCount = 20323234
        userTwo.focusCount = 244
        userTwo.fansCount = 1938232
        userTwo.workCount = 123
        userTwo.dynamicCount = 123
        userTwo.likeCount = 344
        userList.add(userTwo)
        videoTwo.user = userTwo

        val videoThree = Video()
        videoThree.coverRes = R.mipmap.cover3
        videoThree.content = "科比生涯霸气庆祝动作，最后动作诠释了一生荣耀 #科比 @路人王篮球 "
        videoThree.videoRes = R.raw.video3
        videoThree.distance = 15.9f
        videoThree.isFocused = false
        videoThree.isLiked = false
        videoThree.likeCount = 592032
        videoThree.commentCount = 9221
        videoThree.shareCount = 982

        val userThree = User()
        userThree.uid = 3
        userThree.head = R.mipmap.head3
        userThree.nickName = "七叶篮球"
        userThree.sign = "老科的视频会一直保留，想他了就回来看看"
        userThree.subCount = 1039232
        userThree.focusCount = 159
        userThree.fansCount = 29232323
        userThree.workCount = 171
        userThree.dynamicCount = 173
        userThree.likeCount = 1724
        userList.add(userThree)
        videoThree.user = userThree

        val videoFour = Video()
        videoFour.coverRes = R.mipmap.cover4
        videoFour.content = "美好的一天，从发现美开始 #莉莉柯林斯 "
        videoFour.videoRes = R.raw.video4
        videoFour.distance = 25.2f
        videoFour.isFocused = false
        videoFour.isLiked = false
        videoFour.likeCount = 887232
        videoFour.commentCount = 2731
        videoFour.shareCount = 8924

        val userFour = User()
        userFour.uid = 4
        userFour.head = R.mipmap.head4
        userFour.nickName = "一只爱修图的剪辑师"
        userFour.sign = "接剪辑，活动拍摄，修图单\n 合作私信"
        userFour.subCount = 2689424
        userFour.focusCount = 399
        userFour.fansCount = 360829
        userFour.workCount = 562
        userFour.dynamicCount = 570
        userFour.likeCount = 4310
        userList.add(userFour)
        videoFour.user = userFour

        val videoFive = Video()
        videoFive.coverRes = R.mipmap.cover5
        videoFive.content = "有梦就去追吧，我说到做到。 #网球  #网球小威 "
        videoFive.videoRes = R.raw.video5
        videoFive.distance = 9.2f
        videoFive.isFocused = false
        videoFive.isLiked = false
        videoFive.likeCount = 8293241
        videoFive.commentCount = 982
        videoFive.shareCount = 8923

        val userFive = User()
        userFive.uid = 5
        userFive.head = R.mipmap.head5
        userFive.nickName = "国际网球联合会"
        userFive.sign = "ITF国际网球联合会负责制定统一的网球规则，在世界范围内普及网球运动"
        userFive.subCount = 1002342
        userFive.focusCount = 87
        userFive.fansCount = 520232
        userFive.workCount = 89
        userFive.dynamicCount = 122
        userFive.likeCount = 9
        userList.add(userFive)
        videoFive.user = userFive

        val videoSix = Video()
        videoSix.coverRes = R.mipmap.cover6
        videoSix.content = "能力越大，责任越大，英雄可能会迟到，但永远不会缺席  #蜘蛛侠 "
        videoSix.videoRes = R.raw.video6
        videoSix.distance = 16.4f
        videoSix.isFocused = true
        videoSix.isLiked = true
        videoSix.likeCount = 2109823
        videoSix.commentCount = 9723
        videoFive.shareCount = 424

        val userSix = User()
        userSix.uid = 6
        userSix.head = R.mipmap.head6
        userSix.nickName = "罗鑫颖"
        userSix.sign = "一个行走在Tr与剪辑之间的人\n 有什么不懂的可以来直播间问我"
        userSix.subCount = 29342320
        userSix.focusCount = 67
        userSix.fansCount = 7028323
        userSix.workCount = 5133
        userSix.dynamicCount = 5159
        userSix.likeCount = 0
        userList.add(userSix)
        videoSix.user = userSix

        val videoSeven = Video()
        videoSeven.coverRes = R.mipmap.cover7
        videoSeven.content = "真的拍不出来你的神颜！现场看大屏帅疯！#陈情令南京演唱会 #王一博 😭"
        videoSeven.videoRes = R.raw.video7
        videoSeven.distance = 16.4f
        videoSeven.isFocused = false
        videoSeven.isLiked = false
        videoSeven.likeCount = 185782
        videoSeven.commentCount = 2452
        videoSeven.shareCount = 3812

        val userSeven = User()
        userSeven.uid = 7
        userSeven.head = R.mipmap.head7
        userSeven.nickName = "Sean"
        userSeven.sign = "云深不知处"
        userSeven.subCount = 471932
        userSeven.focusCount = 482
        userSeven.fansCount = 371423
        userSeven.workCount = 242
        userSeven.dynamicCount = 245
        userSeven.likeCount = 839
        userList.add(userSeven)
        videoSeven.user = userSeven

        val videoEight = Video()
        videoEight.coverRes = R.mipmap.cover8
        videoEight.content = "逆序只是想告诉大家，学了舞蹈的她气质开了挂！"
        videoEight.videoRes = R.raw.video8
        videoEight.distance = 8.4f
        videoEight.isFocused = false
        videoEight.isLiked = false
        videoEight.likeCount = 1708324
        videoEight.commentCount = 8372
        videoEight.shareCount = 982

        val userEight = User()
        userEight.uid = 8
        userEight.head = R.mipmap.head8
        userEight.nickName = "曹小宝"
        userEight.sign = "一个晒娃狂魔麻麻，平日里没啥爱好！喜欢拿着手机记录孩子成长片段，风格不喜勿喷！"
        userEight.subCount = 1832342
        userEight.focusCount = 397
        userEight.fansCount = 1394232
        userEight.workCount = 164
        userEight.dynamicCount = 167
        userEight.likeCount = 0
        userList.add(userEight)
        videoEight.user = userEight

        datas.add(videoOne)
        datas.add(videoTwo)
        datas.add(videoThree)
        datas.add(videoFour)
        datas.add(videoFive)
        datas.add(videoSix)
        datas.add(videoSeven)
        datas.add(videoEight)
        datas.add(videoOne)
        datas.add(videoTwo)
        datas.add(videoThree)
        datas.add(videoFour)
        datas.add(videoFive)
        datas.add(videoSix)
        datas.add(videoSeven)
        datas.add(videoEight)
    }

    companion object {
        @JvmField
        var datas = ArrayList<Video>()
        @JvmField
        var userList = ArrayList<User>()
    }
}