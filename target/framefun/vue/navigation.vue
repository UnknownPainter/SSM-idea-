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
      <el-menu-item index="3">消息中心</el-menu-item>
      <el-menu-item class="noStyle">
        <el-input v-model="searchContent" placeholder="Search..."></el-input>
        <i class="el-icon-search"></i>
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
          <el-menu-item class="noStyle">
            <i class="el-icon-user-solid"></i>
            <span slot="title">{{user.user_name}}</span>
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
          <el-menu-item index="5">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
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
      user:''
    };
  },
  methods:{
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
    }
    ,
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
    var _this = this;
    axios({
      method:'get',
      url:'/session'
    }).then(function (response) {
      var data = response.data;
      _this.user = data;
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
