<template>
  <div class="user-block">
    <div class="avatar-block">
      <el-avatar :size="100" style="font-size:50px;float: left" >
        <i class="el-icon-user-solid" v-show="!url"></i>
        <el-image v-show="url" :src="url" fit="cover" style="height: 100%"></el-image>
      </el-avatar>
      <div style="font-size: 24px;float: left;margin-left: 50px;">
        <div>{{user.user_name}}</div>
        <div style="color: #8c939d;font-size: 16px;margin-top: 8px">id:{{user.user_id}}</div>
        <div style="padding: 12px 0 0 0" @click="follow">
          <el-button  v-if="user.hasFollow!=true" type="primary" round>+关注</el-button>
          <el-button  v-if="user.hasFollow==true" type="info" round>已关注</el-button>
        </div>
      </div>
    </div>
    <div style="text-align: left;margin-top: 40px">
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
    <div style="text-align: left;margin-top: 50px">
      <div>个人简介</div>
      <div  style="color: #8c939d;margin-top: 30px" ><div>{{comment}}</div></div>
    </div>
    <el-divider></el-divider>
    <div>
      <div style="text-align: left;position: relative">
        <div style="color:#606266;display: inline-block;padding-right: 8px"><b>Ta的作品</b></div>
        <div style="display: inline-block"><el-tag size="small">{{user.user_artworkCount}}</el-tag></div>
        <div @click="moreArtwork" style="padding-left: 18px;display: inline-block;color: #8c939d;cursor: pointer;position: absolute;right: 0">查看更多<i class="el-icon-caret-right"></i></div>
      </div>
      <div v-if="artworks.length==0" style="color: #8c939d;margin-bottom: 60px;margin-top: 30px"><b>用户还没有分享任何作品</b></div>
      <el-carousel v-if="artworks.length!=0" :loop="false" :autoplay="false" type="card" height="250px" style="margin-top: 30px">
        <el-carousel-item  v-for="(artwork,index) in artworks" :key="artwork.artwork_id">
          <el-image
              @click="handleClick(artwork.artwork_id)"
              :src="artwork.artwork_location"
              :data-artworkid="artwork.artwork_id"
              style="width: 400px"
              fit="cover"
          ></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>
    <el-divider></el-divider>
    <div>
      <div style="text-align: left;position: relative">
        <div style="color:#606266;display: inline-block;padding-right: 8px"><b>Ta的收藏</b></div>
        <div style="display: inline-block"><el-tag size="small">{{user.user_collectionCount}}</el-tag></div>
        <div @click="moreCollection" style="padding-left: 18px;display: inline-block;color: #8c939d;cursor: pointer;position:absolute;right: 0 ">查看更多<i class="el-icon-caret-right"></i></div>
      </div>
      <div v-if="collections.length==0" style="color: #8c939d;margin-bottom: 60px;margin-top: 30px"><b>用户还没有收藏任何作品</b></div>
      <el-carousel v-if="collections.length!=0" :loop="false" :autoplay="false" type="card" height="250px" style="margin-top: 30px">
        <el-carousel-item v-for="(artwork,index) in collections" :key="artwork.artwork_id+'f'">
          <el-image
              @click="handleClick(artwork.artwork_id)"
              :src="artwork.artwork_location"
              :data-artworkid="artwork.artwork_id"
              style="width: 400px"
              fit="cover"
          ></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
module.exports= {
  data(){
    return {
      user:'',
      url:'',
      comment:'用户还没有留下简介',
      userId:this.$route.params.id,
      artworks:[],
      collections:[]
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
    handleClick(id){
      var i = id;
      this.$router.push({path:`/artworks/${i}`});
    },
    successHandler(res){
      this.url = res;
    },
  },
  mounted(){
    window.scrollTo(0,0);
    var _this = this;
    axios({
      method:'get',
      url:'/artist/'+this.userId+'/info'
    }).then(function (response) {
      var data = response.data;
      _this.user = data;
      _this.url = data.user_avatar;
      _this.comment = data.user_comment==null?_this.comment:data.user_comment;
    });
    axios({
      method:'get',
      url:'/artist/'+this.userId+'/artworks/0'
    }).then(function (response) {
      var data = response.data;
      for(var i in data){
        if(i>=6)break;
        _this.artworks.push(data[i]);
      }

    });
    axios({
      method:'get',
      url:'/artist/'+this.userId+'/collections/0'
    }).then(function (response) {
      var data = response.data;
      for(var i in data){
        if(i>=6)break;
        _this.collections.push(data[i]);
      }

    });
  }
}
</script>

<style scoped>
.user-block{
  padding: 30px;
  margin: auto;
  width: 70%;
  height: auto;
  text-align: center;
  padding-left: 48px;
  padding-right: 48px;
  background-color: #FFFFFF;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 50px;
  border-radius: 15px;
}
.avatar-block{
  width: 100%;
  display: inline-block;
  text-align: left;
}


</style>
