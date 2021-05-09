<template>
  <div class="artwork-user-block">
    <div class="user-avatar" @click="goToUser" style="cursor: pointer">
      <el-avatar :size="64" style="float: left">
        <i class="el-icon-user-solid" v-if="!user.user_avatar"></i>
        <el-image  v-if="user.user_avatar" :src="user.user_avatar" fit="cover" style="height: 100%"></el-image>
      </el-avatar>
      <div style="float: left;font-size: 20px;margin-left: 30px;padding-top: 22px">{{user.user_name}}</div>
    </div>
    <el-divider></el-divider>
    <div>
      <div style="font-size: 18px">粉丝数：{{user.user_followers}}</div>
      <div style="padding: 12px 0 0 0" @click="follow">
        <el-button style="width: 100%" v-if="user.hasFollow!=true" type="primary" round>+关注</el-button>
        <el-button style="width: 100%" v-if="user.hasFollow==true" type="info" round>已关注</el-button>
      </div>
      <div style="display: flex;flex-wrap: wrap;justify-content: space-between;margin-top: 8px" ref="myflex" >
        <div :style="'width: '+(blockWidth<300?'100%':'calc(50% - 8px)')+';padding: '+(blockWidth<300?'100%':'calc(50% - 8px)')+' 0 0;height: 0;position: relative;margin-top: 12px'"
             v-for="(Artwork,index) in user.sampleArtworks" :key="Artwork.artwork_id">
          <el-image
              @click="handleClick(Artwork.artwork_id)"
              :src="Artwork.artwork_location"
              :data-artworkid="Artwork.artwork_id"
              style="border-radius: 15px;position: absolute;width: 100%;;height:100%;top:0;left: 0"
              fit="cover"
          ></el-image>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports={
  data(){
    return {
      user:'',
      artworkId:this.$route.params.id,
      blockWidth:300
    }
  },
  methods:{
    goToUser(){
      this.$router.push({path:`/artist/${this.user.user_id}`});
    },
    handleClick(id){
      var i = id;
      this.$router.push({path:`/artworks/${i}`});
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
    }
  },
  mounted(){
    var _this = this;
    this.$nextTick(() => { //使用nextTick为了保证dom元素都已经渲染完毕
      _this.blockWidth=_this.$refs.myflex.clientWidth;
    });
    axios({
      method:'get',
      url:'/artist/'+this.artworkId
    }).then(function (response) {
      var data = response.data;
      _this.user = Object.assign({},_this.user,data);
    });
    window.onresize = ()=>{
      _this.blockWidth=_this.$refs.myflex.clientWidth;
    };
  }
}
</script>

<style scoped>
.artwork-user-block{
  padding: 20px;
  margin-left: 30px;
  height: auto;
  text-align: right;
  background-color: #FFFFFF;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 50px;
  border-radius: 15px;
  text-align: center;

}
.user-avatar{
  width: 100%;
  display: inline-block;
  text-align: left;
}
</style>
