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
      isManage:false,
      hasGet:false,
      user:''
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
    this.user = this.$router.app.user;
    this.$root.$on('user',(a)=>{
      _this.user = '';
      _this.user = a;
      if(this.user!=null&&this.user!=''&&_this.hasGet==false){
        this.hasGet = true;
        axios({
          method: 'get',
          url: '/user/artworks/'+(_this.currentPage-1)
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
      _this.count = parseInt(_this.user.user_artworkCount);
      _this.pageCount = Math.floor((parseInt(_this.user.user_artworkCount)+23)/24);
      if(_this.pageCount==0)_this.pageCount=1;
      _this.show= false;
      _this.$nextTick(()=>{
        _this.show=true;
      })
    });
    this.currentPage = parseInt(this.$route.params.page)+1;
    this.count = parseInt(this.user.user_artworkCount);
    this.pageCount = Math.floor((parseInt(this.user.user_artworkCount)+23)/24);
    if(this.pageCount==0)this.pageCount=1;
    this.show= false;
    this.$nextTick(()=>{
      _this.show=true;
    })

    if(this.user!=null&&this.user!=''){
      this.hasGet = true;
      axios({
        method: 'get',
        url: '/user/artworks/'+(_this.currentPage-1)
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
    var _this = this;
    this.$router.app.colSpan=24;
    this.$nextTick(()=>{
      this.workWidth = (this.$refs.myworkArea.clientWidth*0.9)/this.col;
      if(this.workWidth<180){
        this.col=4;
        this.workWidth = (this.$refs.myworkArea.clientWidth*0.9)/this.col;
        if(this.workWidth<130){
          this.col=2;
          this.workWidth = (this.$refs.myworkArea.clientWidth*0.9)/this.col;
        }
      }
    });
    window.onresize = ()=>{
      _this.$nextTick(()=>{
        _this.col=6;
        _this.workWidth = (_this.$refs.myworkArea.clientWidth*0.9)/_this.col;
        if(_this.workWidth<180){
          _this.col=4;
          _this.workWidth = (_this.$refs.myworkArea.clientWidth*0.9)/_this.col;
          if(_this.workWidth<130){
            _this.col=2;
            _this.workWidth = (_this.$refs.myworkArea.clientWidth*0.9)/_this.col;
          }
        }
      });
    };

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
