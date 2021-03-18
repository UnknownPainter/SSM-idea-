<template>
  <div class="artwork-block" >
    <el-image
        v-if="artwork"
        style="width: 800px"
        :src="artwork.artwork_location"
        fit="contain"
        :preview-src-list="imageList"
    ></el-image>
    <div style="font-size: 20px;line-height: 1.7">{{artwork.artwork_name}}</div>
  </div>
</template>

<script>
module.exports={
  data(){
    return{
      artworkId:this.$route.params.id,
      artwork:'',
      imageList:[]
    }
  },
  mounted(){
    var _this = this;
    axios({
      method:'get',
      url:'/artworks/'+this.artworkId,
    }).then(function (response) {
      var data = response.data;
      _this.artwork = data;
      _this.imageList.push(data.artwork_location)
    });
  }

}
</script>

<style scoped>
.artwork-block{
  height: auto;
  min-height: 100%;
  width: 100%;
  background: #FFFFFF;
  text-align: center;
  box-shadow: 0 1px 3px rgba(18,18,18,.1);
}
</style>
