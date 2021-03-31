<template>
  <div>
    <div class="artwork-block" >
      <el-image
          v-if="artwork"
          style="width: 100%"
          :src="artwork.artwork_location"
          fit="contain"
          :preview-src-list="imageList"
      ></el-image>
    </div>
    <div class="info-block">
      <div style="font-size: 20px;line-height: 1.7;">{{artwork.artwork_name}}</div>
      <div style="color: #8c939d;font-size: 14px;line-height: 1.7;margin-top: 8px">{{artwork.artwork_comment==""?"上传者没有留下评论":artwork.artwork_comment}}</div>
    </div>
    <div class="comment-block">
      <el-form :model="form" class="form-block">
        <el-form-item>
          <el-row>
            <el-col :span="2" style="padding-top: 2px;height: 64.8px">
              <el-avatar :size="48">
                <el-image :src="user.user_avatar" fit="cover" style="height: 100%" v-show="user"></el-image>
                <i class="el-icon-user-solid" v-show="!user.user_avatar"></i>
              </el-avatar>
            </el-col>
            <el-col :span="20" style="padding-right: 20px">
              <el-input type="textarea" style="font-size: 12px;" v-model="form.comment" :rows="3" placeholder="发表一条评论吧" style="margin: auto;width: 100%"></el-input>
            </el-col>
            <el-col :span="2" style="text-align: right;">
              <el-button type="primary" @click="uploadComment" style="height:64.8px;">发送！</el-button>
            </el-col>
          </el-row>
        </el-form-item>

      </el-form>
      <el-divider></el-divider>
    </div>
  </div>
</template>

<script>
module.exports={
  data(){
    return{
      artworkId:this.$route.params.id,
      artwork:'',
      imageList:[],
      form:{
        comment:''
      },
      user:''
    }
  },
  methods:{
    uploadComment(){
      this.user = 1;
    }
  },
  mounted(){
    this.$router.app.colSpan=18;
    var _this = this;
    axios({
      method:'get',
      url:'/artworks/'+this.artworkId,
    }).then(function (response) {
      var data = response.data;
      _this.artwork = data;
      _this.imageList.push(data.artwork_location)
    });
    this.user = this.$router.app.user;
    console.log(this.user)
    this.$root.$on('user',(a)=>{
      _this.user = '';
      _this.user = a;
      console.log("ddfdfs"+_this.user)
    });
  },
  destroyed(){
    this.$router.app.colSpan=24;
  }

}
</script>

<style scoped>
.artwork-block{
  height: auto;
  background: #FFFFFF;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-right: 30px;
  margin-bottom: 18px;
  margin-left: 30px;
  margin-top: 0;
}
.comment-block{
  padding: 30px;
  margin: 30px;
  height: auto;
  background: #FFFFFF;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 50px;
  margin-top: 0;
}
.info-block{
  padding: 10px 30px 10px 30px;
  margin: 30px;
  height: auto;
  background: #FFFFFF;
  text-align: left;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 18px;
  margin-top: 0;
}
.form-block{
  width: 100%;
}
</style>
