<template>
  <div class="user-block">
    <div class="avatar-block">
      <el-avatar :size="100" style="font-size:50px;float: left" @mouseover.native="avatarover" @mouseout.native="avatarout">
        <i class="el-icon-user-solid" v-show="!hover&&!url"></i>
        <el-image v-show="!hover&&url" :src="url" fit="cover" style="height: 100%"></el-image>
        <div v-show="hover" style="font-size: 18px;">
          <el-upload
              :multiple="false"
              style="font-size: 32px"
              class="avatar-uploader"
              action="/user/info/avatar"
              :show-file-list="false"
              :on-success="successHandler"
          >
            <i class="el-icon-plus avatar-uploader-icon" style="font-size: 32px;"></i>
          </el-upload>
        </div>
      </el-avatar>
      <div style="font-size: 24px;float: left;margin-left: 50px;padding-top: 20px">
        <div>{{user.user_name}}</div>
        <div style="color: #8c939d;font-size: 16px;margin-top: 8px">id:{{user.user_id}}</div>
      </div>
    </div>
    <div style="text-align: left;margin-top: 40px">
      <div style="text-align: center;display: inline-block">
        <div @click="goToFollower" style="color: #8c939d">粉丝数</div>
        <el-link :underline="false" style="padding: 8px">{{user.user_followers}}</el-link>
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
      <div v-if="!edit" style="color: #8c939d" @click="editComment"><div>{{comment}}<i class="el-icon-edit"></i></div></div>
      <div v-else><el-input v-model="comment" :placeholder="comment" @blur="updateComment"></el-input></div>
    </div>
    <el-button type="danger" style="margin-top: 100px" @click="logout">退出登录</el-button>
  </div>
</template>

<script>
module.exports= {
  data(){
    return {
      user:'',
      hover:false,
      url:'',
      comment:'用户还没有留下简介',
      edit:false
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
    editComment(){
      this.edit=!this.edit;
    },
    updateComment(){
      var _this = this;
      axios({
        headers:{
          'Content-Type':'application/x-www-form-urlencoded'
        },
        method:'post',
        url:'/user/info/comment',
        data:"comment="+this.$data.comment

      }).then(function (response) {
        _this.edit=false
      });
    },
    logout(){
      var _this = this;
      axios({
        method:'delete',
        url:'/session'
      }).then(function (response) {
        window.location.href="/login.html";
      });
    },
    successHandler(res){
      this.url = res;
    },
    avatarover(){
      this.hover=true;
    },
    avatarout(){
      this.hover=false;
    }
  },
  mounted(){
    var _this = this;
    axios({
      method:'get',
      url:'/session'
    }).then(function (response) {
      var data = response.data;
      _this.user = data;
      _this.url = data.user_avatar;
      _this.comment = data.user_comment==null?_this.comment:data.user_comment;
    });
  }
}
</script>

<style scoped>
.user-block{
  padding: 30px;
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
.avatar-block{
  width: 100%;
  display: inline-block;
  text-align: left;
}


</style>
