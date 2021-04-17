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

  },
  mounted(){
    var _this = this;
    axios({
      method:'get',
      url:'/artist/'+this.artworkId
    }).then(function (response) {
      var data = response.data;
      _this.user = data;
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
