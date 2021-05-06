<template>
  <div class="fix-box">
    <el-menu
        :default-active="activeIndex"
        class="app-el-menu"
        mode="horizontal"
        background-color="#FFF"
        text-color="#000"
        >

      <el-menu-item  @click="popDrawer" class="noStyle"><i class="el-icon-s-operation"></i></el-menu-item>
      <el-menu-item  class="noStyle">
        <a href="/" title="主页">
          <img src="./image/Frame.png" height="61">
        </a>
      </el-menu-item>
      <el-menu-item index="3">热榜</el-menu-item>
      <el-menu-item class="noStyle">
        <el-autocomplete
            v-model="searchContent"
            placeholder="Search..."
            :fetch-suggestions="getHotArtwork"
            @select="selectSearch"
            @keyup.enter.native="search"
        >
          <template slot-scope="{ item }">
            <span v-if="item.idx<=3" style="float: left;color: #F56C6C">{{item.idx}}</span>
            <span v-else style="float: left;color: #909399">{{item.idx}}</span>
            <span style="float:left;padding-left: 16px">{{item.value}}</span>
          </template>
        </el-autocomplete>
        <i class="el-icon-search" @click="search"></i>
      </el-menu-item>
    </el-menu>
    <el-drawer
        :visible.sync="drawer"
        direction="ltr"
        :with-header="false"
        size="200px"
        :append-to-body="true"
        :before-close="closeDrawer"
    >
      <div class="drawer-table">
        <el-menu>
          <el-menu-item class="noStyle" style="height: 84px;padding: 10px">
            <i class="el-icon-user-solid" v-show="!user.user_avatar"></i>
            <el-avatar :size="64" v-show="user.user_avatar">
              <el-image :src="user.user_avatar" fit="cover" style="height: 100%"></el-image>
            </el-avatar>
            <span slot="title">{{user.user_name?user.user_name:username}}</span>
          </el-menu-item>
          <el-divider></el-divider>
          <el-menu-item index="2" @click="goToUpload">
            <i class="el-icon-menu"></i>
            <span slot="title">上传作品</span>
          </el-menu-item>
          <el-menu-item index="3" @click="goToCollection">
            <i class="el-icon-collection-tag"></i>
            <span slot="title">我的收藏</span>
          </el-menu-item>
          <el-menu-item index="4" @click="goToMyworks">
            <i class="el-icon-cloudy"></i>
            <span slot="title">我的作品</span>
          </el-menu-item>
          <el-menu-item index="5" @click="goToUser">
            <i class="el-icon-setting"></i>
            <span slot="title">我的资料</span>
          </el-menu-item>
        </el-menu>

      </div>
    </el-drawer>
  </div>
</template>

<script>

module.exports = {
  data() {
    return {
      activeIndex: '',
      drawer: false,
      username:'未登录',
      searchContent:'',
      isScroll:false,
      user:'',
      searchList:[]
    };
  },
  methods:{
    search(){
      var tag = this.searchContent;
      this.$router.push({path:`/tag-search/${tag}/0`});
    },
    selectSearch(item){this.searchContent = item.value;},
    createFilter(queryString) {
      return (i)=>{return (i.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)};
    },
    getHotArtwork(str,cb){
      var _this = this;
      axios({
        method: 'get',
        url: '/hot/tag'
      }).then(function (response) {
        var data = response.data;
        _this.searchList=[];
        for(var i in data){
          var item={};
          item.value = data[i];
          item.idx = parseInt(i)+1;
          _this.searchList.push(item);
        }
        var results = str ?_this.searchList.filter(_this.createFilter(str)) : _this.searchList;
        // 调用 callback 返回建议列表的数据
        cb(results);
      });
    },
    start: function (){
      axios.get("/hello").then(function (response) {
        console.log(response);
      })
    },
    popDrawer: function (){
      this.$data.drawer=true;
      document.querySelector('body').setAttribute('style', 'overflow:hidden;');
    },
    goToUpload: function (){
      this.$router.push({path:'/upload'});
    },
    goToCollection(){
      this.$router.push({path:'/collections/0'});
    },
    goToMyworks(){
      this.$router.push({path:'/myworks/0'});
    },
    goToUser(){
      this.$router.push({path:'/user'});
    },
    isScrolling: function (){
      let t = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;   // 目前监听的是整个body的滚动条距离
      if(t>0){
        this.$data.isScroll=true;
      }else{
        this.$data.isScroll=false;
      }
    },
    closeDrawer: function (done){
      document.querySelector('body').setAttribute('style', 'overflow:auto;');
      done();
    }
  },
  mounted () {
    window.addEventListener('scroll', this.isScrolling);
    this.$parent.loadingMain=false;
    this.user = this.$router.app.user;
    var _this = this;
    this.$root.$on('user',(a)=>{
      _this.user = a;
    });
  },
  beforeRouteEnter(to,from,next){

  }
}
</script>

<style scoped>
  .app-el-menu{
    box-shadow: 0 1px 3px rgba(18,18,18,.1);
  }
  .noStyle:hover, .noStyle:focus{
    background-color: rgba(0,0,0,0) !important;
  }
  .drawer-table{
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    font-size: 14px;
  }
  .fix-box{
    position: fixed;
    top:0;
    width: 100%;
    z-index: 10;
  }
</style>
