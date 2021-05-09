<template>
  <div class="hot-block" style="text-align: left">
    <div v-for="(artwork,index) in artworks " :key="artwork.artwork_id">
      <div style="display: inline-block;">
        <div
            style="display: inline-block;float: left;font-size: 24px"
            :style="`color:${index<3?'#F56C6C':'#909399'}`"
        ><b>{{index+1}}</b></div>
        <div style="display: inline-block;float: left;padding-left: 12px">
          <el-image
              @click="handleClick"
              :src="artwork.artwork_location"
              :data-artworkid="artwork.artwork_id"
              style="margin:auto;border-radius: 15px;height: 200px;width: 300px"
              fit="cover"
          ></el-image>
        </div>
        <div style="padding-left: 16px;position: relative;height: 204px;margin-left: 330px;width: 100%" >
          <div>{{artwork.artwork_name}}</div>
          <div style="color: #909399;position: absolute;bottom: 0;">
            <div style="padding-bottom: 12px">
              <div style="display:inline-block">评论数：{{artwork.artwork_commentCount}}</div>
              <el-divider direction="vertical"></el-divider>
              <div style="display: inline-block">收藏数：{{artwork.artwork_collectCount}}</div>
            </div>
            <div>{{artwork.artwork_createTime}}</div>
          </div>
        </div>
      </div>
      <el-divider></el-divider>
    </div>
  </div>

</template>

<script>
module.exports={
  data(){
    return{
      artworks:[]
    }
  },
  methods:{
    handleClick(e){
      if(!this.isManage){
        var id = e.currentTarget.dataset.artworkid;
        this.$router.push({path:`/artworks/${id}`});
      }
    },

  },
  mounted(){
    window.scrollTo(0,0);
    var _this = this;
    this.$router.app.colSpan=24;
    axios({
      method: 'get',
      url: '/hot/artwork'
    }).then(function (response) {
      var data = response.data;
      for (var i in data) {
        _this.artworks.push(data[i]);
      }
    });
  }
}
</script>

<style scoped>
.hot-block{
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
</style>
