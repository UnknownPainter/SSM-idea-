<template>
  <div style="padding-right: 30px;padding-left: 30px">
    <div class="user-block">
      <div style="text-align: left;position: relative">
        <div style="color:#606266;display: inline-block;padding-right: 8px;font-size: 20px"><b>原画</b></div>
        <div @click="moreArtwork1" style="padding-left: 18px;display: inline-block;color: #8c939d;cursor: pointer;position: absolute;right: 0">查看更多<i class="el-icon-caret-right"></i></div>
      </div>
      <div style="display: flex;flex-wrap: wrap;justify-content: space-between;margin-top: 8px" ref="myflex" >
        <div :style="'width: calc(16% - 6px);padding: calc(16% - 8px) 0 0;height: 0;position: relative;margin-top: 12px'"
             v-for="(Artwork,index) in artworks1" :key="Artwork.artwork_id">
          <el-image
              @click="handleClick"
              :src="Artwork.artwork_location"
              :data-artworkid="Artwork.artwork_id"
              style="border-radius: 15px; position: absolute;width: 100%;;height:100%;top:0;left: 0"
              fit="cover"
          >

            <div slot="error"  class="el-image__error">
              {{Artwork.error}}
            </div>
          </el-image>
        </div>
      </div>
    </div>
    <div style="width: 100%;display: flex;flex-wrap: wrap;justify-content: space-between;">
      <div class="a-block" style="display:inline-block;width: calc(50% - 110px)">
        <div style="text-align: left;position: relative">
          <div style="color:#606266;display: inline-block;padding-right: 8px;font-size: 20px"><b>分镜</b></div>
          <div @click="moreArtwork2" style="padding-left: 18px;display: inline-block;color: #8c939d;cursor: pointer;position: absolute;right: 0">查看更多<i class="el-icon-caret-right"></i></div>
        </div>
        <div style="display: flex;flex-wrap: wrap;justify-content: space-between;margin-top: 8px"  >
          <div :style="'width: calc(33.3% - 12px);padding: calc(33.3% - 12px) 0 0;height: 0;position: relative;margin-top: 12px'"
               v-for="(Artwork,index) in artworks2" :key="Artwork.artwork_id">
            <el-image
                @click="handleClick"
                :src="Artwork.artwork_location"
                :data-artworkid="Artwork.artwork_id"
                style="border-radius: 15px; position: absolute;width: 100%;;height:100%;top:0;left: 0"
                fit="cover"
            >

              <div slot="error"  class="el-image__error">
                {{Artwork.error}}
              </div>
            </el-image>
          </div>
        </div>
      </div>
      <div class="a-block" style="display:inline-block;width: calc(50% - 110px)">
        <div style="text-align: left;position: relative">
          <div style="color:#606266;display: inline-block;padding-right: 8px;font-size: 20px"><b>人设</b></div>
          <div @click="moreArtwork3" style="padding-left: 18px;display: inline-block;color: #8c939d;cursor: pointer;position: absolute;right: 0">查看更多<i class="el-icon-caret-right"></i></div>
        </div>
        <div style="display: flex;flex-wrap: wrap;justify-content: space-between;margin-top: 8px"  >
          <div :style="'width: calc(33.3% - 12px);padding: calc(33.3% - 12px) 0 0;height: 0;position: relative;margin-top: 12px'"
               v-for="(Artwork,index) in artworks3" :key="Artwork.artwork_id">
            <el-image
                @click="handleClick"
                :src="Artwork.artwork_location"
                :data-artworkid="Artwork.artwork_id"
                style="border-radius: 15px; position: absolute;width: 100%;;height:100%;top:0;left: 0"
                fit="cover"
            >

              <div slot="error"  class="el-image__error">
                {{Artwork.error}}
              </div>
            </el-image>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
module.exports= {
  data(){
    return {
      artworks1:[],
      artworks2:[],
      artworks3:[]
    }
  },
  methods:{
    handleClick(e){
      var id = e.currentTarget.dataset.artworkid;
      this.$router.push({path:`/artworks/${id}`});
    },

    moreArtwork1(){
      this.$router.push({path:`/home/1`});
    },
    moreArtwork2(){
      this.$router.push({path:`/home/2`});
    },
    moreArtwork3(){
      this.$router.push({path:`/home/3`});
    }
  },
  mounted(){
    var _this= this;
    window.scrollTo(0,0);
    axios({
      method:'get',
      url:'/session'
    }).then(function (response) {
      axios({
        method:'get',
        url:'/artworks/home/1/0'
      }).then(function (response) {
        var data = response.data;
        for(var i in data){
          if(i>=12)break;
          _this.artworks1.push(data[i]);
        }
      });
      axios({
        method:'get',
        url:'/artworks/home/2/0'
      }).then(function (response) {
        var data = response.data;
        for(var i in data){
          if(i>=6)break;
          _this.artworks2.push(data[i]);
        }
      });
      axios({
        method:'get',
        url:'/artworks/home/3/0'
      }).then(function (response) {
        var data = response.data;
        for(var i in data){
          if(i>=6)break;
          _this.artworks3.push(data[i]);
        }
      });
    });
  }

}
</script>

<style scoped>
.user-block{
  padding: 30px;
  height: auto;
  text-align: center;
  padding-left: 48px;
  padding-right: 48px;
  background-color: #FFFFFF;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin-bottom: 30px;
  border-radius: 15px;
}
.a-block{
  padding: 30px;
  height: auto;
  text-align: center;
  padding-left: 48px;
  padding-right: 48px;
  background-color: #FFFFFF;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  border-radius: 15px;
}

</style>
