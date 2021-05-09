<template>
  <div class="collection-block">
    <div style="position: relative">
      <div class="avatar-block">
        <el-avatar @click="home" :size="100" style="font-size:50px;float: left;cursor: pointer" >
          <i class="el-icon-user-solid" v-show="!url"></i>
          <el-image v-show="url" :src="url" fit="cover" style="height: 100%"></el-image>
        </el-avatar>
        <div style="font-size: 24px;float: left;margin-left: 50px;">
          <el-link :underline="false" @click="home" style="color:#303133;cursor: pointer;font-size: 24px">{{user.user_name}}</el-link>
          <div style="color: #8c939d;font-size: 16px;margin-top: 8px">id:{{user.user_id}}</div>
          <div style="padding: 12px 0 0 0" @click="follow">
            <el-button  v-if="user.hasFollow!=true" type="primary" round>+关注</el-button>
            <el-button  v-if="user.hasFollow==true" type="info" round>已关注</el-button>
          </div>
        </div>
      </div>
      <div style="text-align: left;position: absolute;right: 0">
        <div style="text-align: center;display: inline-block">
          <div style="color: #8c939d">粉丝数</div>
          <el-link @click="goToFollower" :underline="false" style="padding: 8px">{{user.user_followers}}</el-link>
        </div>
        <el-divider direction="vertical"></el-divider>
        <div style="text-align: center;display: inline-block">
          <div style="color: #8c939d">关注数</div>
          <el-link @click="goToFollowing" :underline="false" style="padding: 8px">{{user.user_following}}</el-link>
        </div>
        <el-divider direction="vertical"></el-divider>
        <div style="text-align: center;display: inline-block">
          <div style="color: #8c939d">收藏数</div>
          <el-link @click="moreCollection" :underline="false" style="padding: 8px">{{user.user_collectionCount}}</el-link>
        </div>
        <el-divider direction="vertical"></el-divider>
        <div style="text-align: center;display: inline-block">
          <div style="color: #8c939d">作品数</div>
          <el-link @click="moreArtwork" :underline="false" style="padding: 8px">{{user.user_artworkCount}}</el-link>
        </div>
      </div>
    </div>
    <div style="text-align: left;margin-top: 150px">
      <div>个人简介</div>
      <div  style="color: #8c939d;margin-top: 30px" ><div>{{comment}}</div></div>
    </div>
    <el-divider></el-divider>
    <div style="height: 50px;text-align: left"><div style="color:#606266;display: inline-block"><b>Ta的收藏</b></div><el-tag style="margin-left: 5px"><i class="el-icon-star-on"></i>{{count}}</el-tag></div>
    <div class="collection-area" ref="collectionArea" :style="{
      'grid-template-columns': `repeat(${col},1fr)`}">
      <div
          v-for="(Artwork,index) in artworks" :key="Artwork.artwork_id"
          style="position: relative;margin-top: 24px;"
          :style="`height:${collectionWidth}px;width:${collectionWidth}px`"
      >
        <el-image
            @click="handleClick"
            :src="Artwork.artwork_location"
            :data-artworkid="Artwork.artwork_id"
            style="border-radius: 15px"
            :style="`height:${collectionWidth}px;width:${collectionWidth}px`"
            fit="cover"
        >

          <div slot="error"  class="el-image__error">
            {{Artwork.error}}
          </div>
        </el-image>
        <div @click="collectHandler" :data-artworkindex="index" class="a-love-div">
          <svg viewBox="0 0 32 32" width="32" height="32" :class={'love-svg':!Artwork.hasCollect,'love-svg-a':Artwork.hasCollect}>
            <path d="
M21,5.5 C24.8659932,5.5 28,8.63400675 28,12.5 C28,18.2694439 24.2975093,23.1517313 17.2206059,27.1100183
C16.4622493,27.5342993 15.5379984,27.5343235 14.779626,27.110148 C7.70250208,23.1517462 4,18.2694529 4,12.5
C4,8.63400691 7.13400681,5.5 11,5.5 C12.829814,5.5 14.6210123,6.4144028 16,7.8282366
C17.3789877,6.4144028 19.170186,5.5 21,5.5 Z">
            </path>
            <path d="M16,11.3317089 C15.0857201,9.28334665 13.0491506,7.5 11,7.5
C8.23857625,7.5 6,9.73857647 6,12.5 C6,17.4386065 9.2519779,21.7268174 15.7559337,25.3646328
C15.9076021,25.4494645 16.092439,25.4494644 16.2441073,25.3646326 C22.7480325,21.7268037 26,17.4385986 26,12.5
C26,9.73857625 23.7614237,7.5 21,7.5 C18.9508494,7.5 16.9142799,9.28334665 16,11.3317089 Z" :class={'love-patha':!Artwork.hasCollect,'love-pathb':Artwork.hasCollect}>
            </path>
          </svg>
        </div>
      </div>
    </div>
    <el-pagination
        v-if="show"
        :page-count="pageCount"
        class="page"
        background
        layout="prev, pager, next"
        @current-change="pageChange"
        :current-page="currentPage"
    >
    </el-pagination>
    <div style="height: 50px"></div>
  </div>
</template>

