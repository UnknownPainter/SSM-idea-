<template>
  <div class="mywork-block">
    <div style="height: 50px;text-align: left;">
      <div style="float: left">
        <span style="font: 14px Base">我的作品</span><el-tag style="margin-left: 5px"><i class="el-icon-star-on"></i>{{count}}</el-tag>
      </div>
      <div style="float: left;position: absolute;right: 128px;font-size: 20px">
        <i class="el-icon-delete"></i>
        <el-switch
            v-model="isManage"
            active-color="#13ce66"
            inactive-color="#ff4949">
        </el-switch>
      </div>
    </div>
    <div class="mywork-area" ref="myworkArea" :style="{
      'grid-template-columns': `repeat(1,1fr)`}" style="text-align: center">
      <transition-group tag="p" name="mylist" class="mywork-area" :style="{
      'grid-template-columns': `repeat(${col},1fr)`}" style="text-align: center">
      <el-popconfirm
          v-for="(Artwork,index) in artworks" :key="Artwork.artwork_id"
          title="确定要删除此作品吗？"
          confirm-button-text='确定'
          cancel-button-text='取消'
          @confirm="deleteAWork(index)"
          icon="el-icon-warning"
          icon-color="red"
          :disabled="!isManage"
          style="transition: all 1s;"
      >
        <el-image
            slot="reference"
            @click="handleClick"
            :class="{'work-image':isManage}"
            :src="Artwork.artwork_location"
            :data-artworkid="Artwork.artwork_id"
            style="margin:auto;margin-top: 24px;border-radius: 15px;"
            :style="`height:${workWidth}px;width:${workWidth}px`"
            fit="cover"
        >

        </el-image>
      </el-popconfirm>
      </transition-group>
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
      workWidth:'',
      pageCount:0,
      currentPage:0,
      show:false,
      count:'',
      col:'6',
      isManage:false
    }
  },
  methods:{
    deleteAWork(e){
      var _this = this;
      axios({
        method:'delete',
        url:'/artworks/'+this.artworks[e].artwork_id,
      }).then(function (response) {
        var data = response.data;
        if(data==true)
        _this.artworks.splice(e,1);
      });
    },
    handleClick(e){
      if(!this.isManage){
        var id = e.currentTarget.dataset.artworkid;
        this.$router.push({path:`/artworks/${id}`});
      }
    },
    pageChange(e){
      var _this = this;
      this.$router.push({path:`/myworks/${e-1}`});
      axios({
        method: 'get',
        url: '/user/artworks/'+(e-1)
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
      url: '/user/artworks/count'
    }).then(function (response) {
      var data = response.data;
      _this.count = data;
      _this.pageCount = Math.floor((parseInt(data)+23)/24);
      if(_this.pageCount==0)_this.pageCount=1;

      axios({
        method: 'get',
        url: '/user/artworks/'+(_this.currentPage-1)
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
    this.workWidth = (this.$refs.myworkArea.clientWidth*0.9)/this.col;
  }
}
</script>

<style scoped>
.mywork-block{
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
.mywork-area{
  width: 100%;
  height: auto;
  display: grid;
}
.page{
  width: 100%;
  margin-top: 100px;
}
.work-image{
  box-shadow: 1px 2px 24px rgba(18,18,18,.6);
}
.mylist-leave-active {
  position: absolute;
}
.mylist-leave-to
  /* .list-leave-active for below version 2.1.8 */ {
  opacity: 0;
  transform: scale(0.2,0.2);
  transform: translateY(-30px);
}
.mylist-move{
  transition: transform 1s;
}
</style>
