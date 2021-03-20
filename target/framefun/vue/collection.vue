<template>
<div class="collection-block">
  <div style="height: 50px;text-align: left">我的收藏<el-tag style="margin-left: 5px"><i class="el-icon-star-on"></i>{{count}}</el-tag></div>
  <div class="collection-area" ref="collectionArea" :style="{
      'grid-template-columns': `repeat(${col},1fr)`}">
    <el-image
      @click="handleClick"
      v-for="(Artwork,index) in artworks" :key="index"
      :src="Artwork.artwork_location"
      :data-artworkid="Artwork.artwork_id"
      style="margin-top: 24px;border-radius: 15px"
      :style="`height:${collectionWidth}px;width:${collectionWidth}px`"
      fit="cover"
    >

    </el-image>
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
module.exports ={
  data(){
    return{
      artworks:[],
      collectionWidth:'',
      pageCount:0,
      currentPage:0,
      show:false,
      count:'',
      col:'6'
    }
  },
  methods:{
    handleClick(e){
      var id = e.currentTarget.dataset.artworkid;
      this.$router.push({path:`/artworks/${id}`});
    },
    pageChange(e){
      var _this = this;
      this.$router.push({path:`/collections/${e-1}`});
      axios({
        method: 'get',
        url: '/collections/'+(e-1)
      }).then(function (response) {
        var data = response.data;
        _this.artworks = [];
        for (var i in data) {
          _this.artworks.push(data[i]);
        }
        _this.show = false;
        _this.$nextTick(()=>{
          _this.currentPage = parseInt(e);
          _this.show = true;
        });
      });
    }
  },
  created() {
    var _this = this;
    this.currentPage = parseInt(this.$route.params.page)+1;
    axios({
      method: 'get',
      url: '/collections/count'
    }).then(function (response) {
      var data = response.data;
      _this.count = parseInt(data);
      _this.pageCount = Math.floor(parseInt(data)/24+1);

      axios({
        method: 'get',
        url: '/collections/'+(_this.currentPage-1)
      }).then(function (response) {
        var data = response.data;
        for (var i in data) {
          _this.artworks.push(data[i]);
        }

        _this.show= false;
        _this.$nextTick(()=>{
          _this.show=true;
        })
      });
    });
  },
  mounted(){
    this.collectionWidth = (this.$refs.collectionArea.clientWidth*0.9)/this.col;
  }
}
</script>

<style scoped>
.collection-block{
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
.collection-area{
  width: 100%;
  height: auto;
  display: grid;

}
.page{
  width: 100%;
  margin-top: 100px;
}
</style>
