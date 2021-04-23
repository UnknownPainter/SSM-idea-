<template>
  <div class="artwork-user-block">
    <div class="user-avatar">
      <el-avatar :size="64" style="float: left">
        <el-image  v-if="user" :src="user.user_avatar" fit="cover" style="height: 100%"></el-image>
      </el-avatar>
      <div style="float: left;font-size: 20px;margin-left: 30px;padding-top: 22px">{{user.user_name}}</div>
    </div>
    <el-divider></el-divider>
    <div>
      <div style="font-size: 18px">关注者：{{user.user_following}}</div>
      <div style="padding: 12px 0 0 0" @click="follow">
        <el-button style="width: 100%" v-if="user.hasFollow!=true" type="primary" round>+关注</el-button>
        <el-button style="width: 100%" v-if="user.hasFollow==true" type="info" round>已关注</el-button>
      </div>
    </div>
  </div>
</template>

<script>
module.exports={
  data(){
    return {
      user:'',
      artworkId:this.$route.params.id
    }
  },
  methods:{
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
          }
        });
      }
    }
  },
  mounted(){
    var _this = this;
    axios({
      method:'get',
      url:'/artist/'+this.artworkId
    }).then(function (response) {
      var data = response.data;
      _this.user = Object.assign({},_this.user,data);
    });
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
