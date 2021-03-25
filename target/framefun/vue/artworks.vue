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
          <el-input type="textarea" v-model="form.comment" placeholder="发表一条评论吧" style="margin: auto"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="uploadArtwork" style="margin: auto">发送！</el-button>
        </el-form-item>
      </el-form>
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
      }
    }
  },
  mounted(){
    this.$router.app.colSpan=20;
    var _this = this;
    axios({
      method:'get',
      url:'/artworks/'+this.artworkId,
    }).then(function (response) {
      var data = response.data;
      _this.artwork = data;
      _this.imageList.push(data.artwork_location)
    });
  },
  destroyed(){
    this.$router.app.colSpan=24;
  }

}
</script>

<style scoped>
.artwork-block{
  margin: 30px;
  height: auto;
  background: #FFFFFF;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 18px;
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
  padding-bottom: 20px;
}
</style>
