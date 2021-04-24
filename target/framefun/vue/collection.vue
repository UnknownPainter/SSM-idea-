<template>
<div class="collection-block">
  <div style="height: 50px;text-align: left">我的收藏<el-tag style="margin-left: 5px"><i class="el-icon-star-on"></i>{{count}}</el-tag></div>
  <div class="collection-area" ref="collectionArea" :style="{
      'grid-template-columns': `repeat(${col},1fr)`}">
    <div
        v-for="(Artwork,index) in artworks" :key="Artwork.artwork_id"
        style="position: relative;margin-top: 24px;"
        :style="`height:${collectionWidth}px;width:${collectionWidth}px`"
    >
      <el-image
          @click="handleClick"
          :src="Artwork.artwork_location"
          :data-artworkid="Artwork.artwork_id"
          style="border-radius: 15px"
          :style="`height:${collectionWidth}px;width:${collectionWidth}px`"
          fit="cover"
      >

        <div slot="error"  class="el-image__error">
          {{Artwork.error}}
        </div>
      </el-image>
      <div @click="collectHandler" :data-artworkindex="index" class="a-love-div">
        <svg viewBox="0 0 32 32" width="32" height="32" :class={'love-svg':Artwork.isDeleted,'love-svg-a':!Artwork.isDeleted}>
          <path d="
M21,5.5 C24.8659932,5.5 28,8.63400675 28,12.5 C28,18.2694439 24.2975093,23.1517313 17.2206059,27.1100183
C16.4622493,27.5342993 15.5379984,27.5343235 14.779626,27.110148 C7.70250208,23.1517462 4,18.2694529 4,12.5
C4,8.63400691 7.13400681,5.5 11,5.5 C12.829814,5.5 14.6210123,6.4144028 16,7.8282366
C17.3789877,6.4144028 19.170186,5.5 21,5.5 Z">
          </path>
          <path d="M16,11.3317089 C15.0857201,9.28334665 13.0491506,7.5 11,7.5
C8.23857625,7.5 6,9.73857647 6,12.5 C6,17.4386065 9.2519779,21.7268174 15.7559337,25.3646328
C15.9076021,25.4494645 16.092439,25.4494644 16.2441073,25.3646326 C22.7480325,21.7268037 26,17.4385986 26,12.5
C26,9.73857625 23.7614237,7.5 21,7.5 C18.9508494,7.5 16.9142799,9.28334665 16,11.3317089 Z" :class={'love-patha':Artwork.isDeleted,'love-pathb':!Artwork.isDeleted}>
          </path>
        </svg>
      </div>
    </div>
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
      col:'6',
      hasGet:false,
      user:''
    }
  },
  methods:{
    collectHandler(e){
      var _this = this;
      var index = e.currentTarget.dataset.artworkindex;
      console.log(index)
      if(this.artworks[index].isDeleted!=false){
        axios({
          method:'post',
          url:'/collections/'+this.artworks[index].artwork_id,
        }).then(function (response) {
          var data = response.data;
          _this.artworks[index].isDeleted=false;
          _this.count++;
        });
      }
      else{
        axios({
          method:'delete',
          url:'/collections/'+this.artworks[index].artwork_id,
        }).then(function (response) {
          var data = response.data;
          _this.artworks[index].isDeleted=true;
          _this.count--;
        });
      }
    },
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
    this.user = this.$router.app.user;
    this.$root.$on('user',(a)=>{
      _this.user = '';
      _this.user = a;
      if(this.user!=null&&this.user!=''&&_this.hasGet==false){
        this.hasGet = true;
        axios({
          method: 'get',
          url: '/collections/'+(_this.currentPage-1)
        }).then(function (response) {
          var data = response.data;
          for (var i in data) {
            data[i].isDeleted = false;
            data[i].error=data[i].artwork_location==null?"作品已被删除":"加载失败";
            _this.artworks.push(data[i]);
          }
        });
      }
      _this.currentPage = parseInt(_this.$route.params.page)+1;
      _this.count = parseInt(_this.user.user_collectionCount);
      _this.pageCount = Math.floor((parseInt(_this.user.user_collectionCount)+23)/24);
      if(_this.pageCount==0)_this.pageCount=1;
      _this.show= false;
      _this.$nextTick(()=>{
        _this.show=true;
      })
    });
    this.currentPage = parseInt(this.$route.params.page)+1;
    this.count = parseInt(this.user.user_collectionCount);
    this.pageCount = Math.floor((parseInt(this.user.user_collectionCount)+23)/24);
    if(this.pageCount==0)this.pageCount=1;
    this.show= false;
    this.$nextTick(()=>{
      _this.show=true;
    })

    if(this.user!=null&&this.user!=''){
      this.hasGet = true;
      axios({
        method: 'get',
        url: '/collections/'+(_this.currentPage-1)
      }).then(function (response) {
        var data = response.data;
        for (var i in data) {
          data[i].isDeleted = false;
          data[i].error=data[i].artwork_location==null?"作品已被删除":"加载失败";
          _this.artworks.push(data[i]);
        }
      });
    }
  },
  mounted(){
    this.$router.app.colSpan=24;
    this.$nextTick(()=>{
      this.collectionWidth = (this.$refs.collectionArea.clientWidth*0.9)/this.col;
    });
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
.love-svg{
  box-sizing: border-box;
  line-height: 0;
  font-size: 0px;
  vertical-align: top;
  color: rgb(31, 31, 31);
  fill: currentcolor;
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
.love-svg-a{
  box-sizing: border-box;
  line-height: 0;
  font-size: 0px;
  vertical-align: top;
  color: rgb(255,64,96);
  fill: currentcolor;
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
.a-love-div{
  position: absolute;
  right: 5px;
  bottom: 5px;
  display: block;
  z-index: 2;
}
.love-patha{
  fill: rgb(255,255,255);
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
.love-pathb{
  fill: rgb(255,64,96);
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
</style>
