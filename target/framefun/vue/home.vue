<template>
  <div>
    <el-menu style="margin: 20px;" :default-active="idx" class="el-menu-demo" mode="horizontal">
      <el-menu-item index="1" @click="selectGroup(0)">ALL</el-menu-item>
      <el-menu-item index="2" @click="selectGroup(1)">原画</el-menu-item>
      <el-menu-item index="3" @click="selectGroup(2)">分镜</el-menu-item>
      <el-menu-item index="4" @click="selectGroup(3)">人设</el-menu-item>
      <el-menu-item index="5" @click="selectGroup(4)">其他</el-menu-item>
    </el-menu>
<!--    <div class="info-block">-->
<!--      <el-link  type="info" :underline="false" style="padding-right: 12px">ALL</el-link>-->
<!--      <el-link @click="selectGroup(1)" type="info" :underline="false" style="padding-right: 12px">原画</el-link>-->
<!--      <el-link @click="selectGroup(2)" type="info" :underline="false" style="padding-right: 12px">分镜</el-link>-->
<!--      <el-link @click="selectGroup(3)" type="info" :underline="false" style="padding-right: 12px">人设</el-link>-->
<!--      <el-link @click="selectGroup(4)" type="info" :underline="false" style="padding-right: 12px">其他</el-link>-->
<!--    </div>-->
    <div ref="home" class="water-fall-block">
      <div
          class="artwork-block"
          v-for="(Artwork,index) in ArtWorks" :key="index"
          :style=`height:${Artwork.height}px;width:${artworkWidth}px;top:${Artwork.top}px;left:${Artwork.left}px`
      >
        <a>
          <el-image
              :src="Artwork.artwork_location"
              :data-artworkid="Artwork.artwork_id"
              style="position: relative;color: #FFFFFF;display: block"
              :style=`height:${Artwork.height}px`
              @click="artworkClick"
              :fit="Artwork.fit">
          </el-image>
        </a>
        <div @click="collectHandler" :data-artworkindex="index" class="a-love-div">
          <svg viewBox="0 0 32 32" width="32" height="32" :class={'love-svg':!Artwork.hasCollect,'love-svg-a':Artwork.hasCollect}>
            <path d="
M21,5.5 C24.8659932,5.5 28,8.63400675 28,12.5 C28,18.2694439 24.2975093,23.1517313 17.2206059,27.1100183
C16.4622493,27.5342993 15.5379984,27.5343235 14.779626,27.110148 C7.70250208,23.1517462 4,18.2694529 4,12.5
C4,8.63400691 7.13400681,5.5 11,5.5 C12.829814,5.5 14.6210123,6.4144028 16,7.8282366
C17.3789877,6.4144028 19.170186,5.5 21,5.5 Z">
            </path>
            <path d="M16,11.3317089 C15.0857201,9.28334665 13.0491506,7.5 11,7.5
C8.23857625,7.5 6,9.73857647 6,12.5 C6,17.4386065 9.2519779,21.7268174 15.7559337,25.3646328
C15.9076021,25.4494645 16.092439,25.4494644 16.2441073,25.3646326 C22.7480325,21.7268037 26,17.4385986 26,12.5
C26,9.73857625 23.7614237,7.5 21,7.5 C18.9508494,7.5 16.9142799,9.28334665 16,11.3317089 Z" :class={'love-patha':!Artwork.hasCollect,'love-pathb':Artwork.hasCollect}>
            </path>
          </svg>
        </div>
      </div>
    </div>
  </div>
</template>

<script type="module">