<script>
module.exports ={
  data(){
    return{
      userId:this.$route.params.id,
      artworks:[],
      collectionWidth:'',
      pageCount:0,
      currentPage:0,
      show:false,
      count:'',
      col:'6',
      hasGet:false,
      user:'',
      url:'',
      comment:''
    }
  },
  methods:{
    goToFollower(){
      this.$router.push({path:`/follow/follower/${this.user.user_id}/0`});
    },
    goToFollowing(){
      this.$router.push({path:`/follow/following/${this.user.user_id}/0`});
    },
    moreCollection(){
      this.$router.push({path:`/artist/collection/${this.userId}/0`});
    },
    moreArtwork(){
      this.$router.push({path:`/artist/artwork/${this.userId}/0`});
    },
    follow(){
      var _this = this;
      if(this.user.hasFollow==null)return;
      if(this.user.hasFollow==false){
        axios({
          method:'post',
          url:'/follow/'+this.user.user_id
        }).then(function (response) {
          var data = response.data;
          if(data==true){
            _this.$set(_this.user,"hasFollow",true);
            var user=_this.user;
            user.user_followers++;
            _this.user=user;
          }
        });
      }
      else{
        axios({
          method:'delete',
          url:'/follow/'+this.user.user_id
        }).then(function (response) {
          var data = response.data;
          if(data==true){
            _this.$set(_this.user,"hasFollow",false);
            var user=_this.user;
            user.user_followers--;
            _this.user=user;
          }
        });
      }
    },
    collectHandler(e){
      var _this = this;
      var index = e.currentTarget.dataset.artworkindex;
      if(this.artworks[index].hasCollect==false){
        axios({
          method:'post',
          url:'/collections/'+this.artworks[index].artwork_id,
        }).then(function (response) {
          var data = response.data;
          _this.artworks[index].hasCollect=true;
        });
      }
      else{
        axios({
          method:'delete',
          url:'/collections/'+this.artworks[index].artwork_id,
        }).then(function (response) {
          var data = response.data;
          _this.artworks[index].hasCollect=false;
        });
      }
    },
    handleClick(e){
      var id = e.currentTarget.dataset.artworkid;
      this.$router.push({path:`/artworks/${id}`});
    },
    home(){
      this.$router.push({path:`/artist/${this.userId}`});
    },
    pageChange(e){
      var _this = this;
      this.$router.push({path:`/artist/collection/${this.userId}/${e-1}`});
    }
  },
  created() {
    var _this = this;
    this.currentPage = parseInt(this.$route.params.page)+1;
    axios({
      method:'get',
      url:'/artist/'+this.userId+'/info'
    }).then(function (response) {
      var data = response.data;
      _this.user = data;
      _this.url = data.user_avatar;
      _this.comment = data.user_comment==null?_this.comment:data.user_comment;

      _this.count = parseInt(_this.user.user_collectionCount);
      _this.pageCount = Math.floor((parseInt(_this.user.user_collectionCount)+23)/24);
      if(_this.pageCount==0)_this.pageCount=1;
      _this.show= false;
      _this.$nextTick(()=>{
        _this.show=true;
      })
    });
    axios({
      method:'get',
      url:'/artist/'+_this.userId+'/collections/'+(this.currentPage-1)
    }).then(function (response) {
      var data = response.data;
      for(var i in data){
        _this.artworks.push(data[i]);
      }

    });

  },
  mounted(){
    window.scrollTo(0,0);
    var _this = this;
    this.$router.app.colSpan=24;
    this.$nextTick(()=>{
      this.collectionWidth = (this.$refs.collectionArea.clientWidth*0.9)/this.col;
      if(this.collectionWidth<180){
        this.col=4;
        this.collectionWidth = (this.$refs.collectionArea.clientWidth*0.9)/this.col;
        if(this.collectionWidth<130){
          this.col=2;
          this.collectionWidth = (this.$refs.collectionArea.clientWidth*0.9)/this.col;
        }
      }
    });
    window.onresize = ()=>{
      _this.$nextTick(()=>{
        _this.col=6;
        _this.collectionWidth = (_this.$refs.collectionArea.clientWidth*0.9)/_this.col;
        if(_this.collectionWidth<180){
          _this.col=4;
          _this.collectionWidth = (_this.$refs.collectionArea.clientWidth*0.9)/_this.col;
          if(_this.workWidth<130){
            _this.col=2;
            _this.collectionWidth = (_this.$refs.collectionArea.clientWidth*0.9)/_this.col;
          }
        }
      });
    };
  }
}
</script>

<style scoped>
.collection-block{
  padding-top: 30px;
  margin-right: 80px;
  margin-left: 80px;
  height: auto;
  text-align: center;
  padding-left: 48px;
  padding-right: 48px;
  background-color: #FFFFFF;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 50px;
  border-radius: 15px;
}
.collection-area{
  width: 100%;
  height: auto;
  display: grid;

}
.page{
  width: 100%;
  margin-top: 100px;
}
.love-svg{
  box-sizing: border-box;
  line-height: 0;
  font-size: 0px;
  vertical-align: top;
  color: rgb(31, 31, 31);
  fill: currentcolor;
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
.love-svg-a{
  box-sizing: border-box;
  line-height: 0;
  font-size: 0px;
  vertical-align: top;
  color: rgb(255,64,96);
  fill: currentcolor;
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
.a-love-div{
  position: absolute;
  right: 5px;
  bottom: 5px;
  display: block;
  z-index: 2;
}
.love-patha{
  fill: rgb(255,255,255);
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
.love-pathb{
  fill: rgb(255,64,96);
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
</style>
