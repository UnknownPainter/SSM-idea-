<template>
  <div class="follow-block" style="text-align: center">
    <div style="height: 50px;text-align: left"><div style="color:#606266;display: inline-block"><b>{{artist.user_name}}的粉丝</b></div><el-tag style="margin-left: 5px"><i class="el-icon-star-on"></i>{{count}}</el-tag></div>
    <el-divider></el-divider>
    <div v-for="(user,index) in users " style="text-align:left;width: 100%;display: inline-block" :key="user.user_id">
      <div style="display: inline-block;width: 100%;">
        <div style="display: inline-block;float: left;padding-left: 12px">
          <div @click="goToUser(index)" style="cursor: pointer;display: inline-block;height: 64.8px;float: left;padding: 8px 0 8px 0">
            <el-avatar :size="64">
              <el-image :src="user.user_avatar" fit="cover" style="height: 100%" v-show="user.user_avatar"></el-image>
              <i class="el-icon-user-solid" v-show="!user.user_avatar"></i>
            </el-avatar>
          </div>
        </div>
        <div style="padding-left: 24px;position: relative;height: 100px;margin-left: 64px;" >
          <div>{{user.user_name}}</div>
          <div style="color: #909399;position: absolute;bottom: 0;">
            <div style="padding-bottom: 12px">
              <div style="display:inline-block">作品数：{{user.user_artworkCount}}</div>
              <el-divider direction="vertical"></el-divider>
              <div style="display: inline-block">粉丝数：{{user.user_followers}}</div>
            </div>
          </div>
          <div style="position: absolute;right: 0">
            <div style="padding: 12px 0 0 0" @click="follow(index)">
              <el-button  v-if="user.hasFollow!=true" type="primary" round>+关注</el-button>
              <el-button  v-if="user.hasFollow==true" type="info" round>已关注</el-button>
            </div>
          </div>
        </div>
      </div>
      <el-divider></el-divider>
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
module.exports={
  data(){
    return{
      users:[],
      userId:this.$route.params.id,
      currentPage:'',
      count:'',
      artist:'',
      show:false,
      pageCount:''
    }
  },
  methods:{
    pageChange(e){
      var _this = this;
      this.$router.push({path:`/follow/follower/${this.userId}/${e-1}`});
    },
    follow(e){
      var _this = this;
      var us = this.users[e];
      if(us.hasFollow==null)return;
      if(us.hasFollow==false){
        axios({
          method:'post',
          url:'/follow/'+us.user_id
        }).then(function (response) {
          var data = response.data;
          if(data==true){
            us.hasFollow=true;
            us.user_followers++;
            _this.$set(_this.users,e,us)
          }
        });
      }
      else{
        axios({
          method:'delete',
          url:'/follow/'+us.user_id
        }).then(function (response) {
          var data = response.data;
          if(data==true){
            us.hasFollow=false;
            us.user_followers--;
            _this.$set(_this.users,e,us)
          }
        });
      }
    },
    handleClick(e){
      if(!this.isManage){
        var id = e.currentTarget.dataset.artworkid;
        this.$router.push({path:`/artworks/${id}`});
      }
    },
    goToUser(e){
      this.$router.push({path:`/artist/${this.users[e].user_id}`});
    },

  },
  mounted(){
    window.scrollTo(0,0);

    var _this = this;
    _this.currentPage = parseInt(_this.$route.params.page)+1;
    this.$router.app.colSpan=24;
    axios({
      method:'get',
      url:'/artist/'+this.userId+'/info'
    }).then(function (response) {
      var data = response.data;
      _this.artist = data;
      _this.count = parseInt(_this.artist.user_followers);
      _this.pageCount = Math.floor((parseInt(_this.artist.user_followers)+23)/24);
      if(_this.pageCount==0)_this.pageCount=1;
      _this.show= false;
      _this.$nextTick(()=>{
        _this.show=true;
      })
    });
    axios({
      method: 'get',
      url: `/follow/follower/${this.userId}/${this.currentPage-1}`
    }).then(function (response) {
      var data = response.data;
      for (var i in data) {
        _this.users.push(data[i]);
      }
    });

  }
}
</script>

<style scoped>
.follow-block{
  width: 80%;
  padding-top: 30px;
  margin: auto;
  height: auto;
  text-align: center;
  padding-left: 48px;
  padding-right: 48px;
  background-color: #FFFFFF;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 50px;
  border-radius: 15px;
}
</style>