module.exports={

  data(){
    return{
      ArtWorks:[],
      artworkWidth:'',
      colNum:5,
      colHeight:[0,0,0,0,0],
      page:'',
      group:this.$route.params.group,
      idx:''
    }
  },
  methods:{
    selectGroup(e){
      var _this=this;
      this.group=e;
      this.ArtWorks=[];
      this.colHeight=[0,0,0,0,0];
      axios({
        method:'get',
        url:'/artworks/home/'+_this.group+"/"+0
      }).then(function (response) {
        var data = response.data;
        for(var i in data){
          _this.setArtwork(data[i]);
          _this.page=1;
        }
      });
      this.load();
    },
    setArtwork(artwork){
      var minHeight=-1;
      var index=0;
      for(var i in this.colHeight){
        if(this.colHeight[i]<minHeight||minHeight==-1){
          minHeight=this.colHeight[i];
          index=i;
        }
      }
      artwork.top=minHeight+16;
      artwork.left=20+index*16+index*this.artworkWidth;
      artwork.height=artwork.artwork_height*this.artworkWidth/artwork.artwork_width;
      var over = artwork.height/this.artworkWidth>=3;
      artwork.height = over?this.artworkWidth*2:artwork.height;
      artwork.fit = over?"cover":"contain";
      this.ArtWorks.push(artwork);
      this.colHeight[index]+=16+artwork.height;
    },
    load(){
      var _this = this;
      var isLoading = false;
      window.onscroll = ()=>{
        let bottomOfWindow = document.documentElement.scrollHeight - document.documentElement.scrollTop - window.innerHeight <= 30
        if(bottomOfWindow&&!isLoading){
          isLoading=true;
          axios({
            method:'get',
            url:'/artworks/home/'+_this.group+"/"+_this.page
          }).then(function (response) {
            var data = response.data;
            for(var i in data){
              _this.setArtwork(data[i]);
            }
            isLoading=false;
            if(data.length==0){
              _this.$message("已经是最后了");
              isLoading = true;
            }else{
              _this.page++;
            }
          });
        }
      }

    },
    artworkClick(e){
      var id = e.currentTarget.dataset.artworkid;
      this.$router.push({path:`/artworks/${id}`});
    },
    collectHandler(e){
      var _this = this;
      var index = e.currentTarget.dataset.artworkindex;
      if(this.ArtWorks[index].hasCollect==false){
        axios({
          method:'post',
          url:'/collections/'+this.ArtWorks[index].artwork_id,
        }).then(function (response) {
          var data = response.data;
          _this.ArtWorks[index].hasCollect=true;
        });
      }
      else{
        axios({
          method:'delete',
          url:'/collections/'+this.ArtWorks[index].artwork_id,
        }).then(function (response) {
          var data = response.data;
          _this.ArtWorks[index].hasCollect=false;
        });
      }
    }
  },
  mounted(){
    window.scrollTo(0,0);
    this.idx=(parseInt(this.group)+1)+'';
    this.page = 1;
    var _this = this;
    this.load();
    this.artworkWidth=(this.$refs.home.clientWidth-40-this.colNum*16+16)/this.colNum;
    axios({
      method:'get',
      url:'/session'
    }).then(function (response) {
      axios({
        method:'get',
        url:'/artworks/home/'+_this.group+"/"+'0'
      }).then(function (response) {
        var data = response.data;
        for(var i in data){
          _this.setArtwork(data[i]);
        }
      });
    });

  },
  destroyed(){
    window.onscroll=null;
  }
}
</script>

<style scoped>
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
  right: 0;
  bottom: 0;
  display: block;
}
.love-patha{
  fill: rgb(255,255,255);
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
.love-pathb{
  fill: rgb(255, 64, 96);
  transition: color 0.2s ease 0s, fill 0.2s ease 0s;
}
.water-fall-block{
  height: 100vh;
  position: relative;
}
.artwork-block{
  position: absolute;
  transition: transform .15s ease-in-out,box-shadow .15s ease-in-out;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  background-color: #FFFFFF;

}
.artwork-block:hover{
  box-shadow: 0 1px 3px rgba(0,0,0,.02), 0 16px 32px -4px rgba(0,0,0,.17);
  transform: translateY(-1px);
  overflow: hidden;
}
</style>
